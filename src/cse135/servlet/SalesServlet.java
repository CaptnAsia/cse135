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
		try {
			List<Category> categories = (List<Category>)CategoryDAO.list();
			req.setAttribute("categories", categories);
			String rows = req.getParameter("rows");
			if (rows == null || rows.equals("customers"))
				rows = "users.name";
			else if (rows.equals("states"))
				rows = "users.state";
			else
				res.sendRedirect("products");
			map = SaleDAO.listProducts("", rows, list);
			req.setAttribute("products", list);
			req.setAttribute("rows", map);
			req.getRequestDispatcher("analytics.jsp").forward(req, res);
		} catch (SQLException e) {
			e.printStackTrace();
			res.sendRedirect("products");
		}
	}
	
	protected void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
	}
}
