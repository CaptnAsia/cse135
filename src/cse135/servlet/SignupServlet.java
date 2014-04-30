package cse135.servlet;

import java.io.IOException;
//import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cse135.model.User;
import cse135.model.UserDAO;

public class SignupServlet extends HttpServlet {
	
	public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("signup.jsp").forward(req,res);
	}
	
	public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String output = "You have successfully signed up";
		User user;
		// PrintWriter out = res.getWriter();
			// See if the user exists in the database
			try { 
				int a = Integer.parseInt(req.getParameter("age"));
				boolean own = true;
				
				// Create a new user object to put into the database
				if (req.getParameter("owner") == "customer")
					own = false;
				user = new User(req.getParameter("name"),a,req.getParameter("state"),own);
				
				UserDAO.insert(user);
				HttpSession session = req.getSession(true);
				session.setAttribute("currentSessionUser",user); 
				res.sendRedirect("afterSignup.jsp");
				
			} catch (SQLException e) {
				e.printStackTrace();
				output = "Your signup failed";
				req.setAttribute("message", output);
				req.getRequestDispatcher("afterSignup.jsp").forward(req, res);
				//output = "Your signup failed";
				//req.setAttribute("message", output);
				//req.getRequestDispatcher("afterSignup.jsp").forward(req, res);
			}
		
		//String error = "The provided name " + name + " is not known.";
	    //out.println (output);
	}

}
