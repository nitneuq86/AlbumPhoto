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

function POST(controleur, parametres){
	var xhr = getXMLHttpRequest();

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
	console.log("bidule");
	var xhr = getXMLHttpRequest();
	
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
			console.log("bidule");
			location.reload();
		}
	};
	
	xhr.open("DELETE", URL_APP + controleur, true);
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send(parametres);
	
	return false;
}