package cse135.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.*;

public class ProductDAO {
	private static final String dbName = "jdbc:postgresql://localhost/cse135?user=postgres&password=postgres";
	static Connection currentCon = null;
	static ResultSet rs = null;
	
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
	
	public static int insert(Product newProduct) throws SQLException {
		PreparedStatement s = null;
		try {
			// Connect to database and try to insert newUser into the database
			try { Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException e) {}
			currentCon = DriverManager.getConnection(dbName);
			String query = "INSERT INTO products (name, sku, category, price, owner) VALUES (" + "'" + 
					newProduct.getName() + "', " + newProduct.getSku() + ", " + newProduct.getCategory() + ", " +
					newProduct.getPrice() + ", " + newProduct.getOwner() + ")";
			s = currentCon.prepareStatement(query);
			// Execute the query to update the database
			s.executeUpdate();
		} finally {
			// Close connections
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
		// Returns the user that was put into the database
		return (int)newProduct.getSku();
	}
}
