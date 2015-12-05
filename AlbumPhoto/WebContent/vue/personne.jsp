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
		<title><c:out value="${personne.prenom} ${personne.nom}"/></title>
	</head>
	<body>
		<c:import url="header.jsp"/>
		<main>
			<header>
				<h3>Personne</h3>
				<h2><c:out value="${personne.prenom} ${personne.nom}"/></h2>
			</header>
			<section>
				<article>
					<h3>Albums (<c:out value="${fn:length(personne.albums)}"/>)</h3>
				</article>
			</section>
		</main>
	</body>
</html>