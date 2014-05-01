package cse135.servlet;
import cse135.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CategoryServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//System.out.println("doGet()");
		List<Category> categories = null;
		Hashtable<Long, Boolean> canDelete = null;
		HttpSession session = req.getSession(true);
		User user = (User)session.getAttribute("currentSessionUser");
		// Checks if there is a user present, as well as if the user is an owner
		if (user == null || !user.isOwner()) {
			// If not then let the .jsp file know that the user doesn't have permission to view this page
			req.setAttribute("permission", false);
			req.getRequestDispatcher("categories.jsp").forward(req, res);
		} else {
			// Else everything goes as planned
			try {
				categories = CategoryDAO.list();
				canDelete = ProductDAO.categories();
				
			} catch (SQLException e){res.sendRedirect("products"); }
			req.setAttribute("list", categories);
			req.setAttribute("permission", true);
			req.setAttribute("delete", canDelete);
			req.getRequestDispatcher("categories.jsp").forward(req, res);
		}
		
	}
	
	public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String error = "";
		//Category category;
		System.out.println("Posting");
		// See if the category exists in the database
		try {
			String query;
			if (req.getParameter("name") == "") {
				throw new SQLException("No name entered");
			}
			if (req.getParameter("description") == "") {
				throw new SQLException("No description entered");
			}
			if (req.getParameter("newCat") != null) {
				// First case handles if a new Category tries to be inserted into the database
				query = "INSERT INTO categories (name, description) VALUES (" + "'" + 
						req.getParameter("name") + "', '" + req.getParameter("description") + "')";
				CategoryDAO.alter(query);
				res.sendRedirect("categories");
			} else if (req.getParameter("update") != null) {
				// Second case handles if the user tries to update a category
				query = "UPDATE categories SET name = '" + req.getParameter("name") + 
						"', description = '" + req.getParameter("description") + 
						"' WHERE id = " + req.getParameter("id");
				CategoryDAO.alter(query);
				res.sendRedirect("categories");
			} else if (req.getParameter("delete") != null) {
				query = "DELETE FROM categories WHERE id = " + req.getParameter("id");
				CategoryDAO.alter(query);
				res.sendRedirect("categories");
			}
		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
			error = "An error occured when trying to alter the categories.";
			req.setAttribute("error", error);
			req.getRequestDispatcher("categoriesError.jsp").forward(req, res);
		}
	}
}
