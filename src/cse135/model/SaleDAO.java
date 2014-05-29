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
	
	public static TreeMap<String, int[]> listProducts( String rows, ArrayList<String> title, int userOffset, int prodOffset, String ages, String cat) throws SQLException {
		PreparedStatement s = null;
		int[] pid = new int[10];
		// TreeMap because it'll be ordered
		TreeMap<String, int[]> map = new TreeMap<String, int[]>();
		try {
			try {Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException ignore) {}
			currentCon = DriverManager.getConnection(dbName);
			
			// Select the name and id of the products alphabetically, offset (meaning, start after this amount) by prodOffset
			String query = "SELECT p.name, p.id FROM products AS p";
			
			if ( cat != null && !cat.equals("null") && !cat.equals("all") ) {
				query += (" JOIN categories AS c ON p.cid = c.id WHERE c.name = '" + cat + "'");
			}
			
			query += (" ORDER BY name ASC LIMIT 10 OFFSET " + prodOffset);
			
			//System.out.println(query);
			s = currentCon.prepareStatement(query);
			
			// nanoTime for testing purposes
			long startTime = System.nanoTime();
			rs = s.executeQuery();
			long endTime = System.nanoTime();
			System.out.println("products: " + (endTime-startTime));
			
			/* Already start loading the next query needed for the 2d matrix
			 * example: SELECT user.name... or SELECT user.state...
			 */
			query = "SELECT " + rows;
			int i = 0;
			while(rs.next()) {
				// basically, get the sum of the money spent by a single person and that's 1 collumn
				// example looks like 'sum(s1.quantity * s1.price) as q1...'
				query += ", sum(s" + i + ".quantity * s" + i + ".price)" + " as q"+i;
				
				// store the product id for use later
				pid[i] = rs.getInt("id");
				i++;
				
				// add the 
				title.add(rs.getString("name"));
			}
			
			// Now that we have the attributes we want, specify the tables to look at which is the user that we join with the sales table
			query += "\nFROM (users \n";
			
			for (int j = 0; j < i; j++) {
				/* This line basically means that we should join on the sales table, with the user id the same as the sales.uid
				 * and the product id equal to the product id we stored earlier.
				 * example: LEFT JOIN sales AS s1 ON s1.uid = users.id AND s1.pid = 328..."
				 * Note that every product has its own column, which is why we need these aliases s1, s2....etc
				 */
				query += "left join sales as s" + j + " on s" + j + ".uid = users.id and s" + j + ".pid = " + pid[j] + "\n";
			}
			
			/* Now the restrictions:
			 * first check if the user is a customer
			 * then group by the attribute selected (either users.name or users.state)
			 * then order by the attribute seleceted
			 * And finally limit the query to 20 at a time, offsetting by the given offset
			 * TODO: add the other query restrictions here...maybe. Not sure
			 */
			query += ") WHERE users.role = 'customer'";



			if ( ages != null && !ages.equals("null") && !ages.equals("0") ) {
				if ( !ages.equals("4") ) {
					int v1 = 0, v2 = 0;
					switch ( Integer.parseInt(ages) ) {
						case 1: v1 = 12; v2 = 18; break;
						case 2: v1 = 18; v2 = 45; break;
						case 3: v1 = 45; v2 = 65; break;
					}
					query += (" AND users.age BETWEEN " + v1 + " AND " + v2);
				}
				else {
					query += " AND users.age >= 65";
				}	
			}
			
			query += (" GROUP BY " + rows + " ORDER BY " + rows + " ASC");
			query += (" LIMIT 20 OFFSET " + userOffset);
			
			// This is the final query based on everything else
			//System.out.println(query);
			s = currentCon.prepareStatement(query);
			
			startTime = System.nanoTime();
			rs = s.executeQuery();
			endTime = System.nanoTime();
			System.out.println("2d: "+ (endTime - startTime));
			
			while(rs.next()) {
				int[] value = new int[i];
				// This is the array for each column in the table, i is the number of products (number of columns) in the table
				for (int j = 0; j <i; j++) {
					value[j] = rs.getInt("q"+j);
				}
				
				/* put the int array in the treemap; user name is the key, int array is the value
				 * have to do rows.indexOf because you need to parse the rows to just name or state
				 */
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
