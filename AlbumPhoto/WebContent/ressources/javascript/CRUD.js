var URL_APP = "http://localhost:8080/AlbumPhoto/";

function getXMLHttpRequest() {
	var xhr = null;
	
	if (window.XMLHttpRequest || window.ActiveXObject) {
		if (window.ActiveXObject) {
			try {
				xhr = new ActiveXObject("Msxml2.XMLHTTP");
			} catch(e) {
				xhr = new ActiveXObject("Microsoft.XMLHTTP");
			}
		} else {
			xhr = new XMLHttpRequest(); 
		}
	} else {
		alert("Votre navigateur ne supporte pas l'objet XMLHTTPRequest...");
		return null;
	}
	
	return xhr;
}

function POST(controleur, parametres, callback){
	var xhr = getXMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
			callback(xhr.responseText);
		}
	};
	
	xhr.open("POST", URL_APP + controleur, true);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send(parametres);
	return false;
}

function PUT(controleur, parametres){
	var xhr = getXMLHttpRequest();

	xhr.open("PUT", URL_APP + controleur, true);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");;
	xhr.send(parametres);
	return false;
}

function DELETE(controleur, parametres){
	var xhr = getXMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
			location.reload();
		}
	};
	
	xhr.open("DELETE", URL_APP + controleur, true);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send(parametres);
	
	return false;
}

function submitForm(){
	var ouString = "ou";
	var ou = document.getElementById(ouString);
	var hiddenInput = document.getElementById(ouString + "-hidden");
	var liste = ou.getAttribute("list");
	var options = document.querySelectorAll("#" + liste + " option");
	var erreur = false;
	var estDansLaListe = false;
	var message = "";
	
	if(document.getElementById("titre").value == ""){
		erreur = true;
		message += "Veuillez choisir un titre. <br/>";
	}
	
	if(document.getElementById("url").files.length == 0){
		erreur = true;
		message += "Veuillez choisir une photo. <br\>";
	}

	if(document.getElementById("date").value == ""){
		erreur = true;
		message += "Veuillez choisir une date. <br\>";
	}

	var i = 0;
	while(!estDansLaListe && i < options.length) {
        var option = options[i];
        if(option.innerText === ou.value) {
        	estDansLaListe = true;
            hiddenInput.value = option.getAttribute('data-value');
            break;
        }
        i++;
    }
	
    if(!estDansLaListe){
    	erreur = true;
		message += "Veuillez choisir un endroit de la liste déroulante. <br/>";
    }

    document.getElementById("message").innerHTML = message;
	
	return !erreur;
}

function submitRecherche(){
	var ouString = "ou";
	var ou = document.getElementById(ouString);
	var hiddenInput = document.getElementById(ouString + "-hidden");
	var liste = ou.getAttribute("list");
	var options = document.querySelectorAll("#" + liste + " option");
	var estDansLaListe = false;
	
	var erreur = false;
	var message = "";
	
	if(ou.value != ""){
		var i = 0;
		while(!estDansLaListe && i < options.length) {
	        var option = options[i];
	        if(option.innerText === ou.value) {
	        	estDansLaListe = true;
	            hiddenInput.value = option.getAttribute('data-value');
	            break;
	        }
	        i++;
	    }
		
		if(!estDansLaListe){
	    	erreur = true;
			message += "Veuillez choisir un endroit de la liste déroulante. <br/>";
	    }
	}
	
	if(document.getElementById("caracteristique").value != "" && document.getElementById("ptVue").value == ""){
		erreur = true;
		message += "Veuillez choisir un point de vue. <br/>";
	}
	
	if(document.getElementById("caracteristique").value != "" && document.getElementById("ptVue").value != ""
			&& (document.getElementById("aucun").checked || document.getElementById("quelquun").checked)){
		erreur = true;
		if(document.getElementById("aucun").checked)
			message += "Pour chercher des photos où il n'y a personne, veuille ne pas remplir les champs de caractéristiques."
		if(document.getElementById("quelquun").checked)
			message += "Pour chercher des photos où il n'y a quelqu'un, veuille ne pas remplir les champs de caractéristiques."
	}
	
	if(document.getElementById("selfie").checked || document.getElementById("aucun").checked || document.getElementById("quelquun").checked){
		var quis = document.querySelectorAll("[name='qui']");
		var quiEstSelectionne = false;
		var i = 0;
		while(!quiEstSelectionne && i < quis.length ){
			if(quis[i].value !== "") quiEstSelectionne = true;
			i++;
		}

		if(quiEstSelectionne){
			erreur = true;
			if(document.getElementById("selfie").checked)
				message += "Pour chercher un selfie, veuillez ne pas remplir les champs \"Contient (Personne) : \". <br/>";
			if(document.getElementById("aucun").checked)
				message += "Pour chercher des photos où il n'y a personne, veuillez ne pas remplir les champs \"Contient (Personne) : \". <br/>";
			if(document.getElementById("quelquun").checked)
				message += "Pour chercher des photos où il n'y a quelqu'un, veuillez ne pas remplir les champs \"Contient (Personne) : \". <br/>";
		}
	}
	
	if(document.getElementById("selfie").checked && document.getElementById("aucun").checked){
		erreur = true;
		message += "Les champs \"Selfie\" et \"Aucun\" sont incompatibles. <br/>";
	}
	
	if(document.getElementById("quelquun").checked && document.getElementById("aucun").checked){
		erreur = true;
		message += "Les champs \"Quelquun\" et \"Aucun\" sont incompatibles. <br/>";
	}
	
	if(document.getElementById("dateDebut").value == "" && document.getElementById("dateFin") != ""){
		erreur = true;
		message += "Il manque la date de début. <br />";
	}
	
	if(document.getElementById("dateDebut").value != "" && document.getElementById("dateFin") != ""){
		var d1 = new Date(document.getElementById("dateDebut").value);
		var d2 = new Date(document.getElementById("dateFin").value);
		if(d1.getTime() < d2.getTime()){
			erreur = true;
			message += "Le deuxième champs de date est antérieur au premier. <br />";
		}
	}
	
	document.getElementById("message").innerHTML = message;
	
	return !erreur;
}

