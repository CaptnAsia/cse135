<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="stylesheets/main.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Here</title>
</head>
<body>
<%@ include file="WEB-INF/header.jsp" %>
<div class="wrapper">
	<div class="title1">Login</div>
	<% if (request.getAttribute("error") != null) { %>
		<div style="margin: 10px 10px 10px 0px; color: red;"><%=request.getAttribute("error") %></div>
		<% } %>
	<form action="login" method="POST">
	<div>Name:</div>
	<div><input name="name"/></div>
	<div><input type="Submit" value="Log in"/></div>
	</form>
	<div>New user? <a href="/cse135/signup">Sign up here</a></div>
</div>
</body>
</html>