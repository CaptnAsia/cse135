<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Hashtable" %>
<link rel="stylesheet" type="text/css" href="stylesheets/main.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Categories</title>
</head>
<body>
<%@ include file="WEB-INF/header.jsp" %>
<div class="wrapper">
<%  //System.out.println("categories.jsp"); %>
<% if (((Boolean)request.getAttribute("permission")).booleanValue()) { %>
<div class="title1">Categories</div>
    <% if (request.getAttribute("error") != null) { %>
		<div style="margin: 10px 10px 10px 0px; color: red;"><%=request.getAttribute("error") %></div>
		<% } %>
	<table border="1">
    	<tr>
        	<th>ID</th>
        	<th>Name</th>
        	<th>Description</th>
        	<th>Update</th>
        	<th>Delete</th>
        </tr>
        <% List<Category> categories = (List<Category>)request.getAttribute("list");
        Hashtable<Long,Boolean> table = (Hashtable<Long,Boolean>)request.getAttribute("delete");
        int i = 1;
        for(Category c : categories) {%>
        
        <tr>
        	<form action="categories" method="POST">
            	<input type="hidden" value="<%=c.getId()%>" name="id"/>
        	<td><%=i %></td>
        	<td><input name="name" value="<%=c.getName() %>"/></td>
        	<td><input name="description" value="<%=c.getDescription() %>"/></td>
        	<td><input type="Submit" name="update" value="Update"/></td>
        	<td><% if (table.get(c.getId()) == null) {%>
        	<form action="categories" method="POST">
        		<input type="hidden" value="<%=c.getId() %>" name="id"/>
        		<input type="Submit" name="delete" value="Delete"/>
        	</form><%} %></td>
        	</form>
        </tr>
        <%i++;} %>
    </table>
    <div style="height:20px;"></div>
    <div><h2>Add New Category</h2></div>
    	<form action="categories" method="POST">
        	<div>Category Name:</div>
        	<div><input name="name"/></div>
        	<div>Description:</div>
        	<div><textarea name="description" cols="40" rows="5"></textarea></div>
        	<div><input type="Submit" name="newCat" value="Submit"/></div>
        </form>
 <% } else { %>
 You do not have permission to access this page.
 <% } %>
</div>
</body>
</html>