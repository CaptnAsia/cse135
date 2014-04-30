package cse135.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cse135.model.User;
import cse135.model.UserDAO;

public class SignupServlet extends HttpServlet {
	public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String output = "You have successfully signed up";
		User user;
		PrintWriter out = res.getWriter();
			// See if the user exists in the database
			try { 
				UserDAO.find(req.getParameter("name"));
				
				// Execute this line if a user is found
				output = "Your signup failed because of existing user";
			} catch (SQLException e) {
				try {
					// See if age is an integer
					int a = Integer.parseInt(req.getParameter("age"));
					boolean own = true;
					
					// Create a new user object to put into the database
					if (req.getParameter("owner") == "customer")
						own = false;
					user = new User(req.getParameter("name"),a,req.getParameter("state"),own);
					UserDAO.insert(user);
					
				} catch (NumberFormatException | SQLException ex) {output = "Your signup failed because of something else"; e.printStackTrace();}
			}
		
	    out.println (output);
	}

}
