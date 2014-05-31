package cse135.servlet;
import cse135.model.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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
		HttpSession session = req.getSession(true);
		
		int uid = (int)((User)session.getAttribute("currentSessionUser")).getId();
		List<Order> cart;
		try {
			cart = OrderDAO.list(uid);
		
			//HashMap<Product,Integer> map = (HashMap<Product,Integer>)session.getAttribute("cart");
			if (cart.size() != 0) {
				/*try {
					for (Map.Entry<Product, Integer> entry : map.entrySet()) {
						map.put(ProductDAO.find("id", (int)(entry.getKey().getId())), map.remove(entry.getKey()));
						
					}
				} catch (SQLException e) {}*/
				//session.setAttribute("cart", map);
				req.setAttribute("cart", cart);
				//req.setAttribute("cart", OrderDAO.insert(, pid, quantity, price))
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			req.getRequestDispatcher("productOrderError.jsp").forward(req, res);
			return;
		}
			if (req.getParameter("product") != null ) {
			//int sku = Integer.parseInt(req.getParameter("product"));
				String sku = req.getParameter("product");
			try {
				// Find's the product that the customer tries to buy
				Product p = ProductDAO.find("sku", sku);
				req.setAttribute("orderProd", p);
				req.getRequestDispatcher("productOrder.jsp").forward(req, res);
				return;
			} catch (SQLException e) {
				// If an error occurred, redirect
				e.printStackTrace();
				req.getRequestDispatcher("productOrderError.jsp").forward(req, res);
			}
		} else {
			// If someone tries to access this page without a product parameter, then redirect to products page
			res.sendRedirect("products");
		}
	}
	
	protected void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		int uid = (int)((User)session.getAttribute("currentSessionUser")).getId();
		//HashMap<Product,Integer> cart = (HashMap<Product,Integer>)session.getAttribute("cart");
		try {
			//cart = 
			Integer quantity = Integer.parseInt(req.getParameter("quantity"));
			if (quantity <= 0) {
				// Errors if quantity is less than 0
				throw new NumberFormatException();
			}
			int price = Integer.parseInt(req.getParameter("price"));
			long id = Long.parseLong(req.getParameter("id"));
			// Don't care about anything else, just need name and price;
			Product p = new Product(req.getParameter("name"), "0", 0, price);
			p.setId(id);
			OrderDAO.insert(uid, (int)id, quantity, price);
			/*if (cart == null) { // If there's nothing in the cart, make a new cart
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
			session.setAttribute("cart", cart);*/
			res.sendRedirect("products");
		} catch (NumberFormatException | SQLException e) {
			req.getRequestDispatcher("WEB-INF/productOrderError.jsp").forward(req, res);
		}
		
	}

}
