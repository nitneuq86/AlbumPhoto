<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header>
	<section class="banner">
		<h1>Albumz</h1>
		<h2>Gestionnaire d'albums photo en JavaEE</h2>
	</section>
	<section class="module">
		<p>
			<c:out
				value="${sessionScope.sessionUtilisateur.personne.prenom } ${sessionScope.sessionUtilisateur.personne.nom}" />
			(<a href="<c:url value="/Deconnexion"/>">Déconnexion</a>)
		</p>
		<form>
			<input type="text" placeholder="Recherche rapide..."> <input
				type="submit" value="Go !">
		</form>
	</section>
</header>