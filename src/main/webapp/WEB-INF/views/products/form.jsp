<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Cadastro de Produtos</title>
</head>
<body>

<!-- action="${spring:mvcUrl('saveProduct').build()}" -->
<c:url var="post_url"  value="/produtos" />
<form:form method="post" action="${post_url}" commandName="product" enctype="multipart/form-data">
	
	<div><form:hidden path="id"/></div>
		
	<div>
		<label for="title">Titulo</label>
		<form:input path="title"/>
		<form:errors path="title"/>
	</div>
	
	<div>
		<label for="description">Descrição</label>
		<form:textarea path="description" rows="10" cols="20"/>
		<form:errors path="description"/>
	</div>
	
	<div>
		<label for="pages">Número de paginas</label>
		<form:input path="pages"/>
		<form:errors path="pages"/>
	</div>
	
	<div>
		<label for="releaseDate">Data de lançamento</label>
		<form:input path="releaseDate" type="date"/>
		<form:errors path="releaseDate"/>
	</div>
	
	<div>
		<label for="summary">Sumario do livro</label>
		<input type="file" name="summary"/>
		<form:errors path="summaryPath"/>
	</div>
	
	<c:forEach items="${types}" var="bookType" varStatus="status">
		<div>
			<label for="price_${bookType}">${bookType}</label>
			<input type="text" name="prices[${status.index}].value" id="price_${bookType}" value="<fmt:formatNumber value="${precos[status.index]}" pattern="#,##0.00"/>"/>
			<input type="hidden" name="prices[${status.index}].bookType" value="${bookType}"/>
		</div>
	</c:forEach>
	
	<div>
		<input type="submit" value="Enviar">
	</div>
</form:form>
	


</body>
</html>