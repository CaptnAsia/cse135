<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="stylesheets/main.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Signup</title>
</head>
<body>
<div class="header"><%@ include file="WEB-INF/header.jsp" %></div>
<div class="wrapper" style="padding-top: 10px;">
	<% if (request.getAttribute("result") != null) {%>
	<%= request.getAttribute("result")%>
	<% } else { %>
Successful purchase. Thank you for shopping with us! <% } %>
</div>
</body>
</html>