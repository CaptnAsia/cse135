package cse135.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cse135.model.User;
import cse135.model.UserDAO;

public class LoginServlet extends HttpServlet{
	public void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		if (session.getAttribute("currentSessionUser") != null) {
			req.getRequestDispatcher("products").forward(req,res);
		} else {
			req.getRequestDispatcher("login.jsp").forward(req,res);
		}
	}
	public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String name = req.getParameter("name");
		User user;
		try {
			// First try to find if the user exists
			System.out.println("Trying to find user");
			user = UserDAO.find(name);
			
			if (user != null) {
				 HttpSession session = req.getSession(true);	    
		         session.setAttribute("currentSessionUser",user); 
		         res.sendRedirect("/cse135");
			} else {
				String error = "The provided name " + name + " is not known.";
				req.setAttribute("error", error);
				req.getRequestDispatcher("login.jsp").forward(req, res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// If no user found of the given name then redirect to login page with error shown
			System.out.println("something happened");
			String error = "The provided name " + name + " is not known.";
			req.setAttribute("error", error);
			req.getRequestDispatcher("login.jsp").forward(req, res);
			//res.sendRedirect("login.jsp");
		}
		//res.sendRedirect("categories.jsp");
	}
}
