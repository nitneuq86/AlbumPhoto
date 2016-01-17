<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="<c:url value="/ressources/style.css"/>">
		<script type="text/javascript" src="<c:url value="/ressources/javascript/CRUD.js"/>"></script>
		<link href='https://fonts.googleapis.com/css?family=Indie+Flower' rel='stylesheet' type='text/css'>
		<title>Rechercher</title>
	</head>
	<body>
		<c:import url="header.jsp"/>
		<main>
			<header>
				<h2>Rechercher</h2>
			</header>
			<article id="rechercheSection">
				<form method="post" onsubmit="return submitRecherche();" action="<c:url value="/Rechercher"></c:url>">
				<table class="formulaire">
					<tr>
						<td><label for="titre">Titre :</label></td>
						<td><input type="text" id="titre" name="titre" value="${titre != '' ? titreRequete : ''}"/></td>
					</tr>
					<c:if test="${quis != null}">
						<c:forEach var="quiRequete" items="${quis}" varStatus="num">							
							<tr>
								<td><label for="qui">Contient (Personne) : </label></td>
								<td ${num.count == 1 ? 'class="ajoutPersonne"' : ''}>
								
									<select id="qui${num.count > 1 ? num.count : ''}" name="qui">
										<option value=""></option>
									
										<c:forEach var="personne" items="${personnes}">
											<option value="${personne.URI}"  ${quiRequete == personne.URI ? 'selected' : ''}>
													${personne.prenom} ${personne.nom}
											</option>
										</c:forEach>
									</select>	
									<c:if test="${num.count == 1}">
										<a id="ajoutPersonne" href="#" onClick="return ajoutPersonne('Contient (Personne) : ');"></a>
									</c:if>
								</td>
								<c:if test="${num.count == 1 }">
									<td>
										<input ${aucunRequete == true ? 'checked' : '' } id="aucun" type="checkbox" name="aucun" > 
										<label for="aucun">Aucun</label>
									</td>
									<td>
										<input ${quelquunRequete == true ? 'checked' : '' } id="quelquun" type="checkbox" name="quelquun" > 
										<label for="quelquun">Quelqu'un</label>
									</td>
								</c:if>
							</tr>
							
						</c:forEach>
					</c:if>
							
					<c:if test="${quis == null }">
						<tr>
							<td><label for="qui">Contient (Personne) : </label></td>
							<td class="ajoutPersonne">
								<select id="qui" name="qui">
									<option value=""></option>
								
									<c:forEach var="personne" items="${personnes}" varStatus="num">
										<option value="${personne.URI}"  ${personneRequete == personne.URI ? 'selected' : ''}>
												${personne.prenom} ${personne.nom}
										</option>
									</c:forEach>
								</select>
								<a id="ajoutPersonne" href="#" onClick="return ajoutPersonne('Contient (Personne) : ');"></a>
							</td>
							<td>
								<input ${aucunRequete == true ? 'checked' : '' } id="aucun" type="checkbox" name="aucun" > 
								<label for="aucun">Aucun</label>
							</td>
							<td>
								<input ${quelquunRequete == true ? 'checked' : '' } id="quelquun" type="checkbox" name="quelquun" > 
								<label for="quelquun">Quelqu'un</label>
							</td>
						</tr>
					</c:if>
					
					<c:if test="${quisAnimal != null}">
						<c:forEach var="quiRequete" items="${quisAnimal}" varStatus="num">							
							<tr>
								<td><label for="quiAnimal">Contient (Animal) : </label></td>
								<td ${num.count == 1 ? 'class="ajoutAnimal"' : ''}>
								
									<select id="quiAnimal${num.count > 1 ? num.count : ''}" name="quiAnimal">
										<option value=""></option>
									
										<c:forEach var="animal" items="${animaux}">
											<option value="${animal.URI}" ${quiRequete == animal.URI ? 'selected' : ''}>
												${animal.prenom}
											</option>
										</c:forEach>
									</select>	
									<c:if test="${num.count == 1}">
										<a id="ajoutAnimal" href="#" onClick="return ajoutAnimal('Contient (Animal) : ');"></a>
									</c:if>
								</td>
							</tr>
							
						</c:forEach>
					</c:if>
							
					<c:if test="${quisAnimal == null }">
						<tr>
							<td><label for="quiAnimal">Contient (Animal) : </label></td>
							<td class="ajoutAnimal">
								<select id="quiAnimal" name="quiAnimal">
									<option value=""></option>
									<c:forEach var="animal" items="${animaux}" varStatus="num">
										<option value="${animal.URI}">${animal.prenom}</option>
									</c:forEach>
								</select>
								<a id="ajoutAnimal" href="#" onClick="return ajoutAnimal('Contient 	(Animal) : ');"></a>
							</td>
						</tr>
					</c:if>
					
					<c:if test="${quois != null}">
						<c:forEach var="quoiRequete" items="${quois}" varStatus="num">							
							<tr>
								<td><label for="quoi">Quoi : </label></td>
								<td ${num.count == 1 ? 'class="ajoutObjet"' : ''}>
								
									<input type="text" id="quoi${num.count > 1 ? num.count : ''}" name="quoi" value="${quoiRequete}" />	
									<c:if test="${num.count == 1}">
										<a id="ajoutObjet" href="#" onClick="return ajoutObjet('Quoi : ');"></a>
									</c:if>
								</td>
							</tr>
							
						</c:forEach>
					</c:if>
							
					<c:if test="${quois == null }">
						<tr>
							<td><label for="quoi">Quoi : </label></td>
							<td class="ajoutObjet">
								<input type="text" id="quoi" name="quoi"/>	
								<a id="ajoutObjet" href="#" onClick="return ajoutObjet('Quoi : ');"></a>
							</td>
						</tr>
					</c:if>
					
					<tr>
						<td><label for="caracteristique">Caractéristique : </label></td>
						<td>
							<select id="caracteristique" name="caracteristique">
								<option value=""></option>
								<c:forEach var="caracteristique" items="${caracteristiques}" varStatus="num">
									<option value="${caracteristique}" ${caracteristique == caracteristiqueRequete ? 'selected' : ''}>
										${caracteristique }
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					
					<tr>
						<td><label for="createur">Créateur : </label></td>
						<td>
							<select id="createur" name="createur">
								<option value=""></option>					
								<c:forEach var="personne" items="${personnes}" varStatus="num">
									<option value="${personne.URI}"  ${createurRequete == personne.URI ? 'selected' : ''}>
											${personne.prenom} ${personne.nom}
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td><label for="ou">Où :</label></td>
						<td>
							<input type="text" id="ou" name="ou" onkeyup="verificationPlace()" list="places" value="${ouRequete != '' ? ouRequete : '' }"/>
							<input type="hidden" name="ou-hidden" id="ou-hidden"/>
							<datalist id="places">
								<c:if test="${ouHiddenRequete != '' }">
									<option data-value="${ouHiddenRequete}">${ouRequete}</option>
								</c:if>
							</datalist>
						</td>
						<td>
							<div id="loading"></div>
							<input ${ouEtenduRequete == true ? 'checked' : '' } id="ouEtendu" type="checkbox" name="ouEtendu" > 
							<label for="ouEtendu">Etendu</label>
						</td>
					</tr>
					
					<tr>
						<td><label for="ptVue">Point de vue : </label></td>
						<td>
							<select id="ptVue" name="ptVue">
								<option value=""></option>					
								<c:forEach var="personne" items="${personnes}" varStatus="num">
									<option value="${personne.URI}"  ${ptVueRequete == personne.URI ? 'selected' : ''}>
											${personne.prenom} ${personne.nom}
									</option>
								</c:forEach>
							</select>
						</td>
						<td>
							<input  ${selfieRequete == true ? 'checked' : '' } id="selfie" type="checkbox" name="selfie" /> 
							<label for="selfie">Selfie</label>
						</td>
					</tr>
					<tr>
						<td><label for="dateDebut">Date :</label></td>
						<td>
							<input value="${dateDebutRequete != '' ? dateDebutRequete : '' }" type="date" id="dateDebut" name="dateDebut" >
						</td>
						<td><label for="dateFin">au :</label></td>
						<td>
							<input value="${dateFinRequete != '' ? dateFinRequete : '' }" type="date" id="dateFin" name="dateFin" >
						</td>
					</tr>
					<tr>
						<td><label for="type">Événement : </label></td>
						<td>
							<select id="type" name="type">
								<option value=""></option>
								
								<c:forEach var="evenement" items="${evenements}" varStatus="num">
									<option value="${evenement.URI}"  ${evenementRequete == evenement.URI ? 'selected' : ''}>
											${evenement.titre}
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>					
				</table>
				
				<p id="message"></p>
				<input type="submit" value="Envoyer">
				</form>
			</article>
			
			<c:if test="${photos != null }">
				<article>
					<h3>Resultat</h3>
					<c:if test="${fn:length(photos) == 0}">Il n'y a pas de résultat correspondant à votre recherche</c:if>
					<c:if test="${fn:length(photos) > 0}">
						<ul class="photos">
							<c:forEach var="photo" items="${photos}" varStatus="num">
								<li>
									<a href="<c:url value="/Photo/${photo.id}"/>"> <img src="<c:url value="${pathImages}${photo.url}"/>" style="background:url(<c:url value="${pathImages}${photo.url}"/>) center; background-size:cover;"/></a>
									<p>${photo.titre}</p>
								</li>
							</c:forEach>
						</ul>
					</c:if>
				</article>
			</c:if>
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