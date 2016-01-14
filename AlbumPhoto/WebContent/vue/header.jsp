<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header>
	<section class="banner">
		<h1><a href="<c:url value="/"/>">Albumz</a></h1>
		<h2>Gestionnaire d'albums photo en JavaEE</h2>
	</section>
	<section class="module"><c:if test="${not empty sessionScope.sessionUtilisateur}">
			<h3>Bienvenue <c:out value="${sessionScope.sessionUtilisateur.personne.prenom}" /> !</h3>
			<ul>
				<li><a href="<c:url value="/Personne/${sessionScope.sessionUtilisateur.personne.id}"/>">Accéder à ma page personnelle</a></li>
				<li><a href="<c:url value="/GestionnaireAlbums"/>">Gérer mes albums photo</a></li>
				<li><a href="<c:url value="/Rechercher"/>">Rechercher</a></li>
				<li><a href="<c:url value="/Deconnexion"/>">Se déconnecter</a></li>
			</ul>
		</c:if>
		<c:if test="${empty sessionScope.sessionUtilisateur}">
			<ul>
				<li><a href="<c:url value="/Inscription"/>">Créer un compte</a></li>
				<li><a href="<c:url value="/Connexion"/>">Se connecter</a></li>
			</ul>
		</c:if>
	</section>
</header>