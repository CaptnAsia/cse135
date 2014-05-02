package cse135.servlet;
import cse135.model.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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

public class OrderServlet extends HttpServlet{
	protected void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			if (req.getParameter("product") != null ) {
			int sku = Integer.parseInt(req.getParameter("product"));
			System.out.println(sku);
			try {
				Product p = ProductDAO.find("sku", sku);
				req.setAttribute("orderProd", p);
				req.getRequestDispatcher("productOrder.jsp").forward(req, res);
			} catch (SQLException e) {
				res.sendRedirect("orderError.jsp.");
			}
		} else {
		res.sendRedirect("products");
		}
	}
	
	protected void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		ArrayList<Order> cart = (ArrayList<Order>)session.getAttribute("cart");
		try {
			int quantity = Integer.parseInt(req.getParameter("quantity"));
			double price = Double.parseDouble(req.getParameter("price"));
			Order o = new Order((String)req.getParameter("name"),price,quantity);
			if (cart == null) {
				cart = new ArrayList<Order>();
			}
			boolean exists = false;
			for (Order temp : cart ) {
				if (temp.getName().equals(o.getName())) {
					temp.setAmount(temp.getAmount()+o.getAmount());
					exists = true;
				}
			}
			if (!exists)
				cart.add(o);
			session.setAttribute("cart", cart);
			res.sendRedirect("products");
			
		} catch (NumberFormatException e) {
			req.getRequestDispatcher("productOrder.jsp").forward(req, res);
		}
		
	}

}
