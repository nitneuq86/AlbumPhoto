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
					<h3>Mes albums photo (<c:out value="${fn:length(utilisateur.personne.albums)}"/>)</h3>
					<c:if test="${fn:length(utilisateur.personne.albums) == 0}">Aucun album photo créé :(</c:if>
				</article>
				<article>
					<h3>Ajouter un album photo</h3>
					<form method="post" action="/Album">
						<table class="formulaire">
							<tr>
								<td><label for="title">Titre :</label></td>
								<td><input type="text" id="title" name="title"/></td>
							</tr>
						</table>
						<input type="submit" value="Créer l'album">
					</form>
				</article>
			</section>
		</main>
	</body>
</html>