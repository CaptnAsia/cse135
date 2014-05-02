<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DecimalFormat" %>
<% Product p = (Product)request.getAttribute("orderProd");%>
<link rel="stylesheet" type="text/css" href="stylesheets/main.css">
<link rel="stylesheet" type="text/css" href="stylesheets/products.css">
<link rel="stylesheet" type="text/css" href="stylesheets/cart.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Order Products</title>
</head>
<body>
<%@ include file="WEB-INF/header.jsp" %>
<div class="wrapper">
	<div class="title2">Shopping Cart</div>
	<%@ include file="WEB-INF/cart.jsp" %>
	
	<div class="title2">New Order</div>
	<form action="order" method="POST">
		<input type="hidden" name="name" value="<%=p.getName() %>"/>
		<input type="hidden" name="price" value="<%=df.format(p.getPrice())%>"/>
		<input type="hidden" name="id" value="<%=p.getId() %>"/>
		<table>
			<th>Name</th>
			<th>Price</th>
			<th>Amt</th>
		
			<tr>
			<td class="name"><%=p.getName() %></td>
			<td class="price">$<%=df.format(p.getPrice()) %></td>
			<td class="quantity"><input name="quantity" value="1"/></td>
			</tr>
		</table>
		<div id="submit"><input type="Submit" name="addToCart" value="Add to Cart"></div>
	</form>
</div>
</body>
</html>