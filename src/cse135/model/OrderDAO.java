package cse135.model;
import java.text.*;
import java.util.*;
import java.sql.*;

public class OrderDAO {
	private static final String dbName = "jdbc:postgresql://localhost/cse135?user=postgres&password=postgres";
	static Connection currentCon = null;
	static ResultSet rs = null;
	
	public static void insert (int uid, int pid, int quantity, int price) throws SQLException {
		PreparedStatement s = null;
		try {
			try {Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException ignore) {}
			currentCon = DriverManager.getConnection(dbName);
			String query = "INSERT INTO carts (uid, pid, quantity, price) VALUES (?, ?, ?, ?)";
			s = currentCon.prepareStatement(query);
			s.setInt(1,uid);
			s.setInt(2, pid);
			s.setInt(3, quantity);
			s.setInt(4, price);
			int newId = s.executeUpdate();
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
	}
	
	public static List<Order> list (int uid) throws SQLException {
		PreparedStatement s = null;
		List<Order> cart = new ArrayList<Order>();
		try {
			try {Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException ignore) {}
			currentCon = DriverManager.getConnection(dbName);
			String query = "select products.name, carts.quantity, carts.price from products join carts" +
					" on products.id = carts.pid where uid = ? group by products.name, carts.quantity, carts.price";
			s = currentCon.prepareStatement(query);
			s.setInt(1, uid);
			System.out.println(query);
			rs = s.executeQuery();
			
			while (rs.next()) {
				Order o = new Order(rs.getString("name"), rs.getInt("quantity"), rs.getInt("price"));
				cart.add(o);
			}
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
		
		return cart;
	}
	
	public static void delete (int uid) throws SQLException {
		PreparedStatement s = null;
		try {
			try {Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException ignore) {}
			currentCon = DriverManager.getConnection(dbName);
			String query = "DELETE FROM carts WHERE uid = ?";
			s = currentCon.prepareStatement(query);
			s.setInt(1,uid);
			int newId = s.executeUpdate();
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
	}
}
