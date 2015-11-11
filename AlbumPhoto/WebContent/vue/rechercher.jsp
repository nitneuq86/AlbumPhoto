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
		<c:import url="header.html"/>
		<main>
			<header>
				<h2>Rechercher</h2>
			</header>
			<section class="recherche">
				<p>Sur quoi porte votre recherche ?</p>
				<select>
					<option selected>Tout type de ressource</option>
					<option>Une photo</option>
					<option>Un album</option>
					<option>Une personne</option>
				</select>
				<form class="all show">
					<input type="submit" value="Rechercher">
				</form>
				<form class="photo">
					<input type="submit" value="Rechercher">
				</form>
				<form class="album">
					<input type="submit" value="Rechercher">
				</form>
				<form class="personne">
					<input type="submit" value="Rechercher">
				</form>
			</section>
		</main>
	</body>
</html>