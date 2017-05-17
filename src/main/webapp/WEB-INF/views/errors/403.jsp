<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Nao autorizado</title>
</head>
<body>
<h2>Voce nao tem autorizacao para ver essa pagina</h2>
<a href="<c:url value='/login'/>">Login</a>
</body>
</html>