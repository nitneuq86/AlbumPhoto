<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="<c:url value="/style.css"/>">
		<title>Adresse inconnue</title>
	</head>
	<body>
		<c:import url="header.jsp"/>
		<main>
			<header>
				<h2>Vous êtes perdu ?</h2>
			</header>
			<section>
				<p>Vous avez tenté d'accéder à une page qui n'existe pas !</p>
			</section>
		</main>
	</body>
</html>