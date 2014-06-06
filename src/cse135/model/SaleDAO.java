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
			
			int sumamt = price * quantity;
			query = "INSERT INTO UserSales (uid, state, category, sumamt) VALUES (" +
			        uid + ", " + 
			        "(SELECT u.state FROM users AS u WHERE u.id = " + uid + "), " + 
					"(SELECT c.name FROM categories AS c JOIN products AS p ON c.id = p.cid WHERE p.id = " + pid + "), " +
			        sumamt + ")";
			s = currentCon.prepareStatement(query);
			s.executeUpdate();

			query = "INSERT INTO ProductSales (pid, state, sumamt) VALUES ( " +
					pid + ", " +
					"(SELECT u.state FROM users AS u WHERE u.id = " + uid + "), " +
					sumamt + ")";
			s = currentCon.prepareStatement(query);
			s.executeUpdate();
			
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
	}
	
	public static String listProducts( String rows, ArrayList<String> title, ArrayList<Integer> pids,
			ArrayList<String> rowTitle, String cat, String state) throws SQLException {
		PreparedStatement s = null;
		boolean categoryFilter =  (cat != null && !cat.equals("null") && !cat.equals("all"));
		boolean stateFilter = (state != null && !state.equals("null") && !state.equals("All"));
		String table;
		try {
			try {Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException ignore) {}
			currentCon = DriverManager.getConnection(dbName);
			
			
			// Select the name and id of the products alphabetically, offset (meaning, start after this amount) by prodOffset
			
			String tempTable = "CREATE TEMPORARY TABLE topProd AS\n";
			String query;
			//String sumamt = (stateFilter) ? "sumamt" : "SUM(sumamt)";
			 tempTable += "SELECT p.name, p.id as pid, CASE WHEN SUM(sumamt) IS NULL THEN 0 ELSE SUM(sumamt) END" +
			 		" AS total FROM (products AS p LEFT JOIN prodstateprecomp ON p.id = pid\n";
			if (stateFilter) {
				tempTable += "AND state = '" + state + "'";
			}
			tempTable += ")\n";
			if (categoryFilter) {
				tempTable += " WHERE cid = (SELECT id FROM categories WHERE name = '"+ cat +"')\n";
				//tempTable += " cid = (SELECT id FROM categories WHERE name = '"+ cat +"')";
			}
			tempTable += " GROUP BY p.name, p.id";
			tempTable += "\nORDER BY total DESC LIMIT 10";
			System.out.println("Temp Table: \n" + tempTable+"\n");
			s = currentCon.prepareStatement(tempTable);
			long startTime = System.nanoTime();
			s.executeUpdate();
			
			
			query = "SELECT name, pid, total from topProd";
			
			System.out.println("Products:\n"+query+"\n");
			s = currentCon.prepareStatement(query);
			
			// nanoTime for testing purposes
			
			rs = s.executeQuery();
			
			int i = 0;
			while (rs.next()) {
				title.add(rs.getString("name")+"\n($"+rs.getInt("total")+")");
				pids.add(rs.getInt("pid"));
			}
			
			
			
			// Now get the names and totals of the rows of the matrix
			/* SELECT users.name AS name, users.id AS uid, case when sum(sumamt) is null then 0 else sum(sumamt) end AS total
FROM (users LEFT JOIN usersales ON users.id = uid and category = 'C10')

GROUP BY users.name, users.id ORDER BY total DESC LIMIT 20;
*/
			// Temp table because we're gonna use this info again later
			String name;
			String orderby;
			String twoD;
			if (rows.equals("users.state")) {
				name = "users.state AS name,";
				orderby = "users.state";
				twoD = ".state = temp.name\n";
			} else {
				name = "users.name AS name, users.id AS uid,";
				orderby = "users.name, users.id";
				twoD = ".uid = temp.uid\n";
			}
			//sumamt = categoryFilter ? "sumamt" : "SUM(sumamt)";
			tempTable = "CREATE TEMPORARY TABLE temp AS\n" +
					"SELECT "+ name +" CASE WHEN SUM(sumamt) IS NULL THEN 0 ELSE SUM(sumamt) END AS total\n" +
					"FROM (users LEFT JOIN userscatprecomp ON users.id = uid\n";//)\n" +
			if (categoryFilter)
				tempTable += "AND category = '" + cat + "'\n";
			tempTable += ")";
			if (stateFilter)
				tempTable += " WHERE users.state = '" + state + "'\n";
			
			tempTable += "GROUP BY "+orderby;
			tempTable += "\nORDER BY total DESC LIMIT 20";
			System.out.println("Temp Table: \n" + tempTable+"\n");
			s = currentCon.prepareStatement(tempTable);
			s.executeUpdate();
			
			// the actual query to get the names and the totals
			query = "SELECT name, total FROM temp";
			
			s = currentCon.prepareStatement(query);
			//System.out.println("Rows:\n"+query+"\n");
			rs = s.executeQuery();
			
			while(rs.next()) {
				rowTitle.add(rs.getString("name")+"\n($"+rs.getInt("total")+")");
			}
			
			
			/* Finally get the data for the 2D matrix
			 */
			//sumamt = (stateFilter && categoryFilter) ? "sumamt" : "SUM(sumamt)";
			query = "SELECT temp.total as rows, topProd.total as columns, temp.name, UsersCatProdStatePrecomp.pid, " +
						"SUM(sumamt) as sum\n" +
					"FROM temp\n" +
					"LEFT JOIN UsersCatProdStatePrecomp ON UsersCatProdStatePrecomp"+twoD+" AND pid IN (\n" +
					"SELECT pid FROM topProd)\n" +
					"LEFT JOIN topProd ON UsersCatProdStatePrecomp.pid = topProd.pid\n" +
					"WHERE UsersCatProdStatePrecomp.pid IS NOT NULL\n";
			if(stateFilter)
				query += "AND UsersCatProdStatePrecomp.state = '" + state + "'\n";
			if(categoryFilter)
				query += "AND UsersCatProdStatePrecomp.category = '" + cat + "'\n";
			query += "GROUP BY temp.name, USersCatProdStatePrecomp.pid, temp.total, topProd.total" +
					 "\nORDER BY temp.total DESC, name ASC, topProd.total DESC";// +
			/*if(rows.equals("users.state"))
				query += "LEFT JOIN users ON temp.name = users.state AND users.state IN (SELECT name FROM temp)\n";*/
			/*query +="LEFT JOIN sales ON "+twoD+" AND pid IN (\n" +
					"SELECT pid FROM topProd)\n" +
					"LEFT JOIN topProd ON sales.pid = topProd.pid\n" +//
					"WHERE sales.pid IS NOT NULL GROUP BY temp.name, sales.pid, temp.total, topProd.total ORDER BY temp.total DESC, name ASC, topProd.total DESC";
			*/
			// This is the final query based on everything else
			System.out.println("2D query:\n" + query+"\n");
			s = currentCon.prepareStatement(query);
			
			rs = s.executeQuery();
			
			long endTime = System.nanoTime();
			System.out.println(""+ (endTime - startTime));
			
			// Start writing the html for the table
			table = "<table border=\"1\">\n<tr>\n<th></th>";
						
						// Header of the columns: the products to list
			for (String p : title) {
				table += "<th>"+p+"</th>\n";
			}
			table += "</tr>\n";
						
			// get the first element of the set
			//rs.next();
				
			// set up the first user as the currentUser we're on
			String currentUser = null;
			if (rs.next())
				currentUser = rs.getString("name");
			
			// Iterate through the rows
			
			for (String r : rowTitle) {
				// boolean set up so when we finish the purchases of one user
				System.out.print(currentUser+ " | ");
				boolean skip = rs.isAfterLast();
				// new row
				table += "<tr>\n<td style=\"font-weight: bold\">"+r+"</td>\n";
				// Iterate through the top 10 pids
				for (Integer prod : pids) {
					table += "<td>$";
					// If the result set isn't already on the next row or the pid is equal to this cell's pid
					if (!skip && rs.getInt("pid") == prod) {
						// populate this cell with the total sales.
						table += "" + rs.getString("sum");
						boolean isnext = rs.next();
						// then get the next row of the rs.
						// then check if, by going to the next set, we reached the end of the set
						// or we finished iterating through this user's purchases
						if (!isnext || !rs.getString("name").equals(currentUser)) {
							// if ^ is true, then skip the rest of the products and just populate those cells with 0's
							skip = true;
							// change the current user to the next user
							if (!rs.isAfterLast())
								currentUser = rs.getString("name");
						}
					} else {
						// if the user didn't buy any of this product, just show 0
						table += "0";
					}
					table += "</td>\n";
				}
				if (!skip) {
					if (!rs.next()) 
						currentUser = rs.getString("name");
				}
				table += "</tr>\n";
			}
			rs.next();
			table += "</table>\n";
			
		} finally {
			
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
		return table;
		//return map;
	}
}
