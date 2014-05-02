<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="java.text.DecimalFormat" %>
<link rel="stylesheet" type="text/css" href="stylesheets/main.css">
<link rel="stylesheet" type="text/css" href="stylesheets/products.css">
<link rel="stylesheet" type="text/css" href="stylesheets/cart.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><% if (request.getAttribute("result") == null) { %>Checkout Successful<% } else { %>Error<% } %></title>
</head>
<body>
<div class="header"><%@ include file="WEB-INF/header.jsp" %></div>
<div class="wrapper" style="padding-top: 10px;">
	<% if (request.getAttribute("result") != null) {%>
	<%= request.getAttribute("result")%>
	<% } else { %>
Successful purchase. Thank you for shopping with us!
<div class="title2">What you bought:</div>
<% cart = (HashMap<Product,Integer>)request.getAttribute("Descartes"); %>
<%@ include file="WEB-INF/cart.jsp" %><% } %>
</div>
</body>
</html>