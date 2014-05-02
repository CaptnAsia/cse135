<%@ page import="cse135.model.*" %>
<% User current = (User)session.getAttribute("currentSessionUser"); 
%>
<div class="header">
<div class="header-content"><div class="title2"><a href="/cse135">Home</a></div><%
if (current == null) {%>
<a href="login">Login</a> or <a href="signup">Sign Up</a> 
 
<% } else { %>
Hello <%=current.getName() %> | <%
if (current.isOwner()) { %><a href="categories">Categories</a> | 
<% } %><a href="products">Products</a>
<%} %></div><div class="buyCart"><a href="buyCart">Buy Shopping Cart</a></div></div>

</div>