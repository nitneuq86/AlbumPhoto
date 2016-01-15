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
	var liste = ou.getAttribute("list");
	var options = document.querySelectorAll("#" + liste + " option");
	var hiddenInput = document.getElementById(ouString + "-hidden");
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

	for(var i = 0; i < options.length; i++) {
        var option = options[i];
        if(option.innerText === ou.value) {
        	estDansLaListe = true;
            hiddenInput.value = option.getAttribute('data-value');
            break;
        }
    }
    if(!estDansLaListe){
    	erreur = true;
		message += "Veuillez choisir un endroit de la liste déroulante. <br/>";
    }

    document.getElementById("message").innerHTML = message;
	
	return !erreur;
}

function verificationPlace(){
	var ou = document.getElementById('ou').value;
	if(ou.length >= 3){
		POST("Place", "place=" + ou, traitementPlaces);
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
}

function ajoutPersonne(){
	var qui = "qui";
	var positionNouveauQui = document.querySelectorAll("select[name='" + qui + "']").length + 1;
	var positionCourante = positionNouveauQui <= 2 ? "" : positionNouveauQui - 1;
	var ligneCourante = document.getElementById(qui + positionCourante).parentElement.parentElement;
	var nouveauQui = document.createElement("tr");
	nouveauQui.innerHTML = '<td><label >Qui :</label></td>\n'
						 + '<td>'
						 + '<select name="qui" id="' + qui + positionNouveauQui + '">'
						 	+ document.getElementById(qui).innerHTML
						 + '</select>'
						 + '</td>';
	ligneCourante.parentNode.insertBefore(nouveauQui, ligneCourante.nextElementSibling);
	return false;
}

function ajoutObjet(){
	var quoi = "quoi";
	var positionNouveauQuoi = document.querySelectorAll("input[name='" + quoi + "']").length + 1;
	var positionCourante = positionNouveauQuoi <= 2 ? "" : positionNouveauQuoi - 1;
	var ligneCourante = document.getElementById(quoi + positionCourante).parentElement.parentElement;
	var nouveauQuoi = document.createElement("tr");
	nouveauQuoi.innerHTML = '<td><label >Quoi :</label></td>\n'
						 + '<td>'
						 + '<input type="text" name="quoi" id="' + quoi + positionNouveauQuoi + '"/>'
						 + '</td>';
	ligneCourante.parentNode.insertBefore(nouveauQuoi, ligneCourante.nextElementSibling);
	return false;
}