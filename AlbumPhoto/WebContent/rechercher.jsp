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
			<section>
				<form>
					<input type="text">
					<input type="submit" value="Rechercher">
				</form>
			</section>
		</main>
	</body>
</html>