function verificationPlace(){
	var ou = document.getElementById('ou').value;
	if(ou.length >= 3){
		POST("Place", "place=" + ou, traitementPlaces);
		var loading = document.getElementById("loading");
		loading.style.display = "block";
	}
}

function traitementPlaces(response){
	response = JSON.parse(response);
	var places = document.getElementById('places');
	var options = "";
	response.forEach(function(element, index, array){
		options += '<option data-value="' + element.uri + '">' + element.place + '</option>';
	});
	places.innerHTML = options;
	var loading = document.getElementById("loading");
	loading.style.display = "none";
}

function ajoutPersonne(titre){
	var qui = "qui";
	var positionNouveauQui = document.querySelectorAll("select[name='" + qui + "']").length + 1;
	var positionCourante = positionNouveauQui <= 2 ? "" : positionNouveauQui - 1;
	var ligneCourante = document.getElementById(qui + positionCourante).parentElement.parentElement;
	var nouveauQui = document.createElement("tr");
	nouveauQui.innerHTML = '<td><label >' + titre + '</label></td>\n'
						 + '<td>'
						 + '<select name="' + qui + '" id="' + qui + positionNouveauQui + '">'
						 	+ document.getElementById(qui).innerHTML
						 + '</select>'
						 + '</td>';
	ligneCourante.parentNode.insertBefore(nouveauQui, ligneCourante.nextElementSibling);
	
	var opt = document.querySelectorAll("#" + qui + positionNouveauQui + " [selected]");
	opt[0].removeAttribute("selected");
	var defaultOpt = document.querySelectorAll("#" + qui + positionNouveauQui + " option:first-child");
	defaultOpt[0].setAttribute("selected", "selected");
	
	return false;
}

function ajoutAnimal(titre){
	var qui = "quiAnimal";
	var positionNouveauQui = document.querySelectorAll("select[name='" + qui + "']").length + 1;
	var positionCourante = positionNouveauQui <= 2 ? "" : positionNouveauQui - 1;
	var ligneCourante = document.getElementById(qui + positionCourante).parentElement.parentElement;
	var nouveauQui = document.createElement("tr");
	nouveauQui.innerHTML = '<td><label >' + titre + '</label></td>\n'
						 + '<td>'
						 + '<select name="' + qui + '" id="' + qui + positionNouveauQui + '">'
						 	+ document.getElementById(qui).innerHTML
						 + '</select>'
						 + '</td>';
	ligneCourante.parentNode.insertBefore(nouveauQui, ligneCourante.nextElementSibling);
	
	var opt = document.querySelectorAll("#" + qui + positionNouveauQui + " [selected]");
	if(opt.length != 0){
		opt[0].removeAttribute("selected");
		var defaultOpt = document.querySelectorAll("#" + qui + positionNouveauQui + " option:first-child");
		defaultOpt[0].setAttribute("selected", "selected");
	}
	return false;
}

function ajoutObjet(titre){
	var quoi = "quoi";
	var positionNouveauQuoi = document.querySelectorAll("input[name='" + quoi + "']").length + 1;
	var positionCourante = positionNouveauQuoi <= 2 ? "" : positionNouveauQuoi - 1;
	var ligneCourante = document.getElementById(quoi + positionCourante).parentElement.parentElement;
	var nouveauQuoi = document.createElement("tr");
	nouveauQuoi.innerHTML = '<td><label >' + titre + '</label></td>\n'
						 + '<td>'
						 + '<input type="text" name="quoi" id="' + quoi + positionNouveauQuoi + '"/>'
						 + '</td>';
	ligneCourante.parentNode.insertBefore(nouveauQuoi, ligneCourante.nextElementSibling);
	return false;
}