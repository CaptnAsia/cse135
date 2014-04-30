<%@ page import="cse135.model.*" %>
<% User current = (User)session.getAttribute("currentSessionUser"); 
%>
<div class="header">
<div class="header-content"><%
if (current == null) {%>
<a href="login">Login</a> or <a href="signup">Sign Up</a>
<% } else { %>
Hello <%=current.getName() %> | <a href="products">Products</a>
 | <a href="/cse135/categories">Categories</a>
<%} %></div></div>
</div>