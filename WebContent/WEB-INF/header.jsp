<% User current = (User)session.getAttribute("currentSessionUser"); 

if (current != null) {%>
<div>Hello <%=current.getName() %></div>
<% } %>