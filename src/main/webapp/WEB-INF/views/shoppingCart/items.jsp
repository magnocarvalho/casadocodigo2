<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
</head>
<body>
<h2>Seu carrinho de compras</h2>
<table>
	<thead>
    	<tr>
	        <th>Item</th>
	        <th>Preço</th>
	        <th>Quantidade</th>
	        <th>Total</th>
	        <th>Remover</th>
        </tr>
	</thead>
	<tbody>
        <c:forEach items="${shoppingCart.list}" var="item">
        <tr>
          <td>${item.product.title} - ${item.bookType}</td>
          <td>${item.price}</td>
          <td>${shoppingCart.getQuantity(item)}</td>
          <td>${shoppingCart.getTotal(item)}</td>
          <td>
          	<form method="post" action="${spring:mvcUrl('SCC#remove').arg(0,item.product.id).arg(1,item.bookType).build()}">
          		<input type="submit" value="Remover" />
          	</form>
          </td>
        </tr>
        </c:forEach>
        
      </tbody>
      <tfoot>
        <tr>
          <td>
          	<form action="${spring:mvcUrl('PC#checkout').build()}" method="post">
          		<input type="submit" name="checkout" value="Finalizar compra " id="checkout"/>
          	</form>
          </td>
          <td>Total: ${shoppingCart.total}</td>
        </tr>
      </tfoot>
</table>
</body>
</html>