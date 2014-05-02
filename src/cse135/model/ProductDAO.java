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
			s.setInt(1, value);
			rs = s.executeQuery();
			
			if (rs.next()) {
				p.setId(rs.getLong("id"));
				p.setName(rs.getString("name"));
				p.setSku(rs.getLong("sku"));
				p.setCategory(rs.getInt("category"));
				p.setPrice(rs.getDouble("price"));
				p.setOwner(rs.getLong("owner"));
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
			String query = "SELECT category FROM Products";
			s = currentCon.prepareStatement(query);
			rs = s.executeQuery();
			
			while (rs.next()) {
				table.put(rs.getLong("category"),false);
			}
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
		
		return table;
	}
	
	public static List<Product> list(long id) throws SQLException{
		PreparedStatement s = null;
		List<Product> products = new ArrayList<Product>();

         // Open a connection to the database using DriverManager
		try {
			try {Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException ignore) {}
			currentCon = DriverManager.getConnection(dbName);
			String query;
			if (id == -1)
				query = "SELECT * FROM products ORDER BY sku ASC";
			else
				query = "SELECT * FROM products WHERE category = " + id + " ORDER BY sku ASC";
			s = currentCon.prepareStatement(query);
			rs = s.executeQuery();
			
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getLong("id"));
				product.setName(rs.getString("name"));
				product.setSku(rs.getLong("sku"));
				product.setCategory(rs.getInt("category"));
				product.setPrice(rs.getDouble("price"));
				product.setOwner(rs.getLong("owner"));
				products.add(product);
			}
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
		return products;
	}
	
	public static int alter(String query, String name, int sku, int cat, double price, long owner, int id) throws SQLException {
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
			} if (sku >= 0) {
				s.setInt(parameterIndex, sku);
				parameterIndex++;
			} if (cat >= 0) {
				s.setInt(parameterIndex, cat);
				parameterIndex++;
			} if (price >= 0) {
				s.setDouble(parameterIndex, price);
				parameterIndex++;
			} if (owner >= 0) {
				s.setLong(parameterIndex, owner);
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
