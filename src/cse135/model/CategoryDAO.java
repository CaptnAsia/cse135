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
			s = currentCon.prepareStatement("SELECT * FROM categories ORDER BY name ASC");
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
	
	public static int alter(String query, String name, String descr, int id) throws SQLException {
		PreparedStatement s = null;
		int rowCount;
		int parameterIndex = 1;
		try {
			// Connect to database and try to insert newUser into the database
			try { Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException e) {}
			currentCon = DriverManager.getConnection(dbName);
			s = currentCon.prepareStatement(query);
			if (name != null ) {
				s.setString(parameterIndex, name);
				parameterIndex++;
			}
			if (descr != null ) {
				s.setString(parameterIndex, descr);
				parameterIndex++;
			}
			if (id != -1) {
				s.setInt(parameterIndex, id);
			}
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
	
	// Much DRY, wow, very space saving
	public static long find(String name) throws SQLException {
        long catID = -1;
        for(Category c : list())
     	   if (c.getName().equals(name))
     		   catID = c.getId();
        return catID;
	}
}
