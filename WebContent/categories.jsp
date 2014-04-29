<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List" %>
    <%@ page import="cse135.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Categories</title>
</head>
<body>
<!-- <@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  -->
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
</body>
</html>