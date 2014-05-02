<% ArrayList<Order> cart = (ArrayList<Order>)session.getAttribute("cart"); 
   DecimalFormat df = new DecimalFormat("#.00");
   double total = 0;%>
<div class="title2">Shopping Cart</div>
	<div><% if (cart != null) { %><table>
			<th>Name</th>
			<th>Price</th>
			<th>Amt</th>
			<% for (Order o: cart) { %>
			<tr>
			<td class="name"><%=o.getName() %></td>
			<td class="price">$<%=df.format(o.getPrice()) %></td>
			<td class="quantity"><%=o.getAmount() %></td>
			</tr><% total += o.getPrice()*o.getAmount();} %>
			<tr>
			
			
			
		</table><table class="last"><tr>
		<td class="name last" style="text-align: right;">Total:</td>
		<td class="price last">$<%=total %></td><td class="quantity last"></tr></table><%} else { %>Nothing in your cart yet! <% } %></div>