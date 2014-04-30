package cse135.model;
import java.text.*;
import java.util.*;
import java.sql.*;

public class CategoryDAO {
	private static final String dbName = "jdbc:postgresql://localhost/cse135?user=postgres&password=postgres";
	static Connection currentCon = null;
	static ResultSet rs = null;
	
	public static List<Category> list() throws SQLException{
		PreparedStatement s = null;
		List<Category> cats = new ArrayList<Category>();

         // Open a connection to the database using DriverManager

		try {
			try {Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException ignore) {}
			currentCon = DriverManager.getConnection(
					"jdbc:postgresql://localhost/cse135?" +
				    "user=postgres&password=postgres");
			s = currentCon.prepareStatement("SELECT * FROM categories");
			rs = s.executeQuery();
			
			while (rs.next()) {
				Category cat = new Category();
				cat.setId(rs.getLong("id"));
				cat.setName(rs.getString("name"));
				cat.setDescription(rs.getString("description"));
				cats.add(cat);
			}
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
		return cats;
	}
	
	public static int insert(Category newCategory) throws SQLException {
		PreparedStatement s = null;
		int rowCount;
		try {
			// Connect to database and try to insert newUser into the database
			try { Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException e) {}
			currentCon = DriverManager.getConnection(dbName);
			String query = "INSERT INTO categories (name, description) VALUES (" + "'" + 
					newCategory.getName() + "', '" + newCategory.getDescription() + "')";
			s = currentCon.prepareStatement(query);
			// Execute the query to update the database
			rowCount = s.executeUpdate();
		} finally {
			// Close connections
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
		// Returns the user that was put into the database
		return rowCount;
	}
}
