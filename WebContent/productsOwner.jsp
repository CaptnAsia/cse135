<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="java.util.List" %>
<link rel="stylesheet" type="text/css" href="stylesheets/main.css">
<link rel="stylesheet" type="text/css" href="stylesheets/products.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Products</title>
</head>
<body>

<%@ include file="WEB-INF/header.jsp" %>
<div class="wrapper">
	<div class="sidebar"><%@ include file="WEB-INF/categoriesSidebar.jsp" %></div>
	<div class="products">
	<div class="title2">Products</div>
    <div style="height:10px;"></div>
    <form action="products" method="GET">
    	<%if (request.getParameter("category") != null) { %>
    	<input type="hidden" name="category" value="<%=request.getParameter("category") %>"/> <%} %>
    	<div>Search (by Name):</div>
    	<div><input name="search"/></div>
    	<div><input type="Submit" value="Go"/></div>
    </form>
    <div style="height:10px;"></div>
	<table border="1">
    	<tr>
        	<th>SKU</th>
        	<th>Name</th>
        	<th>Price</th>
        </tr>
        <%
        String filterName = (String)request.getAttribute("filter");
        System.out.println("filterName = '" + filterName + "'");
        List<Product> products = (List<Product>)request.getAttribute("productList");
        for(Product p : products) {
        	if (filterName == "" || p.getName().toLowerCase().contains(filterName.toLowerCase()) ) {%>
        <tr>
        	<td><%= p.getSku() %></td>
        	<td><%= p.getName() %></td>
        	<td>$<%= p.getPrice() %></td>
        </tr>
        <% } } %>
    </table>
    <div style="height:10px;"></div>
    <div><h2>Add New Product</h2></div>
    	<form action="products" method="POST">
    		<div>Category:</div>
			<select name="category">
			<% List<Category> cats = CategoryDAO.list();
			for(Category c : cats) {%>
			<option value="<%=c.getId()%>"><%=c.getName()%></option>
			<% } %>
			</select>
			<div style="height:10px;"></div>
        	<div>Product Name:</div>
        	<div><input name="name"/></div>
        	<div>SKU:</div>
        	<div><input name="sku"/></div>
        	<div>Price:</div>
        	<div><input name="price"/></div>
        	<div style="height:10px;"></div>
        	<div><input type="Submit" name="newProd" value="Submit"/></div>
        </form>
	</div>
</div>
</body>
</html>