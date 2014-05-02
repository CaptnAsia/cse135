package cse135.servlet;
import cse135.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.zip.DataFormatException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class BuyCartServlet extends HttpServlet{
	protected void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
				
				/*System.out.println("cardNumber = " + cardNumber);
				boolean pass = true;
				String num = "" + cardNumber;*/
				
				if ( card.length() != 16 ) {
					req.setAttribute("result", error);
					throw new NumberFormatException();
				} else {
					long cardNumber = Long.parseLong(card);
					req.setAttribute("Descartes", session.getAttribute("cart"));
					session.setAttribute("cart", null);
					//res.sendRedirect("buyConfirm.jsp");
					req.getRequestDispatcher("buyConfirm.jsp").forward(req, res);
					//System.out.println("Good card number");
				}
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
				req.setAttribute("result", error);
				req.getRequestDispatcher("buyConfirm.jsp").forward(req, res);
			}
		}
		
	}

}
