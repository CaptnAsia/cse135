package cse135.model;
import java.text.*;
import java.util.*;
import java.sql.*;

public class CategoryDAO {
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
				cat.setId(rs.getLong("c_id"));
				cat.setName(rs.getString("c_name"));
				cat.setDescription(rs.getString("c_description"));
				cats.add(cat);
			}
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
		return cats;
	}
}
