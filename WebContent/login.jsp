<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Here</title>
</head>
<body>
	<h1>Login</h1>
	<form action="loginAttempt" method="POST">
	Name:
	<div><input name="name"/></div>
	<p><input type="Submit" value="Log in"/>
	</form>
	New user? <a href="/cse135/signup">Sign up here</a>
</body>
</html>