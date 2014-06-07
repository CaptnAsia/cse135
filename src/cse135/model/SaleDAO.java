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
			
			String state = "(SELECT u.state FROM users AS u WHERE u.id = " + uid + ")";
			String category = "(SELECT c.name FROM categories AS c JOIN products AS p ON c.id = p.cid WHERE p.id = " + pid + ")";
			int sumamt = price * quantity;
			String pTable = "";
			String condition = "";
			String oldSumamt = "";
			
			System.out.println("\nPrecomputed Table Queries:");
			
			// ProdStatePrecomp (pid, state, sumamt)
			pTable = "ProdStatePrecomp";
			condition = " WHERE pid = " + pid + " AND state = " + state;
			oldSumamt = "(SELECT sumamt FROM " + pTable + condition + ")";
			
			query = "SELECT * FROM " + pTable + condition;
			s = currentCon.prepareStatement(query);
			rs = s.executeQuery();
			
			query = (rs.next()) ?
					("UPDATE " + pTable + " SET sumamt = " + sumamt + " + " + oldSumamt + condition) :
			        ("INSERT INTO " + pTable + " (pid, state, sumamt) VALUES (" + pid + ", " + state + ", " + sumamt + ")");
			System.out.println(query);
			s = currentCon.prepareStatement(query);
			s.executeUpdate();
			
			// UsersCatPrecomp (uid, category, sumamt)
			pTable = "UsersCatPrecomp";
			condition = " WHERE uid = " + uid + " AND category = " + category;
			oldSumamt = "(SELECT sumamt FROM " + pTable + condition + ")";
			
			query = "SELECT * FROM " + pTable + condition;
			s = currentCon.prepareStatement(query);
			rs = s.executeQuery();
			
			query = (rs.next()) ?
					("UPDATE " + pTable + " SET sumamt = " + sumamt + " + " + oldSumamt + condition) :
					("INSERT INTO " + pTable + " (uid, category, sumamt) VALUES (" + uid + ", " + category + ", " + sumamt + ")");
			System.out.println(query);
			s = currentCon.prepareStatement(query);
			s.executeUpdate();
			
			// StateCatPrecomp (state, category, sumamt)
			pTable = "StateCatPrecomp";
			condition = " WHERE state = " + state + " AND category = " + category;
			oldSumamt = "(SELECT sumamt FROM " + pTable + condition + ")";
			
			query = "SELECT * FROM " + pTable + condition;
			s = currentCon.prepareStatement(query);
			rs = s.executeQuery();
			
			query = (rs.next()) ?
					("UPDATE " + pTable + " SET sumamt = " + sumamt + " + " + oldSumamt + condition) :
					("INSERT INTO " + pTable + " (state, category, sumamt) VALUES (" + state + ", " + category + ", " + sumamt + ")");	
			System.out.println(query);
			s = currentCon.prepareStatement(query);
			s.executeUpdate();
			
			//UsersCatProdStatePrecomp (uid, category, pid, state, sumamt)
			pTable = "UsersCatProdStatePrecomp";
			condition = " WHERE uid = " + uid + " AND category = " + category + " AND pid = " + pid + " AND state = " + state;
			oldSumamt = "(SELECT sumamt FROM " + pTable + condition + ")";
			
			query = "SELECT * FROM " + pTable + condition;
			s = currentCon.prepareStatement(query);
			rs = s.executeQuery();
			
			query = (rs.next()) ?
					("UPDATE " + pTable + " SET sumamt = " + sumamt + " + " + oldSumamt + condition) :
					("INSERT INTO " + pTable + " (uid, category, pid, state, sumamt) VALUES (" + uid + ", " + category + ", " + pid + ", " + state + ", " + sumamt + ")");
			System.out.println(query);
			s = currentCon.prepareStatement(query);
			s.executeUpdate();
			
			// UsersPrecomp (uid, sumamt)
			pTable = "UsersPrecomp";
			condition = " WHERE uid = " + uid;
			oldSumamt = "(SELECT sumamt FROM " + pTable + condition + ")";
			
			query = "SELECT * FROM " + pTable + condition;
			s = currentCon.prepareStatement(query);
			rs = s.executeQuery();
			
			query = (rs.next()) ?
					("UPDATE " + pTable + " SET sumamt = " + sumamt + " + " + oldSumamt + condition) :
					("INSERT INTO " + pTable + " (uid, sumamt) VALUES (" + uid + ", " + sumamt + ")");
			System.out.println(query);
			s = currentCon.prepareStatement(query);
			s.executeUpdate();
			
			// ProdPrecomp (pid, sumamt)
			pTable = "ProdPrecomp";
			condition = " WHERE pid = " + pid;
			oldSumamt = "(SELECT sumamt FROM " + pTable + condition + ")";
			
			query = "SELECT * FROM " + pTable + condition;
			s = currentCon.prepareStatement(query);
			rs = s.executeQuery();
			
			query = (rs.next()) ?
					("UPDATE " + pTable + " SET sumamt = " + sumamt + " + " + oldSumamt + condition) :
					("INSERT INTO " + pTable + " (pid, sumamt) VALUES (" + pid + ", " + sumamt + ")");
			System.out.println(query);
			s = currentCon.prepareStatement(query);
			s.executeUpdate();
			
			// StatesPrecomp (state, sumamt)
			pTable = "StatesPrecomp";
			condition = " WHERE state = " + state;
			oldSumamt = "(SELECT sumamt FROM " + pTable + condition + ")";
			
			query = "SELECT * FROM " + pTable + condition;
			s = currentCon.prepareStatement(query);
			rs = s.executeQuery();
			
			query = (rs.next()) ?
					("UPDATE " + pTable + " SET sumamt = " + sumamt + " + " + oldSumamt + condition) :
					("INSERT INTO " + pTable + " (state, sumamt) VALUES (" + state + ", " + sumamt + ")");
			System.out.println(query);
			s = currentCon.prepareStatement(query);
			s.executeUpdate();
			
			System.out.println();
			
		} finally {
			if (rs != null) try {rs.close();} catch (SQLException ignore) {}
			if (s != null) try {s.close();} catch (SQLException ignore) {}
			if (currentCon != null) try {currentCon.close();} catch (SQLException ignore) {}
		}
	}
	
	public static String listProducts(String rows, String cat, String state) throws SQLException {
		PreparedStatement s = null;
		ArrayList<String> title = new ArrayList<String>(); // the headers for the columns
		ArrayList<Integer> pids = new ArrayList<Integer>(); // product ids corresponding with the columns
		ArrayList<String> rowTitle = new ArrayList<String>(); // Users or States on the left most column
		boolean categoryFilter =  (cat != null && !cat.equals("null") && !cat.equals("all")); // Check if there's a category filter
		boolean stateFilter = (state != null && !state.equals("null") && !state.equals("All")); // Check if there's a state filter
		String table;
		try {
			try {Class.forName("org.postgresql.Driver");} catch (ClassNotFoundException ignore) {}
			currentCon = DriverManager.getConnection(dbName);
			
			
			// Select the name and id of the products alphabetically, offset (meaning, start after this amount) by prodOffset
			
			/* Create a temp table to get the top 10 products based on total sales of the product from our precomputed tables
			 * if there's a state filter, select from the ProdStatePrecomp table, if not then select from the ProdPrecomp table
			 * 
			 * SELECT p.name, p.id as pid, case when sum(sumamt) is null then 0 else sum(sumamt) end AS total
			 * ^ This means select the product's name, id, and the total sales (sumamt) for each of the products.
			 * 		The case statements means that if there's no sales for a product then just put in 0, since we do a left join later
			 * 
			 * FROM (products AS p LEFT JOIN <tableName> ON p.id = pid
			 * select these things from the products table left join (meaning all elements of products are included) the precomputed table
			 * 
			 * AND state = '<state>'
			 * If there's a state filter include this inside the join because we still want to see all the products.
			 * 		If we did a where state = state clause outside of the ")" then it would filter out a lot of products, that we want to see
			 * 
			 * ) WHERE cid = (SELECT id FROM categories WHERE name = <cat>)
			 * If theres a category filter then filter out all the sales where the category is cat
			 * 		We select the cid of the cat based on the name that's passed into the query
			 * 
			 * GROUP BY p.name, p.id ORDER BY total DESC LIMIT 10
			 * group by the p.name and order by total desc (most sales to least sales)
			 */
			String tempTable = "CREATE TEMPORARY TABLE topProd AS\n";
			String query;
			String tableName = stateFilter ? "prodstateprecomp" : "prodprecomp";
			//String sumamt = (stateFilter) ? "sumamt" : "SUM(sumamt)";
			 tempTable += "SELECT p.name, p.id as pid, CASE WHEN SUM(sumamt) IS NULL THEN 0 ELSE SUM(sumamt) END" +
			 		" AS total FROM (products AS p LEFT JOIN "+tableName+" ON p.id = pid\n";
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
			//System.out.println("Temp Table: \n" + tempTable+"\n");
			s = currentCon.prepareStatement(tempTable);
			long startTime = System.nanoTime();
			s.executeUpdate();
			
			// Query to get the products
			query = "SELECT name, pid, total from topProd";
			
			//System.out.println("Products:\n"+query+"\n");
			s = currentCon.prepareStatement(query);
			rs = s.executeQuery();
			
			int i = 0;
			while (rs.next()) {
				title.add(rs.getString("name")+"\n($"+rs.getInt("total")+")");
				pids.add(rs.getInt("pid"));
			}
			
			
			
			// Now get the names and totals of the rows of the matrix
			/* SELECT users.name AS name, users.id AS uid, case when sum(sumamt) is null then 0 else sum(sumamt) end AS total
			 * FROM (users LEFT JOIN <precompTable> ON users.id = uid and category = 'C10')
			 * GROUP BY users.name, users.id ORDER BY total DESC LIMIT 20;
			 */
			// Temp table because we're gonna use this info again later
			String name;
			String orderby;
			String twoD;
			String joinCond;
			if (rows.equals("users.state")) {
				name = "users.state AS name,";
				orderby = "users.state";
				twoD = ".state = temp.name\n";
				
				tableName = categoryFilter ? "statecatprecomp" : "statesprecomp";
				joinCond = "users.state = " + tableName + ".state";
			} else {
				name = "users.name AS name, users.id AS uid,";
				orderby = "users.name, users.id";
				twoD = ".uid = temp.uid\n";
				tableName = categoryFilter ? "userscatprecomp" : "usersprecomp";
				joinCond = "users.id = uid";
			}
			//tableName = categoryFilter ? "userscatprecomp" : "usersprecomp";
			tempTable = "CREATE TEMPORARY TABLE temp AS\n" +
					"SELECT "+ name +" CASE WHEN SUM(sumamt) IS NULL THEN 0 ELSE SUM(sumamt) END AS total\n" +
					"FROM (users LEFT JOIN "+tableName+" ON "+joinCond+"\n";//)\n" +
			if (categoryFilter)
				tempTable += "AND category = '" + cat + "'\n";
			tempTable += ")";
			if (stateFilter)
				tempTable += " WHERE users.state = '" + state + "'\n";
			
			tempTable += "GROUP BY "+orderby;
			tempTable += "\nORDER BY total DESC LIMIT 20";
			//System.out.println("Temp Table: \n" + tempTable+"\n");
			s = currentCon.prepareStatement(tempTable);
			s.executeUpdate();
			
			// the actual query to get the names and the totals
			query = "SELECT name, total FROM temp";
			
			s = currentCon.prepareStatement(query);
			//System.out.println("Rows:\n"+query+"\n");
			rs = s.executeQuery();
			
			while(rs.next()) {
				rowTitle.add(rs.getString("name")+"\n($"+rs.getLong("total")+")");
			}
			
			
			/* Finally get the data for the 2D matrix
			 * SELECT temp.total as rows, topProd.total as columns, temp.name, UsersCatProdStatePrecomp.pid, SUM(sumamt) as sum
			 * Select the total from the rows temp table, total from the products temp table for sorting later
			 * 		Also select temp.name (which could be users.state or users.name) and the pid from the userscatprodstate precomp table
			 * 		userscatprodstate precomp table used to easily compute the cells of the 2d matrix
			 * 
			 * FROM temp
			 * LEFT JOIN UsersCatProdStatePrecomp ON UsersCatProdStatePrecomp.uid = temp.uid
			 * AND pid IN (
			 * SELECT pid FROM topProd)
			 * 
			 * Select these things from the temp table, left join on the precomp table where the uids are equal and the pid is in the topProd table
			 * 
			 * LEFT JOIN topProd ON UsersCatProdStatePrecomp.pid = topProd.pid
			 * Then join on the topProd table wher the pids are equal of the precomputed and the topprod table
			 * 
			 * WHERE UsersCatProdStatePrecomp.pid IS NOT NULL
			 * Make sure the pid is not null, I was getting an error for some reason for this before
			 * 
			 * AND UsersCatProdStatePrecomp.category = 'C14'
			 * Filters
			 * 
			 * GROUP BY temp.name, USersCatProdStatePrecomp.pid, temp.total, topProd.total
			 * Group by temp.name first, then precomptable, then the totals
			 * 
			 * ORDER BY temp.total DESC, name ASC, topProd.total DESC
			 * Since we're going to iterate through the query by the rows, first sort by temp.total, then
			 * in the case where the temp.totals are equal, sort by the names sot hey don't get mixed together,
			 * and then finally sort by topProd totals, so that they follow the order of rows from most to least sales, and
			 * follow the order of products from most bought to least bought.
			 */
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
			
			// This is the final query based on everything else
			System.out.println("2D query:\n" + query+"\n");
			s = currentCon.prepareStatement(query);
			
			rs = s.executeQuery();
			
			long endTime = System.nanoTime();
			System.out.println(""+ (endTime - startTime));
			
			/* Decided to just write the html to print in here, so that I don't have to go through all these <%%> tags
			 * everywhere throughout the jsp file. Basically makes the jsp file more pretty.
			 * 
			 */
			table = "<table border=\"1\">\n<tr>\n<th></th>";
						
			// Iterate through the products list and put them at the top of the columns of the formatted table
			for (String p : title) {
				table += "<th>"+p+"</th>\n";
			}
			table += "</tr>\n";
				
			// set up the first user as the currentUser we're on
			String currentUser = null;
			if (rs.next()) // If the query actually worked and we have things in the rs then set the current user to it
				currentUser = rs.getString("name");
			
			// Iterate through the rows (users or states) list
			for (String r : rowTitle) {
				// boolean set up so when we finish the purchases of one user
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
				// If this particular row did not buy any of the top ten products, then we have to
				// increase the result set anyway, so this is just a fail safe of that
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
