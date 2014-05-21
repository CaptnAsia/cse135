package cse135.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.*;

public class ProductDAO {
	private static final String dbName = "jdbc:postgresql://localhost/cse135?user=postgres&password=postgres";
	static Connection currentCon = null;
	static ResultSet rs = null;
	
	/* Find's a product in the products database
	 */
	public static Product find(String key, int value) throws SQLException {
		PreparedStatement s = null;
		Product p = new Product();

         // Open a connection to the database using DriverManager
		try {
			try {Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException ignore) {}
			currentCon = DriverManager.getConnection(dbName);
			String query = "SELECT * FROM products WHERE " + key + " = ?";// + key + " = " + value;
			s = currentCon.prepareStatement(query);
			if (key.equals("sku")) {
				s.setString(1, "" + value);
			} else {
				s.setInt(1, value);
			}
			rs = s.executeQuery();
			
			if (rs.next()) {
				p.setId(rs.getLong("id"));
				p.setName(rs.getString("name"));
				p.setSku(rs.getString("sku"));
				p.setCategory(rs.getInt("cid"));
				p.setPrice(rs.getDouble("price"));
			}
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
		return p;
	}
	
	/* This function searches for all the categories that are linked to products and
	 * puts the id's in a hashtable to check for later
	 */
	public static Hashtable<Long,Boolean> categories() throws SQLException {
		PreparedStatement s = null;
		Hashtable<Long,Boolean> table = new Hashtable<Long,Boolean>();
		try {
			currentCon = DriverManager.getConnection(dbName);
			String query = "SELECT cid FROM products";
			s = currentCon.prepareStatement(query);
			rs = s.executeQuery();
			
			while (rs.next()) {
				table.put(rs.getLong("cid"),false);
			}
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
		
		return table;
	}
	
	/**
	 * Lists all the products with a certain category. If the category id is negative than just
	 * query all products.
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static List<Product> list(String category) throws SQLException{
		PreparedStatement s = null;
		List<Product> products = new ArrayList<Product>();

         // Open a connection to the database using DriverManager
		try {
			try {Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException ignore) {}
			currentCon = DriverManager.getConnection(dbName);
			String query;
			query = "SELECT products.* FROM products";
			if (!category.equals("*")) {
				query += " JOIN categories ON cid = categories.id WHERE categories.name = ?";
			}
			
			s = currentCon.prepareStatement(query);
			if (!category.equals("*")) {
				s.setString(1, category);
			}
			rs = s.executeQuery();
			
			while (rs.next()) {
				Product product = new Product();
				
				product.setId(rs.getLong("id"));
				product.setName(rs.getString("name"));
				product.setSku(rs.getString("sku"));
				product.setCategory(rs.getInt("cid"));
				product.setPrice(rs.getDouble("price"));
				products.add(product);
				System.out.println("product: " + product.getName());
			}
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
		return products;
	}
	
	public static int alter(String query, String name, String sku, int cat, double price, int id) throws SQLException {
		PreparedStatement s = null;
		int parameterIndex = 1;
		int newId;
		try {
			// Connect to database and try to insert newUser into the database
			try { Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException e) {}
			currentCon = DriverManager.getConnection(dbName);
			s = currentCon.prepareStatement(query);
			if (name != null) {
				s.setString(parameterIndex, name);
				parameterIndex++;
			} if (sku != null) {
				s.setString(parameterIndex, sku);
				parameterIndex++;
			} if (cat >= 0) {
				s.setInt(parameterIndex, cat);
				parameterIndex++;
			} if (price >= 0) {
				s.setDouble(parameterIndex, price);
				parameterIndex++;
			} if (id >= 0)
				s.setInt(parameterIndex, id);
			
			// Execute the query to update the database
			newId = s.executeUpdate();
		} finally {
			// Close connections
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
		// Returns the user that was put into the database
		return newId;
	}
}
