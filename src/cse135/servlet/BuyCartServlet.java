package cse135.servlet;
import cse135.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class BuyCartServlet extends HttpServlet{
	protected void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		HashMap<Product,Integer> map = (HashMap<Product,Integer>)session.getAttribute("cart");
		
		if ((map) != null) {
			/*try {
				List<Product> prods = ProductDAO.list(-1);
				for (Map.Entry<Product, Integer> entry : map.entrySet()) {
					if (prods.contains(entry.getKey())) {
							prods.
					}
					map.put(ProductDAO.find("id", (int)(entry.getKey().getId())), map.remove(entry.getKey()));
					
				}
			} catch (SQLException e) {}*/
			session.setAttribute("cart", map);
		}
		req.getRequestDispatcher("buyCart.jsp").forward(req, res);
	}
	
	protected void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String error = "Unsuccessful purchase";
		HttpSession session = req.getSession(true);
		if (session.getAttribute("currentSessionUser") == null) {
			res.sendRedirect("login");
		} else {
			try {
				String card = (String)req.getParameter("cardNumber");
				card = card.replace(" ", "");
				card = card.replace("-", "");
				
				// Simple card check if the length of the card is 16 digits
				if ( card.length() != 16 ) {
					req.setAttribute("result", error);
					throw new NumberFormatException();
				} else {
					// Also checks if the string is actually a number
					Long.parseLong(card);
					req.setAttribute("Descartes", session.getAttribute("cart"));
					HashMap<Product,Integer> map = (HashMap<Product,Integer>)session.getAttribute("cart");
					for (Map.Entry<Product, Integer> cartList : map.entrySet()) {
						User current = (User)session.getAttribute("currentSessionUser");
						SaleDAO.add(current.getId(),cartList.getKey().getId(),cartList.getValue(),cartList.getKey().getPrice());
					}
					session.setAttribute("cart", null);
					req.getRequestDispatcher("buyConfirm.jsp").forward(req, res);
				}
				
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
				req.setAttribute("result", error);
				req.getRequestDispatcher("buyConfirm.jsp").forward(req, res);
			}
		}
		
	}

}
