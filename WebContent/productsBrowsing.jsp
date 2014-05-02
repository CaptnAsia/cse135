<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>
<link rel="stylesheet" type="text/css" href="stylesheets/main.css">
<link rel="stylesheet" type="text/css" href="stylesheets/products.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Browse for Products</title>
</head>
<body>
<%@ include file="WEB-INF/header.jsp" %>
<div class="wrapper">
	<div id="sidebar"><%@ include file="WEB-INF/categoriesSidebar.jsp" %></div>
	<div id="products">
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
    	<div>Please <a href="login">log in</a> first.</div>
    	<%} %>
    </form>
    <div style="height:10px;"></div>
	<table border="1">
    	<tr>
        	<th>SKU</th>
        	<th>Name</th>
        	<th>Price</th>
        	<th>Buy</th>
        </tr>
        <%
        DecimalFormat df = new DecimalFormat("#.00");
        String filterName = (String)request.getAttribute("filter");
        System.out.println("filterName = '" + filterName + "'");
        List<Product> products = (List<Product>)request.getAttribute("productList");
        for(Product p : products) {
        	if (filterName == "" || p.getName().toLowerCase().contains(filterName.toLowerCase()) ) {%>
        <tr>
        	<td><%= p.getSku() %></td>
        	<td><%= p.getName() %></td>
        	<td>$<%= df.format(p.getPrice()) %></td>
        	<td><a href="order?product=<%=p.getSku()%>">Buy</a></form>
        </tr>
        <% } } %>
    </table>
    </div>
    </div>
</body>
</html>