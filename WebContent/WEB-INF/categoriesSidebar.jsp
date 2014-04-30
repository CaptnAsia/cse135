<div class="title2">Categories</div>
	<div class="list"><a href="products">All Products</a></div>
	<% List<Category> categories = CategoryDAO.list();
    for(Category c : categories) {%>
    <div class="list"><a href="products?category=<%= c.getName() %>"><%= c.getName() %></a></div>

    <%} %>