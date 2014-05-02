<div class="title2">Categories</div>
<div style="height:10px;"></div>
	<div class="list"><a href="products" title="List all the Products">All Products</a></div>
	<% List<Category> categories = CategoryDAO.list();
    for(Category c : categories) {%>
    <div class="list"><a href="products?category=<%= c.getName() %>" title="<%=c.getDescription() %>"><%= c.getName() %></a></div>
    <%} %>