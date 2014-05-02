package cse135.model;

import java.sql.*;

public class UserDAO {
	// static variables
	private static final String dbName = "jdbc:postgresql://localhost/cse135?user=postgres&password=postgres";
	static Connection currentCon = null;
	static ResultSet rs = null;
	
	public static User find(String name) throws SQLException {
		PreparedStatement s = null;
		User user = null;
		
		try {
			try { Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException e) {}
			
			// Connect to the database and execute a query to find a user with the same name as name
			currentCon = DriverManager.getConnection(dbName);
			String query = "SELECT * FROM Users WHERE name = ?";
			s = currentCon.prepareStatement(query);
			s.setString(1, name);
			rs = s.executeQuery();
			
			// Checks if anything is returned
			if (rs.next()) {
				// Then creates a new user90
				System.out.println("not working");
				user = new User(rs.getString("name"),rs.getInt("age"),rs.getString("state"),rs.getBoolean("owner"),rs.getLong("id"));
			}
		} finally {
			// Close all connections
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
		// returns a null user or a user with all the objects filled, depending on if a thing was found
		return user;
	}
	
	public static long insert(User newUser) throws SQLException {
		PreparedStatement s = null;
		long rowCount = 0;
		try {
			// Connect to database and try to insert newUser into the database
			try { Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException e) {}
			currentCon = DriverManager.getConnection(dbName);
			String query = "INSERT INTO users (name, age, state, owner) VALUES (?, ?, ?, ?)";
			s = currentCon.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			s.setString(1, newUser.getName());
			s.setInt(2, newUser.getAge());
			s.setString(3, newUser.getState());
			s.setBoolean(4, newUser.isOwner());
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
