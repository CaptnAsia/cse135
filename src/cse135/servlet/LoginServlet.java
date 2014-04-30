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
	public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String name = req.getParameter("name");
		User user;
		try {
			user = UserDAO.find(name);
			
			if (user != null) {
				 HttpSession session = req.getSession(true);	    
		         session.setAttribute("currentSessionUser",user); 
		         res.sendRedirect("menu.html");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res.sendRedirect("login.jsp");
		}
		//res.sendRedirect("categories.jsp");
	}
}
