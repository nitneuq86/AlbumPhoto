<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="<c:url value="/ressources/style.css"/>">
		<link href='https://fonts.googleapis.com/css?family=Indie+Flower' rel='stylesheet' type='text/css'>
		<title>Gestionnaire d'albums photo</title>
	</head>
	<body>
		<c:import url="header.jsp"/>
		<main>
			<header>
				<h2>Gestion de l'album "<c:out value="${album.titre}"></c:out>"</h2>
			</header>
			<section>
				<article>
					<h3>Photos de l'album (<c:out value="${fn:length(album.photos)}"/>)</h3>
					<c:if test="${fn:length(album.photos) == 0}">Cet album ne comporte encore aucune photo</c:if>
					<c:if test="${fn:length(album.photos) > 0}">
					<ul class="photos">
						<c:forEach var="photo" items="${album.photos}" varStatus="num">
							<li>
								<img src="<c:url value="${pathImages}${photo.url}"/>" style="background:url(<c:url value="${pathImages}${photo.url}"/>) center; background-size:cover;"/>
								<p>${num.index+1}</p>
								<form class="suppression" action="<c:url value="/GestionnairePhotos/${album.id}"></c:url>" method="post">
									<input type="hidden" name="method" value="DELETE">
									<input type="hidden" name="idPhoto" value="${photo.id}">
									<input type="submit" title="Supprimer la photo de l'album" value="X">
								</form>
							</li>
						</c:forEach>
					</ul>
					</c:if>
					
				</article>
				<article>
					<h3>Ajouter une photo</h3>
					<form method="post" action="<c:url value="/GestionnairePhotos/${album.id}"></c:url>" enctype="multipart/form-data">
						<table class="formulaire">
							<tr>
								<td><label for="titre">Titre :</label></td>
								<td><input type="text" id="titre" name="titre"/></td>
							</tr>
							<tr>
								<td><label for="url">Photo :</label></td>
								<td><input type="file" id="url" name="url"/></td>
							</tr>
							<tr>
								<td><label for="date">Quand :</label></td>
								<td><input type="date" id="date" name="date"/></td>
							</tr>
							<tr>
								<td><label for="qui">Qui :</label></td>
								<td>
									<select name="qui[]">
										<option value="" selected>Personne</option>
										<c:forEach var="personne" items="${personnes}" varStatus="num">
											<option value="${personne.URI}">${personne.prenom} ${personne.nom}</option>
										</c:forEach>
							   	  	</select>
						   	 	</td>
							</tr>
							<tr>
								<td><label for="ou">Où :</label></td>
								<td><input type="text" id="ou" name="ou" onkeyup="verificationEndroit"/></td>
							</tr>
							<tr>
								<td><label for="createur">Auteur :</label></td>
								<td>
									<select name="createur" id="createur">
											<c:forEach var="personne" items="${personnes}" varStatus="num">
												<option value="${personne.URI}">${personne.prenom} ${personne.nom}</option>
											</c:forEach>
							   	  	</select>
						   	  	</td>
							</tr>
						</table>
						<input type="hidden" name="idAlbum" value="<c:out value="${album.id}"></c:out>">
						<input type="submit" value="Ajouter">
					</form>
					<c:if test="${code == 400}"><p class="erreur">Erreur : <c:out value="${message}"></c:out></p></c:if>
				</article>
			</section>
		</main>
	</body>
</html>