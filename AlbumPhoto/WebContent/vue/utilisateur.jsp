<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="<c:url value="/ressources/style.css"/>">
		<title>Utilisateur <c:out value="${requestScope.album.titre}"/></title>
	</head>
	<body>
		<c:import url="header.jsp"/>
		<main>
			<header>
				<h3>Utilisateur</h3>
				<h2><c:out value=""/></h2>
			</header>
			<section>
			</section>
		</main>
	</body>
</html>