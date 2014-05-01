package cse135.servlet;
import cse135.model.*;
import java.io.IOException;
import java.sql.SQLException;
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

public class ProductServlet extends HttpServlet{
	protected void doGet (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String currCat = "";
		String filter = "";
		HttpSession session = req.getSession(true);
		User user = (User)session.getAttribute("currentSessionUser");
		
		/*String currCat = (String)req.getAttribute("currCat");
        long catID = CategoryDAO.find(currCat);
        String filterName = (String)req.getAttribute("filter");
        System.out.println("filterName = '" + filterName + "'");
        List<Product> products = ProductDAO.list();*/
		
		if ( req.getParameterNames().hasMoreElements() ) {
			currCat = req.getParameterValues(req.getParameterNames().nextElement())[0];
		}
		filter = (String)req.getParameter("search");
		try {
			long catID;
			if (currCat == null)
				catID = -1;
			else
				catID = CategoryDAO.find(currCat);
			List<Product> products = ProductDAO.list(catID);
			req.setAttribute("productList", products);
			if ( filter == null ) {
				req.setAttribute("filter", "");
			}
			else {
				req.setAttribute("filter", filter.trim());
			}
			if (user != null && user.isOwner()) 
				req.getRequestDispatcher("productsOwner.jsp").forward(req,res);
			else
				req.getRequestDispatcher("productsBrowsing.jsp").forward(req,res);
		} catch (SQLException e) {
			String error = "An error occured while trying display the products.";
			req.setAttribute("error", error);
			req.getRequestDispatcher("productsOwnerError.jsp").forward(req, res);
		}
		//req.setAttribute("currCat", currCat);
		
	}
	
	public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		String error = "";
		Product product;
		
		try {
			// Checks if name was blank
			if (req.getParameter("name") == "") {
				throw new SQLException("No name entered");
			}
			if (req.getParameter("sku") == "") {
				throw new SQLException("No SKU entered");
			}
			if (req.getParameter("price") == "") {
				throw new SQLException("No price entered");
			}
			int sku = Integer.parseInt(req.getParameter("sku"));
			double price = Double.parseDouble(req.getParameter("price"));
			int category = Integer.parseInt(req.getParameter("category"));
			long owner = ((User)session.getAttribute("currentSessionUser")).getId();
			
			product = new Product(req.getParameter("name"), sku, category, price, owner );
			ProductDAO.insert(product);
			req.setAttribute("product", product);
			req.getRequestDispatcher("productsOwnerConfirm.jsp").forward(req, res);
			//res.sendRedirect("productsOwnerConfirm.jsp");
			
		} catch (SQLException | NumberFormatException e) {
			e.printStackTrace();
			error = "Failure to insert new product";
			req.setAttribute("error", error);
			req.getRequestDispatcher("productsOwnerError.jsp").forward(req, res);
		}
	}
}
