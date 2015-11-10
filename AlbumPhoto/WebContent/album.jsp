<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="../style.css">
		<title>Album "<c:out value="${requestScope.album.titre}"></c:out>"</title>
	</head>
	<body>
		<c:import url="header.html"></c:import>
		<main>
			<section>
				<h2><c:out value="${requestScope.album.titre}"></c:out></h2>
				<article>
					<h3>Informations</h3>
					<ul>
						<li>Créateur : <c:out value="${requestScope.album.createur}"></c:out></li>
						<li>Date de création : <c:out value="${requestScope.album.dateCreation}"></c:out></li>
					</ul>
				</article>
				<article>
					<h3>Photos</h3>
					<ul>
						<c:forEach var="photo" items="${requestScope.album.photos}">
							<li><c:out value="${photo}"></c:out></li>
						</c:forEach>
					</ul>
				</article>
			</section>
		</main>
	</body>
</html>