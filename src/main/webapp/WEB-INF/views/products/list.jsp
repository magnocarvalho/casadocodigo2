<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Lista</title>
</head>
<body>

${sucesso}

<a href="<c:url value='/produtos/form'/>">Incluir novo</a>

	<table>
		<tr>
			<td>Titulo</td>
			<td>Sumário</td>
			<td>Valores</td>
			<td>Imagem</td>
			<td>Download</td>
			<td>Alterar</td>
			<td>Excluir</td>
		</tr>
		<c:forEach items="${products}" var="product">
			<tr>
				<td>${product.title}</td>
				
				<td><a href="<c:url value='/produtos/download?file=${product.summaryPath}'/>">Sumário</a></td>
				
				<td>
					<c:forEach items="${product.prices}" var="price"> 
						[${price.value} - ${price.bookType}]
					</c:forEach>
				</td>
				
				<td><img src="http://localhost:9444/s3/casadocodigo2/${product.summaryPath}?noAuth=true"></td>
				
				<td><a href="http://localhost:9444/s3/casadocodigo2/${product.summaryPath}?noAuth=true">Download</a></td>
				
				<td><a href="<c:url value='/produtos/alterar/${product.id}'/>">Alterar Registro</a></td>
				
				<td><a href="<c:url value='/produtos/excluir/${product.id}'/>">Excluir Registro</a></td>
			</tr>
		</c:forEach>
	</table>

</body>
</html>