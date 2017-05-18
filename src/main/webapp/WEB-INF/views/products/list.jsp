<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="customTags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<customTags:page bodyClass="" title="">

	<security:authorize access="isAuthenticated()">
		<security:authentication property="principal" var="user"/>
		<div>
			<spring:message code="users.welcome" arguments="${user.name}"/><br><a href="<c:url value='/logout'/>">Logout</a>
		</div>
	</security:authorize>
	
	<c:if test="${not empty sucesso}">
		${sucesso}<p/>
	</c:if>
	
	<a href="<c:url value='/shopping'/>"> <fmt:message key="shoppingCart.title"/> (${shoppingCart.quantity}) </a>
	<p/>
	
	<security:authorize access="hasRole('ROLE_ADMIN')">
		<a href="<c:url value='/produtos/form'/>">Incluir novo item ao catálogo</a>
		<p/>
	</security:authorize>
	
	<h3><spring:message code="products.list-title"/></h3>

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
				<td>
					<a href="<c:url value='/produtos/show?id=${product.id}'/>">${product.title}</a>
				</td>
				
				<td><a href="<c:url value='/produtos/download?file=${product.summaryPath}'/>">Sumário</a></td>
				
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

</customTags:page>