<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="<c:url value="/style.css"/>">
		<c:choose>
			<c:when test="${requestScope.typeRecherche == 'utilisateur'}"><title>Rechercher un utilisateur</title></c:when>
			<c:when test="${requestScope.typeRecherche == 'album'}"><title>Rechercher un album</title></c:when>
			<c:otherwise><title>Rechercher</title></c:otherwise>
		</c:choose>
	</head>
	<body>
		<c:import url="header.html"/>
		<main>
			<header>
				<c:choose>
					<c:when test="${requestScope.typeRecherche == 'utilisateur'}">
						<h2>Rechercher un utilisateur</h2>
					</c:when>
					<c:when test="${requestScope.typeRecherche == 'album'}">
						<h2>Rechercher un album</h2>
					</c:when>
					<c:otherwise>
						<h2>Rechercher</h2>
					</c:otherwise>
				</c:choose>
			</header>
			<section>
				<c:choose>
					<c:when test="${requestScope.typeRecherche == 'utilisateur'}">
						<form>
							<input type="text"><input type="submit" value="Rechercher">
						</form>
					</c:when>
					<c:when test="${requestScope.typeRecherche == 'album'}">
						<form>
							<input type="text"><input type="submit" value="Rechercher">
						</form>
					</c:when>
					<c:otherwise>
						<form>
							<input type="text"><input type="submit" value="Rechercher">
						</form>
					</c:otherwise>
				</c:choose>
			</section>
		</main>
	</body>
</html>