<% DecimalFormat df = new DecimalFormat("#.00");
   int total = 0;%>
<div style="height:10px;"></div>
	<div><% if (cart != null) { %>
		<table>
			<th>Name</th>
			<th>Price</th>
			<th>Amt</th>
			<% for (Map.Entry<Product, Integer> cartList : cart.entrySet()) { %>
			<tr>
				<td class="name"><%=cartList.getKey().getName() %></td>
				<td class="price">$<%=cartList.getKey().getPrice() %></td>
				<td class="quantity"><%=cartList.getValue() %></td>
			</tr><% total += cartList.getKey().getPrice()*cartList.getValue();} %>
			<tr>
		</table><table class="last"><tr>
		<div style="height:5px;"></div>
		<td class="name last" style="text-align: right;">Total:</td>
		<td class="price last">$<%=total %></td><td class="quantity last"></tr></table><%} else { %>Nothing in your cart yet! <% } %></div>