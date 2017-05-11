<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Lista</title>
</head>
<body>

<c:if test="${not empty sucesso}">
${sucesso}<p/>
</c:if>

<a href="<c:url value='/shopping'/>">Seu carrinho (${shoppingCart.quantity}) </a>
<p/>
<a href="<c:url value='/produtos/form'/>">Incluir novo item ao cat�logo</a>
<p/>
	<table>
		<tr>
			<td>Titulo</td>
			<td>Sum�rio</td>
			<td>Valores</td>
			<td>Imagem</td>
			<td>Download</td>
			<td>Alterar</td>
			<td>Excluir</td>
		</tr>
		
		<c:forEach items="${products}" var="product">
			<tr>
				<td>
					<a href="<c:url value='/produtos/show?id=${product.id}'/>">${product.title}</a>
				</td>
				
				<td><a href="<c:url value='/produtos/download?file=${product.summaryPath}'/>">Sum�rio</a></td>
				
				<td>
					<c:forEach items="${product.prices}" var="price"> 
						[${price.value} - ${price.bookType}]
					</c:forEach>
				</td>
				
				<td><img src="${pathImages}${product.summaryPath}?noAuth=true"></td>
				
				<td><a href="${pathImages}${product.summaryPath}?noAuth=true">Download</a></td>
				
				<td><a href="<c:url value='/produtos/alterar/${product.id}'/>">Alterar Registro</a></td>
				
				<td><a href="<c:url value='/produtos/excluir/${product.id}'/>">Excluir Registro</a></td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>