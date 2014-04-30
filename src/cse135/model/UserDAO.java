package cse135.model;

import java.sql.*;

public class UserDAO {
	private static final String dbName = "jdbc:postgresql://localhost/cse135?user=postgres&password=postgres";
	static Connection currentCon = null;
	static ResultSet rs = null;
	public static User find(String name) throws SQLException {
		PreparedStatement s = null;
		User user = null;
		try {
			try { Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException e) {}
			currentCon = DriverManager.getConnection(dbName);
			s = currentCon.prepareStatement("SELECT * FROM Users WHERE name = '"+ name + "'");
			rs = s.executeQuery();
			
			if (rs.next()) {
				user = new User(rs.getString("name"),rs.getInt("age"),rs.getString("state"),rs.getBoolean("owner"));
				/*user.setName(rs.getString("name"));
				user.setAge(rs.getInt("age"));
				user.setState(rs.getString("state"));
				user.setOwner(rs.getBoolean("owner"));*/
			}
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
		return user;
	}
	
	public static User insert(User newUser) throws SQLException {
		PreparedStatement s = null;
		try {
			try { Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException e) {}
			currentCon = DriverManager.getConnection(dbName);
			String query = "INSERT INTO users (name, age, state, owner) VALUES ('"+ newUser.getName() + "', " +
					newUser.getAge() + ", '" + newUser.getState() + "', " + newUser.isOwner() + ")";
			s = currentCon.prepareStatement(query);
			System.out.println(query);
			rs = s.executeQuery();
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
		return newUser;
	}
}
