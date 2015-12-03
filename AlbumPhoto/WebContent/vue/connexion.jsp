<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/style.css"/>">
<title>Connexion</title>
</head>
<body>
	<c:import url="header.jsp" />
	<main> <header>
		<h2>Connexion</h2>
	</header>
	<section>
		<form method="post" action="Connexion">
			<table class="formulaire">
				<tr>
					<td><label for="login">Login :</label></td>
					<td><input type="text" id="login" name="login"
						value="<c:out value="${utilisateur}"/>" /></td>
				</tr>
				<tr>
					<td><label for="pass">Mot de passe :</label></td>
					<td><input type="password" id="pass" name="pass" value="" /></td>
				</tr>
			</table>
			<input type="submit" value="Connexion">
			<c:if test="${utilisateur != null}">
				<p class="erreurConnexion">Combinaison login/mot de passe
					incorrecte</p>
			</c:if>
		</form>
	</section>
	</main>
</body>
</html>