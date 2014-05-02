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
		res.sendRedirect("buyCart.jsp");
	}
	
	protected void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String error = "Unsuccessful purchase";
		
		try {
			long cardNumber = Long.parseLong((String)req.getParameter("cardNumber"));
			System.out.println("cardNumber = " + cardNumber);
			boolean pass = true;
			String num = "" + cardNumber;
			
			if ( num.length() != 16 ) {
				req.setAttribute("result", error);
				pass = false;
				throw new NumberFormatException();
			} else {
				req.setAttribute("result", new String("success"));
				HttpSession session = req.getSession(true);
				session.setAttribute("cart", null);
				res.sendRedirect("buyConfirm.jsp");
				//System.out.println("Good card number");
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			req.setAttribute("result", error);
			req.getRequestDispatcher("buyConfirm.jsp").forward(req, res);
		}
		//
		
	}

}
