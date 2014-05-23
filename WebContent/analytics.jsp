<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="java.util.List" %>
<%@ page import="java.util.*" %>
<link rel="stylesheet" type="text/css" href="stylesheets/main.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sales Analytics</title>
</head>
<body>
<%@ include file="WEB-INF/header.jsp" %>
<div class="wrapper">
<div class="title1">Sales Analytics</div>
<% List<String> products = (List<String>)request.getAttribute("products"); 
   TreeMap<String, int[]> map = (TreeMap<String, int[]>)request.getAttribute("rows");
   String rows = request.getParameter("rows");
   List<Category> categories = (List<Category>)request.getAttribute("categories");%>
<div style="margin-top: 5px; margin-bottom: 5px"><div class="title2">Query Options</div>
<form method="GET">
	<select name="rows">
		<option value="customers">Customers</option>
		<option value="states" <% if (rows != null && rows.equals("states")) { %>selected="selected" <%} %>>States</option>
	</select>
	 Age: <select name="ages">
	 	<option value="0">All Ages</option>
	 	<option value="1">12-18</option>
	 	<option value="2">18-45</option>
	 	<option value="3">45-65</option>
	 	<option value="4">65+</option>
	 </select>
	 Category: <select name="category">
	 	<option value="all">All Categories</option>
	 	<% for (Category c : categories) {%>
	 	<option><%=c.getName() %></option>
	 	<%} %>
	 </select>
	<input type="submit" value="Run Query"/>
</form></div>
<table border="1">
	<tr>
		<th></th>
	<% for (String p : products) { %>
		<th><%=p %></th>
	<%} %>
	</tr>
	<% for (Map.Entry<String, int[]> row : map.entrySet()) { %>
	<tr>
		<td style="font-weight: bold"><%=row.getKey() %></td>
		<% for (int i = 0; i < row.getValue().length; i++) { %>
		<td>$<%=row.getValue()[i] %></td> <%} %>
	</tr><%} %>
</table>
</div>
</body>
</html>