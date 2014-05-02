<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="java.text.DecimalFormat" %>
<link rel="stylesheet" type="text/css" href="stylesheets/main.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Successfully Inserted a New Product</title>
</head>
<body>
<div class="header"><%@ include file="WEB-INF/header.jsp" %></div>
<div class="wrapper">
	<% if ( request.getAttribute("error") != null ) { %>
	<div style="height:20px;"></div>
	<%= request.getAttribute("error") %>
    <% } else { %>
	<div class="title2">Confirmation Page</div>
	<div style="height:10px;"></div>
	<% Product p = (Product)request.getAttribute("product");
	   DecimalFormat df = new DecimalFormat("#.00");%>
	<div>Successfully added new product!</div>
	<div style="height:10px;"></div>
	<div>Name:  <%= p.getName() %></div>
	<div>SKU:   <%= p.getSku() %></div>
	<div>Price: $<%= df.format(p.getPrice()) %></div>
	<% } %>
</div>
</body>
</html>