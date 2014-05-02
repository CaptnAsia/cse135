<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DecimalFormat" %>
<link rel="stylesheet" type="text/css" href="stylesheets/main.css">
<link rel="stylesheet" type="text/css" href="stylesheets/products.css">
<link rel="stylesheet" type="text/css" href="stylesheets/cart.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Shopping Cart</title>
</head>
<body>
<%@ include file="WEB-INF/header.jsp" %>
<div class="wrapper">
	<%@ include file="WEB-INF/cart.jsp" %>
	
	<% if ( session.getAttribute("cart") != null ) { %> 
	<form action="buyCart" method="POST">
		<div>Enter your credit card number nigga:</div>
    	<div><input name="cardNumber"/></div>
    	<div style="height:5px;"></div>
    	<div><input type="Submit" name="cardNum" value="Purchase"/></div>
    </form>
	<% } %>
	
</div>
</body>
</html>