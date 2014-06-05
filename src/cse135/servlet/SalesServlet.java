package cse135.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cse135.model.Category;
import cse135.model.CategoryDAO;
import cse135.model.SaleDAO;

public class SalesServlet extends HttpServlet {
	protected void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		//TreeMap<String,int[]> map;
		ResultSet rs;
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<String> rowTitle = new ArrayList<String>();
		ArrayList<Integer> pids = new ArrayList<Integer>();
		String table;// = new String();
		//String rowOffset = req.getParameter("rowRange");
		
		try {
			List<Category> categories = (List<Category>)CategoryDAO.list();
			req.setAttribute("categories", categories);
			String rows = req.getParameter("rows");
			String cat = req.getParameter("category");
			String states = req.getParameter("state");
			
			if (rows == null || rows.equals("customers"))
				rows = "users.name";
			else if (rows.equals("states"))
				rows = "users.state";
			else {
				res.sendRedirect("products");
				return;
			}
			
			table = SaleDAO.listProducts(rows, list, pids, rowTitle, cat, states);
			//System.out.println(table);
			/*// Start writing the html for the table
			table = "<table border=\"1\">\n<tr>\n<th></th>";
			
			// Header of the columns: the products to list
			for (String p : list) {
				table += "<th>"+p+"</th>\n";
			}
			table += "</tr>\n";
			
			// get the first element of the set
			rs.next();
			
			// set up the first user as the currentUser we're on
			String currentUser = rs.getString("name");
			
			// Iterate through the rows
			for (String r : rowTitle) {
				// boolean set up so when we finish the purchases of one user
				boolean skip = false;
				// new row
				table += "<tr>\n<td style=\"font-weight: bold\">"+r+"</td>/n";
				// Iterate through the top 10 pids
				for (Integer i : pids) {
					table += "<td>$";
					// If the result set isn't already on the next row or the pid is equal to this cell's pid
					if (!skip && rs.getInt("pid") == i) {
						// populate this cell with the total sales.
						table += "" + rs.getString("sum");
						// then get the next row of the rs.
						rs.next();
						// then check if, by going to the next set, we reached the end of the set
						// or we finished iterating through this user's purchases
						if (rs.getString("name") == null || !rs.getString("name").equals(currentUser)) {
							// if ^ is true, then skip the rest of the products and just populate those cells with 0's
							skip = true;
							// change the current user to the next user
							currentUser = rs.getString("name");
						}
					} else {
						// if the user didn't buy any of this product, just show 0
						table += "0";
					}
					table += "</td>\n";
				}
				table += "</tr>\n";
			}
			rs.close();
			table += "</table>\n";*/
			
			/* <table border="1">
	<tr>
		<th></th>
	<% for (String p : products) { %>
		<th><%=p %></th>
	<%} %>
	</tr>
	<% for (Map.Entry<String, int[]> row : map.entrySet()) { %>
	<tr>
		<td style="font-weight: bold"><%=row.getKey() %></td>
		<% for (int i = 0; i < row.getValue().length; i++) { %>
		<td>$<%=row.getValue()[i] %></td> <%} %>
	</tr><%} %>
</table>*/
			
			req.setAttribute("table", table);
			/**TODO: SET THE TABLE TO A ATTTRIBUTE AND CHANGE THE ANALYTICS PAGE
			 */
			
			
			req.setAttribute("products", list);
			req.setAttribute("rows", rows);
			req.setAttribute("cat", cat);
			req.getRequestDispatcher("analytics.jsp").forward(req, res);
		} catch (SQLException e) {
			e.printStackTrace();
			res.sendRedirect("products");
		}
	}
	
	protected void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("POST");
		
		/*TreeMap<String,int[]> map;
		ArrayList<String> list = new ArrayList<String>();
		String rowOffset = req.getParameter("rowRange");
		String prodOffset = req.getParameter("prodRange");
		try {
			List<Category> categories = (List<Category>)CategoryDAO.list();
			req.setAttribute("categories", categories);
			String rows = req.getParameter("rows");
			String ages = req.getParameter("ages");
			String cat = req.getParameter("category");
			String state = req.getParameter("state");
			
			// Sets which type of rows to show.
			if (rows.equals("null") || rows.equals("customers"))
				rows = "users.name";
			else if (rows.equals("states"))
				rows = "users.state";
			else {
				res.sendRedirect("products");
				return;
			}
			
			// r and p are the row/col offsets
			Integer r = (Integer.parseInt(rowOffset));
			Integer p = (Integer.parseInt(prodOffset));
			if (req.getParameter("prodSubmit") != null) {
				// This means the 'next 10 column' button submitted
				r -= 20; 
				System.out.println("HELLO PRODUCT NEXT BUTTON SUBMITTED rows: "+r+"\nproducts: "+p);
			}else if (req.getParameter("rowSubmit") != null) {
				// This means the 'next 20 row' button submitted
				p -= 10;
				System.out.println("ROWS NEXT BUTTON SUBMITTED rows: "+r+"\nproducts: "+p);
			}
			// set the offset attributes for the jsp to load
			req.setAttribute("prodOffset", p);
			req.setAttribute("rowOffset", r);
			
			// do the query
			map = SaleDAO.listProducts(rows, list, r, p, ages, cat, state);
			req.setAttribute("products", list);
			req.setAttribute("rows", map);
			req.setAttribute("ages", ages);
			req.setAttribute("cat", cat);
			req.setAttribute("state", state);
			
			// This string array is for us to see what attributes were selected for the initial query
			String[] queries = new String[4];
			queries[0] = (rows.equals("users.name")) ? "Customers" : "States";
			queries[1] = (req.getParameter("ages").equals("null") || req.getParameter("ages").equals("0")) ? "All" : req.getParameter("ages");
			if ( queries[1] != null && !queries[1].equals("null") && !queries[1].equals("All") ) {
			     switch (Integer.parseInt(queries[1])) {
			       case 1: queries[1] = "12 - 18"; break;
			       case 2: queries[1] = "18 - 45"; break;
			       case 3: queries[1] = "45 - 65"; break;
			       case 4: queries[1] = "65+"; break;
			     }  
			   }
			queries[2] = (req.getParameter("category").equals("null")) ? "All" : req.getParameter("category");
			queries[3] = (req.getParameter("state").equals("null")) ? "All" : req.getParameter("state");
			req.setAttribute("next", queries);
			
			req.getRequestDispatcher("analytics.jsp").forward(req, res);
			return;
		} catch (SQLException e) {
			e.printStackTrace();
			res.sendRedirect("products");
		}*/
	}
}
