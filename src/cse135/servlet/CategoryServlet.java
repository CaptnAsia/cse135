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
		
		try {
			List<Category> categories = CategoryDAO.list();
			req.setAttribute("categories", categories); //categories list will be available as ${categories} in JSP
			req.getRequestDispatcher("/products.jsp").forward(req, res);
		} catch (SQLException e ) {
			throw new ServletException("Can't obtain categories from database", e);
		}
	}
}
