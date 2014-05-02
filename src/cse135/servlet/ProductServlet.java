package cse135.servlet;
import cse135.model.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
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
		HttpSession session = req.getSession(true);
		User user = (User)session.getAttribute("currentSessionUser");
		
		String currCat = (String)req.getParameter("category");
		String filter = (String)req.getParameter("search");
		try {
			long catID;
			// If there's no category filter, search for all products
			if (currCat == null)
				catID = -1;
			else 
				catID = CategoryDAO.find(currCat);
			
			// List all the products, if there's a category filter, only list products
			// that are in that category
			List<Product> products = ProductDAO.list(catID);
			req.setAttribute("productList", products);
			
			// Set's the filter from the search bar
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
			req.getRequestDispatcher("productsOwnerConfirm.jsp").forward(req, res);
		}
		
	}
	
	public void doPost (HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		HttpSession session = req.getSession(true);
		String error = "";
		Product product;
		int sku = 0;
		double price = 0;
		int category = 0;
		long owner = 0;
		
		try {
			// Checks if any attributes were blank
			if (req.getParameter("name") == "") {
				throw new SQLException("No name entered");
			}
			if (req.getParameter("sku") == "") {
				throw new SQLException("No SKU entered");
			}
			if (req.getParameter("price") == "") {
				throw new SQLException("No price entered");
			}
			if (req.getParameter("delete") == null) {
				// need to set variables for posts that are not delete posts
				sku = Integer.parseInt(req.getParameter("sku"));
				price = Double.parseDouble(req.getParameter("price"));
				category = Integer.parseInt(req.getParameter("category"));
				owner = ((User)session.getAttribute("currentSessionUser")).getId();
			}
			if (req.getParameter("newProd") != null) {
				// First Case: Inserting a new Product
				// Create the product object
				product = new Product(req.getParameter("name"), sku, category, price, owner );
				
				// Write the query string, with "?" to help avoid potential SQL injections
				String query = "INSERT INTO products (name, sku, category, price, owner) " +
						"VALUES (?, ?, ?, ?, ?)";
				ProductDAO.alter(query, req.getParameter("name"), sku, category, price, owner, -1);
				req.setAttribute("product", product);
				req.getRequestDispatcher("productsOwnerConfirm.jsp").forward(req, res);
			} else if (req.getParameter("update") != null) {
				// Second Case: Updating an existing Product
				String query = "UPDATE products SET name = ?, sku = ?, category = ?, " +
						"price = ? WHERE id = ?";
				int temp = Integer.parseInt(req.getParameter("id"));
				
				product = new Product(req.getParameter("name"), sku, category, price, owner );
				ProductDAO.alter(query,req.getParameter("name"), sku, category, price, (long)-1, temp);
				/*HashMap<Product,Integer> map = (HashMap<Product,Integer>)session.getAttribute("cart");
				
				map.put(product, map.remove(ProductDAO.find("id", temp)));*/
				res.sendRedirect("products");
			} else if (req.getParameter("delete") != null) {
				// Third Case: Deleting an existing Product
				int temp = Integer.parseInt(req.getParameter("id"));
				String query = "DELETE FROM products WHERE id = ?";
				ProductDAO.alter(query, null, -1, -1, -1, -1, temp);
				res.sendRedirect("products");
			}
			
		} catch (SQLException | NumberFormatException e) {
			
			// If anything went wrong, redirect to error page with an error
			e.printStackTrace();
			error = "Failure to insert new product";
			req.setAttribute("error", error);
			req.getRequestDispatcher("productsOwnerConfirm.jsp").forward(req, res);
		}
	}
}
