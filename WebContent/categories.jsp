<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="java.util.List" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Categories</title>
</head>
<body>
<%@ include file="WEB-INF/header.jsp" %>
<h1>Categories</h1>
<table border="1">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
            </tr>
            	<% List<Category> categories = CategoryDAO.list();
            	for(Category c : categories) {%>
            	<tr>
            		<td><%=c.getId() %></td>
            		<td><%=c.getName() %></td>
            		<td><%=c.getDescription() %></td>
            	</tr>
            	<%} %>
            </table>
            <div style="height:20px;"></div>
            <div><h2>Add New Categories</h2></div>
            <form action="newCategory" method="POST">
            	<div>Category Name:</div>
            	<div><input name="category"/></div>
            	<div>Description:</div>
            	<div><textarea name="description" cols="40" rows="5"></textarea></div>
            	<div><input type="Submit" name="newCat" value="Submit"/></div>
            </form>
</body>
</html>