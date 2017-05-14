<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>${product.title}</title>
</head>
<body>

<a href="<c:url value='/shopping'/>">Seu carrinho (${shoppingCart.quantity}) </a>

<h1>${product.title}</h1>

<p>${product.description}</p>

<p/>

<img src="${pathImages}${product.summaryPath}?noAuth=true">

<form:form servletRelativeAction="/shopping">

	<input type="hidden" value="${product.id}" name="productId"/>
	
	<ul>
		<c:forEach items="${product.prices}" var="price">
			<li>
				
				<input type="radio" name="bookType" id="${product.id}-${price.bookType}"
				value="${price.bookType}" ${price.bookType.name() == 'COMBO' ? 'checked' : ''}>
				 
				<label for="${product.id}-${price.bookType}"> 
					${price.bookType}								
				</label>
				
				<p>${price.value}</p>
			</li>
		</c:forEach>
	</ul>

	<input type="submit" value="Comprar"/>

</form:form>

</body>
</html>