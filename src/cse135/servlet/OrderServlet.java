package cse135.servlet;
import cse135.model.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cse135.model.Category;
import cse135.model.CategoryDAO;
import cse135.model.ProductDAO;
import cse135.model.User;
import cse135.model.UserDAO;
import cse135.model.Order;

public class OrderServlet extends HttpServlet{
	protected void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			if (req.getParameter("product") != null ) {
			int sku = Integer.parseInt(req.getParameter("product"));
			try {
				// Find's the product that the customer tries to buy
				Product p = ProductDAO.find("sku", sku);
				req.setAttribute("orderProd", p);
				req.getRequestDispatcher("productOrder.jsp").forward(req, res);
			} catch (SQLException e) {
				// If an error occured, redirect
				res.sendRedirect("orderError.jsp.");
			}
		} else {
			// If someone tries to access this page without a product parameter, then redirect to products page
			res.sendRedirect("products");
		}
	}
	
	protected void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		HashMap<Product,Integer> cart = (HashMap<Product,Integer>)session.getAttribute("cart");
		try {
			Integer quantity = Integer.parseInt(req.getParameter("quantity"));
			if (quantity <= 0) {
				// Errors if quantity is less than 0
				throw new NumberFormatException();
			}
			double price = Double.parseDouble(req.getParameter("price"));
			
			// Don't care about anything else, just need name and price;
			Product p = new Product(req.getParameter("name"), 0, 0, price, 0);
			if (cart == null) { // If there's nothing in the cart, make a new cart
				cart = new HashMap<Product,Integer>();
			} 
			
			if (cart.get(p) != null) {
				// if a product is already in the cart, just add the quantity of the old and the new
				cart.put(p, cart.get(p) + quantity);
			} else {
				// else just put in a new Product, Quantity mapping
				cart.put(p, quantity);
			}
			// Put the cart in the session
			session.setAttribute("cart", cart);
			res.sendRedirect("products");
		} catch (NumberFormatException e) {
			req.getRequestDispatcher("WEB-INF/productOrderError.jsp").forward(req, res);
		}
		
	}

}
