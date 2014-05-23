package cse135.model;

import java.sql.*;
import java.util.*;

public class SaleDAO {
	private static final String dbName = "jdbc:postgresql://localhost/cse135?user=postgres&password=postgres";
	static Connection currentCon = null;
	static ResultSet rs = null;
	
	public static void add (long uid, long pid, int quantity, int price) throws SQLException {
		PreparedStatement s = null;
		try {
			try {Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException ignore) {}
			currentCon = DriverManager.getConnection(dbName);
			String query = "INSERT INTO sales (uid, pid, quantity, price) VALUES (?, ?, ?, ?)";
			s = currentCon.prepareStatement(query);
			s.setLong(1,uid);
			s.setLong(2, pid);
			s.setInt(3, quantity);
			s.setInt(4, price);
			int newId = s.executeUpdate();
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
	}
	
	public static TreeMap<String, int[]> listProducts(String name, String rows, ArrayList<String> title) throws SQLException {
		PreparedStatement s = null;
		int[] pid = new int[10];
		TreeMap<String, int[]> map = new TreeMap<String, int[]>();
		try {
			try {Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException ignore) {}
			currentCon = DriverManager.getConnection(dbName);
			String query = "SELECT name, id FROM products WHERE name > '" + name + "' ORDER BY name ASC LIMIT 10 ";
			s = currentCon.prepareStatement(query);
			rs = s.executeQuery();
			query = "SELECT " + rows;
			int i = 0;
			while(rs.next()) {
				query += ", s" + i + ".quantity * s" + i + ".price" + " as q"+i;
				pid[i] = rs.getInt("id");
				i++;
				
				title.add(rs.getString("name"));
			}
			query += "\nfrom (users \n";
			
			for (int j = 0; j < 10; j++) {
				query += "left join sales as s" + j + " on s" + j + ".uid = users.id and s" + j + ".pid = " + pid[j] + "\n";
			}
			
			query += ") ";
			if (rows.equals("users.name")) {
				query += "WHERE users.role = 'customer' ";
			}
			query += "ORDER BY " + rows + " ASC LIMIT 20";
			System.out.println(query);
			s = currentCon.prepareStatement(query);
			rs = s.executeQuery();
			
			while(rs.next()) {
				int[] value = new int[10];
				for (int j = 0; j <10; j++) {
					value[j] = rs.getInt("q"+j);
				}
				map.put(rs.getString(rows.substring(rows.indexOf('.')+1)), value);
			}
			
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
		
		return map;
	}
}
