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
	<div class="products"><div class="title2">Products</div>
	
	<table border="1">
    	<tr>
        	<th>SKU</th>
        	<th>Name</th>
        	<th>Price</th>
        </tr>
        <tr>
        	<td>000</td>
        	<td>Christian Fries</td>
        	<td>$10</td>
        </tr>
    </table>
    <div style="height:20px;"></div>
    <div><h2>Add New Product</h2></div>
    	<form action="products" method="POST">
        	<div>Product Name:</div>
        	<div><input name="name"/></div>
        	<div>SKU:</div>
        	<div><input name="sku"/></div>
        	<div>Price:</div>
        	<div><input name="price"/></div>
        	<div>Category:</div>
			<select name="category">
			<% List<Category> cats = CategoryDAO.list();
            for(Category c : cats) {%>
			<option value="<%=c.getId()%>"><%=c.getName()%></option>
			<% } %>
			</select>
        	<div><input type="Submit" name="newProd" value="Submit"/></div>
        </form>
</div>
</div>
</body>
</html>