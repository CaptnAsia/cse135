<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="java.util.List" %>
<link rel="stylesheet" type="text/css" href="stylesheets/main.css">
<link rel="stylesheet" type="text/css" href="stylesheets/products.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Browse for Products</title>
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
    	<% if (session.getAttribute("currentSessionUser") != null) { %>
    	<div><input name="search"/></div>
    	<div><input type="Submit" value="Go"/></div>
    	<% } else { %>
    	<div>Please <a href="login">login</a> before you can search.</div>
    	<%} %>
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
        	<td><a href="#"><%= p.getName() %></a></td>
        	<td>$<%= p.getPrice() %></td>
        </tr>
        <% } } %>
    </table>
</body>
</html>