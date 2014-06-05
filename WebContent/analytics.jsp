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
<div style="margin-top: 5px; margin-bottom: 5px">
<% List<String> products = (List<String>)request.getAttribute("products"); 
   //TreeMap<String, int[]> map = (TreeMap<String, int[]>)request.getAttribute("rows");
   String rows = request.getParameter("rows");
   String cat = request.getParameter("category");
   String state = request.getParameter("state");
   List<Category> categories = (List<Category>)request.getAttribute("categories");
   String table = (String)request.getAttribute("table");%>
<div class="title2">Query Options</div>
<% // This is the form for queries %>
<form method="GET">
	
	<select name="rows">
		<option value="customers">Customers</option>
		<option value="states" <% if (rows != null && rows.equals("states")) { %>selected="selected" <%} %>>States</option>
	</select>
	State: 
	<%@ include file="WEB-INF/stateDropdown.jsp" %>
	Category: <select name="category">
	 	<option value="all">All Categories</option>
	 	<% for (Category c : categories) {%>
	 	<option value="<%=c.getName() %>" <% if (cat != null && cat.equals(c.getName())) { %>selected="selected" <%} %>><%=c.getName() %></option>
	 	<%} %>
	 </select>
	<input type="submit" value="Run Query"/>
</form>
<%=table %>
</div>
</body>
</html>
