<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />
        <link rel="stylesheet" type="text/css" href="<c:url value="/ressources/style.css"/>">
        <title>Inscription</title>
    </head>
    <body>
    	<c:import url="header.jsp"/>
		<main>
			<header>
				<h2>Inscription</h2>
			</header>
			<section>
				<form method="post" action="Inscription">
					<table class="formulaire">
						<tr>
							<td><label for="login">Login :</label></td>
							<td><input type="text" id="login" name="login" /></td>
						</tr>
						<tr>
							<td><label for="pass">Mot de passe :</label></td>
							<td><input type="password" id="pass" name="pass" value="" /></td>
						</tr>
						<tr>
							<td><label for="nom">Nom :</label></td>
							<td><input type="text" id="nom" name="nom" /></td>
						</tr>
						<tr>
							<td><label for="prenom">Prénom :</label></td>
							<td><input type="text" id="prenom" name="prenom" /></td>
						</tr>
					</table>
					<input type="submit" value="Inscription">
					<c:if test="${message != null}"><p class="erreurConnexion"><c:out value="${message}"></c:out></p></c:if> 
	        	</form>
			</section>
        </main>
    </body>
</html>