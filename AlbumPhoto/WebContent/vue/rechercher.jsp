<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="<c:url value="/style.css"/>">
		<title>Rechercher</title>
	</head>
	<body>
		<c:import url="header.jsp"/>
		<main>
			<header>
				<h2>Rechercher</h2>
			</header>
			<section id="rechercheSection">
				<p>Sur quoi porte votre recherche ?</p>
				<select id="selecteur" onchange="afficherForm()">
					<option <c:if test="${requestScope.typeRecherche == null}">selected</c:if>>Tout type de ressource</option>
					<option <c:if test="${requestScope.typeRecherche == 'photo'}">selected</c:if>>Une photo</option>
					<option <c:if test="${requestScope.typeRecherche == 'album'}">selected</c:if>>Un album</option>
					<option <c:if test="${requestScope.typeRecherche == 'personne'}">selected</c:if>>Une personne</option>
				</select><br/>
				<form <c:if test="${requestScope.typeRecherche == null}">class="show"</c:if>>
					<table>
						<tr>
							<td><label for="quoi">Quoi ?</label></td>
							<td><input id="quoi" type="text" placeholder="Photo, album, personne..."></td>
						</tr>
					</table>
					<br/><input type="submit" value="Rechercher">
				</form>
				<form <c:if test="${requestScope.typeRecherche == 'photo'}">class="show"</c:if>>
					<table>
						<tr>
							<td><label for="qui">Qui ?</label></td>
							<td><input id="qui" type="text" placeholder="Une personne..."></td>
						</tr>
						<tr>
							<td><label for="quoi">Quoi ?</label></td>
							<td><input id="quoi" type="text" placeholder="Un objet..."></td>
						</tr>
						<tr>
							<td><label for="ou">Où ?</label></td>
							<td><input id="ou" type="text" placeholder="Un lieu..."></td>
						</tr>
						<tr>
							<td><label for="quand">Quand ?</label></td>
							<td><input id="quand" type="text" placeholder="Une date..."></td>
						</tr>
					</table>
					<input type="submit" value="Rechercher">
				</form>
				<form <c:if test="${requestScope.typeRecherche == 'album'}">class="show"</c:if>>
					<table>
						<tr>
							<td><label for="qui">Qui ?</label></td>
							<td><input id="qui" type="text" placeholder="Une personne..."></td>
						</tr>
						<tr>
							<td><label for="quoi">Quoi ?</label></td>
							<td><input id="quoi" type="text" placeholder="Un objet..."></td>
						</tr>
						<tr>
							<td><label for="ou">Où ?</label></td>
							<td><input id="ou" type="text" placeholder="Un lieu..."></td>
						</tr>
						<tr>
							<td><label for="quand">Quand ?</label></td>
							<td><input id="quand" type="text" placeholder="Une date..."></td>
						</tr>
					</table>
					<input type="submit" value="Rechercher">
				</form>
				<form <c:if test="${requestScope.typeRecherche == 'personne'}">class="show"</c:if>>
					<table>
						<tr>
							<td><label for="qui">Qui ?</label></td>
							<td><input id="qui" type="text" placeholder="Nom, prénom..."></td>
						</tr>
					</table>
					<input type="submit" value="Rechercher">
				</form>
			</section>
		</main>
		<script type="text/javascript">
			var afficherForm = function() {
				var index = document.getElementById("selecteur").selectedIndex;
				var formulaires = document.getElementById("rechercheSection").getElementsByTagName("form");
				console.log(formulaires);
				for(var i=0; i< formulaires.length; i++) {
					var classList = formulaires[i].classList;
					if(i == index) {
						if(!classList.contains("show")) classList.add("show");
					}
					else {
						if(classList.contains("show")) classList.remove("show");
					}
				}
			}
		</script>
	</body>
</html>