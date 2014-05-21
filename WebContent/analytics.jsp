<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Hashtable" %>
<link rel="stylesheet" type="text/css" href="stylesheets/main.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sales Analytics</title>
</head>
<body>
<%@ include file="WEB-INF/header.jsp" %>
<div class="wrapper">
<div class="title1">Sales Analytics</div>
<div>Products</div>
<% List<String> products = (List<String>)request.getAttribute("products"); 
   Map<String, int[]> map = (Map<String, int[]>)request.getAttribute("rows");%>
<table border="1">
	<tr>
		<th></th>
	<% for (String p : products) { %>
		<th><%=p %></th>
	<%} %>
	</tr>
	<% for (Map.Entry<String, int[]> row : map.entrySet()) { %>
	<tr>
		<td><%=row.getKey() %></td>
		<% for (int i = 0; i < 10; i++) { %>
		<td><%=row.getValue()[i] %></td> <%} %>
	</tr><%} %>
</table>
</div>
</body>
</html>