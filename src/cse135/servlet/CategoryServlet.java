package cse135.servlet;
import cse135.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CategoryServlet extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//res.sendRedirect("categories.jsp");
		//List<Category> categories = CategoryDAO.list();
		//req.setAttribute("categories", categories); //categories list will be available as ${categories} in JSP
		req.getRequestDispatcher("/products.jsp").forward(req, res);
	}
	
	public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String error = "";
		Category category;
		
		// See if the category exists in the database
		try { 
			// Checks if name was blank
			if (req.getParameter("name") == "") {
				error = "Please enter a name";
				throw new SQLException("No name entered");
			}
			if (req.getParameter("description") == "") {
				error = "Please enter a description";
				throw new SQLException("No description entered");
			}
			category = new Category(0, req.getParameter("name"),req.getParameter("description"));
			CategoryDAO.insert(category);
			res.sendRedirect("/cse135/categories");
		} catch (SQLException e) {
			e.printStackTrace();
			if ( error == "" )
				error = "This category already exists";
			req.setAttribute("error", error);
			req.getRequestDispatcher("categories.jsp").forward(req, res);
		}
	}
}
