package cse135.servlet;

import java.io.IOException;
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
		TreeMap<String,int[]> map;
		ArrayList<String> list = new ArrayList<String>();
		String rowOffset = req.getParameter("rowRange");
		
		try {
			List<Category> categories = (List<Category>)CategoryDAO.list();
			req.setAttribute("categories", categories);
			String rows = req.getParameter("rows");
			if (rows == null || rows.equals("customers"))
				rows = "users.name";
			else if (rows.equals("states"))
				rows = "users.state";
			else {
				res.sendRedirect("products");
				return;
			}
			map = SaleDAO.listProducts(rows,list,0, 0);
			req.setAttribute("products", list);
			req.setAttribute("rows", map);
			req.getRequestDispatcher("analytics.jsp").forward(req, res);
		} catch (SQLException e) {
			e.printStackTrace();
			res.sendRedirect("products");
		}
	}
	
	protected void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		TreeMap<String,int[]> map;
		ArrayList<String> list = new ArrayList<String>();
		String rowOffset = req.getParameter("rowRange");
		String prodOffset = req.getParameter("prodRange");
		try {
			List<Category> categories = (List<Category>)CategoryDAO.list();
			req.setAttribute("categories", categories);
			String rows = req.getParameter("rows");
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
			map = SaleDAO.listProducts( rows, list, r, p);
			req.setAttribute("products", list);
			req.setAttribute("rows", map);
			
			// This string array is for us to see what attributes were selected for the initial query
			String[] queries = new String[3];
			queries[0] = (rows.equals("users.name")) ? "Customers" : "States";
			queries[1] = (req.getParameter("ages").equals("null") || req.getParameter("ages").equals("0")) ? "All" : req.getParameter("ages");
			queries[2] = (req.getParameter("category").equals("null")) ? "All" : req.getParameter("category");
			req.setAttribute("next", queries);
			
			req.getRequestDispatcher("analytics.jsp").forward(req, res);
			return;
		} catch (SQLException e) {
			e.printStackTrace();
			res.sendRedirect("products");
		}
	}
}
