<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/style.css"/>">
<link href='https://fonts.googleapis.com/css?family=Indie+Flower'
	rel='stylesheet' type='text/css'>
<title>Album "<c:out value="${requestScope.album.titre}" />"
</title>
<script type="text/javascript">
			console.log("coucou");
			function getXMLHttpRequest() {
				var xhr = null;
				
				if (window.XMLHttpRequest || window.ActiveXObject) {
					if (window.ActiveXObject) {
						try {
							xhr = new ActiveXObject("Msxml2.XMLHTTP");
						} catch(e) {
							xhr = new ActiveXObject("Microsoft.XMLHTTP");
						}
					} else {
						xhr = new XMLHttpRequest(); 
					}
				} else {
					alert("Votre navigateur ne supporte pas l'objet XMLHTTPRequest...");
					return null;
				}
				
				return xhr;
			}
			
			function deleteAlbumPOST(idAlbum, idPersonne){
				console.log("POST");
				var xhr = getXMLHttpRequest();

				xhr.open("POST", "http://localhost:8080/AlbumPhoto/Album", true);
				xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				console.log(idAlbum);
				xhr.send("idAlbum=" + idAlbum);
				return false;
			}
			
			function deleteAlbumPUT(idAlbum, idPersonne){
				console.log("PUT");
				var xhr = getXMLHttpRequest();

				xhr.open("PUT", "http://localhost:8080/AlbumPhoto/Album", true);
				xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				console.log(idAlbum);
				xhr.send("idAlbum=" + idAlbum);
				return false;
			}
			
			function deleteAlbumDELETE(idAlbum, idPersonne){
				console.log("DELETE");
				var xhr = getXMLHttpRequest();

				xhr.open("DELETE", "http://localhost:8080/AlbumPhoto/Album", true);
				xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				console.log(idAlbum);
				xhr.send("idAlbum=" + idAlbum);
				return false;
			}
			
		</script>
</head>
<body>
	<c:import url="header.jsp" />
	<main> <header>
		<h3>Album</h3>
		<h2>
			<c:out value="${requestScope.album.titre}" />
		</h2>
	</header>
	<section>
		<article>
			<h3>Informations</h3>
			<ul>
				<li>Créateur : <a
					href="<c:out value="${pageContext.servletContext.contextPath}"/>/Utilisateur/"><c:out
							value="${requestScope.album.createur.prenom} ${requestScope.album.createur.nom}" /></a></li>
				<li><a href=" " onClick="return deleteAlbumPOST(351);">Supprimer
						POST</a></li>
				<li><a href=" " onClick="return deleteAlbumDELETE(51);">Supprimer
						DELETE</a></li>
				<li><a href=" " onClick="return deleteAlbumPUT(351);">Supprimer
						PUT</a></li>
				<li>Date de création : <fmt:formatDate
						pattern="EEEE dd MMMM yyyy"
						value="${requestScope.album.dateCreation}" /></li>
				<li>Personne(s) :</li>
				<li>Lieu(x) :</li>
				<li>Objet(s) :</li>
			</ul>
		</article>
		<article>
			<h3>Photos</h3>
			<ul class="photos">
				<c:forEach var="photo" items="${requestScope.album.photos}"
					varStatus="num">
					<li><img src="<c:out value="${photo}"/>"
						style="background:url(<c:out value="${photo}"/>) center; background-size:cover;" />
						<p>${num.index+1}</p></li>
				</c:forEach>
			</ul>
		</article>
	</section>
	</main>
</body>
</html>