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
				<h2>Gestionnaire d'albums photo</h2>
			</header>
			<section>
				<article>
					<h3>Mes albums photo (<c:out value="${fn:length(sessionScope.sessionUtilisateur.personne.albums)}"/>)</h3>
					<c:if test="${fn:length(sessionScope.sessionUtilisateur.personne.albums) == 0}">Aucun album photo créé</c:if>
					<c:if test="${fn:length(sessionScope.sessionUtilisateur.personne.albums) > 0}">
					<ul class="albums">
						<c:forEach var="album" items="${sessionScope.sessionUtilisateur.personne.albums}">
							<li>
								<a href="<c:url value="/GestionnairePhotos/${album.id}"></c:url>">
									<article>
										<c:if test="${fn:length(album.photos) >= 1}">
											<img class="previewBigPhoto" src="<c:url value="${pathImages}${album.photos[0].url}"/>" style="background:url(<c:url value="${pathImages}${album.photos[0].url}"/>) center; background-size:cover;"/>
										</c:if>
										<c:if test="${fn:length(album.photos) >= 2}">
											<img class="previewSmallPhoto1" src="<c:url value="${pathImages}${album.photos[1].url}"/>" style="background:url(<c:url value="${pathImages}${album.photos[1].url}"/>) center; background-size:cover;"/>
										</c:if>
										<c:if test="${fn:length(album.photos) >= 3}">
											<img class="previewSmallPhoto2" src="<c:url value="${pathImages}${album.photos[2].url}"/>" style="background:url(<c:url value="${pathImages}${album.photos[2].url}"/>) center; background-size:cover;"/>
										</c:if>
									</article>
									<p lang="fr"><c:out value="${album.titre}"></c:out></p>
									<form class="suppression" action="GestionnaireAlbums" method="post">
										<input type="hidden" name="method" value="DELETE">
										<input type="hidden" name="idAlbum" value="${album.id}">
										<input type="submit" title="Supprimer l'album" value="X">
									</form>
								</a>
							</li>
						</c:forEach>
					</ul>
					</c:if>
					
				</article>
				<article>
					<h3>Ajouter un album photo</h3>
					<form method="post" action="GestionnaireAlbums">
						<table class="formulaire">
							<tr>
								<td><label for="titre">Titre :</label></td>
								<td><input type="text" id="titre" name="titre"/></td>
							</tr>
						</table>
						<input type="submit" value="Créer l'album">
					</form>
					<c:if test="${code == 400}"><p class="erreur">Erreur : <c:out value="${message}"></c:out></p></c:if>
				</article>
			</section>
		</main>
	</body>
</html>