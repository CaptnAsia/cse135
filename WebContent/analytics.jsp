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
   TreeMap<String, int[]> map = (TreeMap<String, int[]>)request.getAttribute("rows");
   String rows = request.getParameter("rows");
   String ages = request.getParameter("ages");
   String cat = request.getParameter("category");
   List<Category> categories = (List<Category>)request.getAttribute("categories");
   String attr[] = new String[4];
   attr[0] = (request.getParameter("rows") != null && request.getParameter("rows").equals("states")) ? "States" : "Customers";
   attr[1] = (request.getParameter("ages") != null) ? request.getParameter("ages") : "0";
   if ( attr[1] != null && !attr[1].equals("null")) {
     switch (Integer.parseInt(attr[1])) {
       case 1: attr[1] = "12 - 18"; break;
       case 2: attr[1] = "18 - 45"; break;
       case 3: attr[1] = "45 - 65"; break;
       case 4: attr[1] = "65+"; break;
     }  
   }
   attr[2] = (request.getParameter("category") != null) ? request.getParameter("category") : "all";
   
   System.out.println("rows = " + rows);
   System.out.println("ages = " + ages);
   System.out.println("cat = " + cat);
   
   // If the next button is clicked, you can't change the current queries
   if (request.getAttribute("next") != null) { 
   attr = (String[])request.getAttribute("next"); %>
<b><%=attr[0] %></b> Age: <b><%=attr[1] %></b> Category: <b><%=attr[2] %></b>
<% } else { %>
<div class="title2">Query Options</div>
<% // This is the form for queries %>
<form method="GET">
<% System.out.println("JSP"); %>
	<select name="rows">
		<option value="customers">Customers</option>
		<option value="states" <% if (rows != null && rows.equals("states")) { %>selected="selected" <%} %>>States</option>
	</select>
	 Age: <select name="ages">
	 	<option value="0" <% if (ages != null && ages.equals("0")) { %>selected="selected" <%} %>>All Ages</option>
	 	<option value="1" <% if (ages != null && ages.equals("1")) { %>selected="selected" <%} %>>12-18</option>
	 	<option value="2" <% if (ages != null && ages.equals("2")) { %>selected="selected" <%} %>>18-45</option>
	 	<option value="3" <% if (ages != null && ages.equals("3")) { %>selected="selected" <%} %>>45-65</option>
	 	<option value="4" <% if (ages != null && ages.equals("4")) { %>selected="selected" <%} %>>65+</option>
	 </select>
	 Category: <select name="category">
	 	<option value="all">All Categories</option>
	 	<% for (Category c : categories) {%>
	 	<option value="<%=c.getName() %>" <% if (cat != null && cat.equals(c.getName())) { %>selected="selected" <%} %>><%=c.getName() %></option>
	 	<%} %>
	 </select>
	<input type="submit" value="Run Query"/>
</form>
<% } %>
<% // This is the form for the next 20 rows / 10 collumns %>
<form method="POST">
	<input type="hidden" name="rows" value="<%=request.getParameter("rows") %>"/>
	<input type="hidden" name="ages" value="<%=request.getParameter("ages") %>"/>
	<input type="hidden" name="category" value="<%=request.getParameter("category") %>"/>
	<% // For both of these, it basically says if the rowOffset attr is not null then set it to the offset + 20 or 10 more
	   int rowRange = (request.getAttribute("rowOffset") != null) ? ((Integer)request.getAttribute("rowOffset")+20) : 20;
	   int prodRange = (request.getAttribute("prodOffset") != null) ? ((Integer)request.getAttribute("prodOffset")+10) : 10;%>
	<input type="hidden" name="rowRange" value="<%=rowRange %>"/>
	<input type="hidden" name="prodRange" value="<%=prodRange %>"/>
	<% if (products.size() == 10) { %>
	<input type="submit" name="prodSubmit" value="Next 10 Products"/>
	<% } if (map.size() == 20) { %>
	<input type="submit" name="rowSubmit" value="Next 20 <%=attr[0]%>"/>
	<% } %></form></div>
	
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
