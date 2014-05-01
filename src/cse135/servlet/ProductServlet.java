package cse135.servlet;
import cse135.model.*;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cse135.model.Category;
import cse135.model.CategoryDAO;
import cse135.model.User;
import cse135.model.UserDAO;

public class ProductServlet extends HttpServlet{
	public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("productsOwner.jsp").forward(req,res);
	}
	public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String error = "";
		Product product;
		
		// See if the product exists in the database
		try {
			// Checks if name was blank
			if (req.getParameter("name") == "") {
				throw new SQLException("No name entered");
			}
			if (req.getParameter("sku") == "") {
				throw new SQLException("No SKU entered");
			}
			if (req.getParameter("price") == "") {
					throw new SQLException("No price entered");
			}
			double price = Double.parseDouble(req.getParameter("price"));
			
			product = new Product(req.getParameter("name"), 0, req.getParameter("category"), price, new String("owner"));
			ProductDAO.insert(product);
			res.sendRedirect("product");
		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
			error = "An error occured.";
			req.setAttribute("error", error);
			req.getRequestDispatcher("productsOwner.jsp").forward(req, res);
		}
	}
}
