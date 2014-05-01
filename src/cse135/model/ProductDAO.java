package cse135.model;

import java.sql.*;
import java.util.Hashtable;

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
