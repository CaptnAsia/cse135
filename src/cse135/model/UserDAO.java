package cse135.model;

import java.sql.*;

public class UserDAO {
	static Connection currentCon = null;
	static ResultSet rs = null;
	public static User login(String name) throws SQLException {
		PreparedStatement s = null;
		User user = null;
		try {
			try { Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException e) {}
			currentCon = DriverManager.getConnection(
					"jdbc:postgresql://localhost/cse135?" +
				    "user=postgres&password=postgres");
			s = currentCon.prepareStatement("SELECT * FROM Users WHERE name = '"+ name + "'");
			rs = s.executeQuery();
			
			if (rs.next()) {
				System.out.println("testtest");
				user = new User();
				user.setName(rs.getString("name"));
				user.setAge(rs.getInt("age"));
				user.setState(rs.getString("state"));
				user.setOwner(rs.getBoolean("owner"));
			}
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
		return user;
	}
}
