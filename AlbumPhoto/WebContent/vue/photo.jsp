<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="<c:url value="/ressources/style.css"/>">
		<title>Photo</title>
	</head>
	<body>
		<c:import url="header.jsp"/>
		<main>
			<header>
				<h2>Photo</h2>
			</header>
			<article class="displayPhoto">
				<img src="<c:url value="${pathImages}${photo.url}" />" />
				
				<table class="formulaire">
					<tr>
						<td><label for="titre">Titre :</label></td>
						<td><input type="text" id="titre" name="titre" value="${photo.titre}" disabled></td>
					</tr>
					<tr>
						<td><label for="date">Date :</label></td>
						<td><input type="date" id="date" name="date" value="${photo.date}" disabled></td>
					</tr>
					<tr>
						<td><label for="date">Où :</label></td>
						<td><input type="text" id="Ou" name="Ou" value="${photo.ou}" disabled></td>
					</tr>
					<tr>
						<td><label for="createur">Créateur :</label></td>
						<td><input type="text" id="createur" name="createur" value="${photo.photographe}" disabled></td>
					</tr>
					<c:if test="${fn:length(photo.qui) != 0}">
						<c:forEach var="qui" items="${photo.qui}" varStatus="num">
							<tr>
								<td><label for="qui">Qui :</label></td>
								<td><input type="text" id="qui${num.count}" name="qui" value="${qui}" disabled></td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${fn:length(photo.quoi) != 0}">
						<c:forEach var="qui" items="${photo.quoi}" varStatus="num">
							<tr>
								<td><label for="quoi">Quoi :</label></td>
								<td><input type="text" id="quoi${num.count}" name="quoi" value="${qui}" disabled></td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${fn:length(photo.evenement) != 0}">
						<tr>
							<td><label for="evenement">Evenement :</label></td>
							<td><input type="text" id="evenement" name="evenement" value="${photo.evenement}" disabled></td>
						</tr>
					</c:if>
				</table>
			</article>
				
</body>
</html>