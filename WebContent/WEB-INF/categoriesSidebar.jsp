<div class="title1">Categories</div>
	<% List<Category> categories = CategoryDAO.list();
    for(Category c : categories) {%>
    <div><%= c.getName() %></div>

    <%} %>