<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<% Product p = (Product)request.getAttribute("orderProd");%>
<link rel="stylesheet" type="text/css" href="stylesheets/main.css">
<link rel="stylesheet" type="text/css" href="stylesheets/products.css">
<link rel="stylesheet" type="text/css" href="stylesheets/cart.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Order Products</title>
</head>
<body>
<%@ include file="/WEB-INF/header.jsp" %>
<div class="wrapper">
<div style="margin-top: 10px">An error occurred while trying to order your items.</div>
</div>
</body>
</html>