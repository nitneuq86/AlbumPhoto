<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="<c:url value="/ressources/style.css"/>">
		<link href='https://fonts.googleapis.com/css?family=Indie+Flower' rel='stylesheet' type='text/css'>
		<title>Album "<c:out value="${album.titre}"/>"</title>
	</head>
	<body>
		<c:import url="header.jsp"/>
		<main>
			<header>
				<h3>Album</h3>
				<h2><c:out value="${album.titre}"/></h2>
			</header>
			<section>
				<article>
					<h3>Informations</h3>
					<ul>
						<li>Créateur : <a href='<c:url value="/Personne/${album.createur.id}"></c:url>'><c:out value="${album.createur.prenom} ${album.createur.nom}"/></a></li>
						<li>Date de création : <fmt:formatDate pattern="EEEE dd MMMM yyyy" value="${album.dateCreation}"/></li>
						<li>Personne(s) :</li>
						<li>Lieu(x) :</li>
						<li>Objet(s) :</li>
					</ul>
				</article>
				<article>
					<h3>Photos</h3>
					<ul class="photos">
						<c:forEach var="photo" items="${album.photos}" varStatus="num">
							<li>
								<img src="<c:out value="${photo.uri}"/>" style="background:url(<c:out value="${photo.uri}"/>) center; background-size:cover;"/>
								<p>${num.index+1}</p>
							</li>
						</c:forEach>
					</ul>
				</article>
			</section>
		</main>
	</body>
</html>