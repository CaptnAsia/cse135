<%@ page import="cse135.model.*" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Map.Entry;" %>
<% Map<Product,Integer> cart = (HashMap<Product,Integer>)session.getAttribute("cart");
   User current = (User)session.getAttribute("currentSessionUser"); 
%>
<div class="header">
<div class="header-content"><div class="title2"><a href="/cse135">Home</a></div><%
if (current == null) {%>
<a href="login">Log In</a> or <a href="signup">Sign Up</a> 
 
<% } else { %>
Hello <%=current.getName() %> | <%
if (current.isOwner()) { %><a href="categories">Categories</a> | 
<% } %><a href="products">Products</a> | <a href="sales">Sales Analytics</a>
<%} %></div><% if (current == null || !current.isOwner()) { %>
<div class="buyCart"><a href="buyCart">Buy Shopping Cart</a> <% if (cart != null) { %>(<%= cart.size() %>) <% } }%></div></div>

</div>