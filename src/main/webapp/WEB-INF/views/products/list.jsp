<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Lista</title>
</head>
<body>

${sucesso}

	<table>
		<tr>
			<td>Titulo</td>
			<td>Sum�rio</td>
			<td>Valores</td>
			<td>Imagem</td>
		</tr>
		<c:forEach items="${products}" var="product">
			<tr>
				<td>${product.title}</td>
				<td><a href="<c:url value='/produtos/download?file=${product.summaryPath}'/>">Sum�rio</a></td>
				<td>
					<c:forEach items="${product.prices}" var="price"> 
						[${price.value} - ${price.bookType}]
					</c:forEach>
				</td>
				<td><img src="http://localhost:9444/s3/casadocodigo2/logo-multitec_pequeno.png?noAuth=true"></td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>