package metier;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.lang.ArrayUtils;
import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;
import org.apache.jena.atlas.web.auth.HttpAuthenticator;
import org.apache.jena.atlas.web.auth.SimpleAuthenticator;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

import dao.DAOFactory;
import modele.Photo;

public class Sparql {
	public static final String ONTOLOGIE = "http://myrquent.org/albumz#";
	public static final String GRAPH_DEFAULT = "http://imss.upmf-grenoble.fr/abdelfam";
	public static final String GRAPH_DBPEDIA = "http://dbpedia.org";
	public static final String SPARQL_ENDPOINT = "https://imss-www.upmf-grenoble.fr/sparql";
	
	private static final Sparql SPARQL = new Sparql();
	private HttpAuthenticator authenticator;
	private String prefixs;
	private List<String> caracteristiques;
	
	private Sparql() {
		authenticator = new SimpleAuthenticator("abdelfam", "abdelfam2015".toCharArray());
		prefixs = "PREFIX : <" + ONTOLOGIE + ">"
				+ "PREFIX IMSS: <" + GRAPH_DEFAULT + ">"
				+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
				+ "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>"
				+ "PREFIX xml: <http://www.w3.org/2001/XMLSchema#>";
		DefaultMutableTreeNode tree = traitementCaracteristiques(new DefaultMutableTreeNode(), "caracteristic", 0);
		Enumeration<String> enumerationTree = tree.breadthFirstEnumeration();
		this.caracteristiques = Collections.list(enumerationTree);
		this.caracteristiques = caracteristiques.subList(1, caracteristiques.size() - 1);
	}
	
	public List<String> getCaracteristiques(){
		return this.caracteristiques;
	}
	
	public static Sparql getSparql(){
		return SPARQL;
	}
	
	public ResultSet requeteSPARQL(String requete, String graph){
		try (QueryExecution qe = QueryExecutionFactory.sparqlService(SPARQL_ENDPOINT,
				prefixs + " " + requete, 
				graph,
				authenticator)) {
//			System.out.println(qe.getQuery());
			ResultSet rs = qe.execSelect();;
			rs = ResultSetFactory.copyResults(rs) ;
			return rs ;
		} catch(Exception ex){
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	public void requeteCRUD(String requete ){
		UpdateRequest req = UpdateFactory.create(prefixs + " " + requete);
		UpdateProcessor up = UpdateExecutionFactory.createRemoteForm(req, SPARQL_ENDPOINT, authenticator);
		up.execute();
	}
	
	public ArrayList<modele.Personne> getPersonnes(){
		//Requête permettant de récupérer toutes les personnes présentes dans le graphe
		String requetePersonnes = "SELECT ?person ?firstName ?familyName "
							   + "WHERE"
							   + "{"
							   + "	?person rdf:type foaf:Person ;"
							   + "	foaf:firstName ?firstName ;"
							   + "	foaf:familyName ?familyName ."
							   + "}";
				
		//Execution de la requête sur le graph imss
		ResultSet  resultatPersonnes =  requeteSPARQL(requetePersonnes, GRAPH_DEFAULT);
		ArrayList<modele.Personne> personnes = new ArrayList<modele.Personne>();
		//Pour chaque résultat, on stocke les personnes dans un tableau de Personne
		while (resultatPersonnes.hasNext()) {
			QuerySolution s = resultatPersonnes.nextSolution();
			personnes.add(new modele.Personne(s.getLiteral("?firstName").toString(), s.getLiteral("?familyName").toString(), s.getResource("?person").toString()));
		}
		return personnes;
	}
	
	public ArrayList<modele.Personne> getAnimaux(){
		String requeteAnimaux = "SELECT ?animal ?title "
				   + "WHERE"
				   + "{"
				   + "	?animal a :Animal ;"
				   + "	:title ?title ;"
				   + "}";
		
		//Execution de la requête sur le graph imss
		ResultSet  resultatAnimaux =  requeteSPARQL(requeteAnimaux, GRAPH_DEFAULT);
		ArrayList<modele.Personne> animaux = new ArrayList<modele.Personne>();
		//Pour chaque résultat, on stocke les animaux dans un tableau de Personne
		while (resultatAnimaux.hasNext()) {
			QuerySolution s = resultatAnimaux.nextSolution();
			animaux.add(new modele.Personne(s.getLiteral("?title").toString(), "", s.getResource("?animal").toString()));
		}
		
		return animaux;
	}
	
	public ArrayList<modele.Evenement> getEvenement(){
		String requeteEvenement = "SELECT ?evenement ?title "
				   + "WHERE"
				   + "{"
				   + "	?evenement a :Event ;"
				   + "	:title ?title ;"
				   + "}";
		
		//Execution de la requête sur le graph imss
		ResultSet  resultatEvenement =  requeteSPARQL(requeteEvenement, GRAPH_DEFAULT);
		ArrayList<modele.Evenement> evenements = new ArrayList<modele.Evenement>();
		//Pour chaque résultat, on stocke les evenements dans un tableau d'Evenement
		while (resultatEvenement.hasNext()) {
			QuerySolution s = resultatEvenement.nextSolution();
			evenements.add(new modele.Evenement(s.getLiteral("?title").toString(), s.getResource("?evenement").toString()));
		}
		
		return evenements;
	}
	
	public String getPlace(String placeUri){
		String requetePlace = "SELECT ?nom "
				   + "WHERE"
				   + "{"
				   + "	<" + placeUri + "> foaf:name ?nom ."
				   + "}";
		
		ResultSet  resultatPlace = requeteSPARQL(requetePlace, GRAPH_DBPEDIA);
		String resultat = "";
		while (resultatPlace.hasNext()) {
			QuerySolution s = resultatPlace.nextSolution();
			resultat = s.getLiteral("?nom").getString();
		}
		
		return resultat;
	}
	
	public Photo getSemAttributs(modele.Photo photo) {
		String requeteAttributsPhoto = "SELECT ?date ?photographe ?ou "
				   + "						(GROUP_CONCAT ( DISTINCT ?qui;separator=\"|\") AS ?quis) "
				   + "						(GROUP_CONCAT ( DISTINCT ?quiAnimal;separator=\"|\") AS ?quisAnimal)"
				   + "						(GROUP_CONCAT ( DISTINCT ?quoi;separator=\"|\") AS ?quois) "
				   + "						?evenement "
				   + "WHERE"
				   + "{"
				   + "	?photo :when ?date ;"
				   + "		    :creator ?photographeURI ;"
				   + "			:where ?ou ."
				   + "	?photographeURI foaf:firstName ?prenom ;"
				   + "					foaf:familyName ?nomFamille ."
				   + "	OPTIONAL { "
				   + "		?photo :who ?quiURI ."
				   + "		OPTIONAL { "
				   + "			?quiURI	foaf:firstName ?quiPrenom ;"
				   + "					foaf:familyName ?quiNomFamille ."
				   + "			BIND(CONCAT(?quiPrenom, \" \", ?quiNomFamille) AS ?qui)"
				   + "		}"
				   + "		OPTIONAL {"
				   + "			?quiURI	:title ?quiAnimal ."
				   + "		}"
				   + "	}"
                   + "	OPTIONAL { "
                   + "		?photo :what ?quoiURI ."
                   + "		?quoiURI :title ?quoi"
                   + "	}"
                   + "	OPTIONAL { "
                   + "		?photo :type ?evenementURI ."
                   + "		?evenementURI :title ?evenement}"
                   + "	BIND(CONCAT(?prenom, \" \", ?nomFamille) AS ?photographe)"
                   + "	FILTER(?photo = :photo" + photo.getId() + ")"
				   + "}"
				   + "GROUP BY ?date ?photographe ?ou ?evenement";
		
		
		ResultSet  resultatAttributsPhoto =  requeteSPARQL(requeteAttributsPhoto, 
				"http://imss.upmf-grenoble.fr/abdelfam");
		
		while (resultatAttributsPhoto.hasNext()) {
			QuerySolution s = resultatAttributsPhoto.nextSolution();
			String date = s.getLiteral("?date").getString();
			String photographe = s.getLiteral("?photographe").getString();
			String ou = getPlace(s.getResource("?ou").getURI());
			String[] quis = s.getLiteral("?quis").getString().equals("") ? null : s.getLiteral("?quis").getString().split("\\|");
			String[] quisAnimal = s.getLiteral("?quisAnimal").getString().equals("") ? null : s.getLiteral("?quisAnimal").getString().split("\\|");
			String[] quois = s.getLiteral("?quois").getString().equals("") ? null : s.getLiteral("?quois").getString().split("\\|");
			String evenement = s.getLiteral("?evenement") == null ? "" : s.getLiteral("?evenement").getString();
			
			photo.setDate(date);
			photo.setPhotographe(photographe);
			photo.setOu(ou);
			photo.setQui((String[])ArrayUtils.addAll(quis, quisAnimal));
			photo.setQuoi(quois);
			photo.setEvenement(evenement);
		}
		
		return photo;
	}
	
	public void supprimerPhotoGraph(int id, int idAlbum, String url){
		String supprPhoto = 
				  "DELETE { GRAPH <" + GRAPH_DEFAULT + "> {:photo" + id + " ?p ?v}} "
			    + " USING <" + GRAPH_DEFAULT + "> WHERE{"
				+ "		:photo" + id + " ?p ?v ."
		        + "	}";
		requeteCRUD(supprPhoto);
				
		String supprPhotoAlbum = 
				  "DELETE { GRAPH <" + GRAPH_DEFAULT + "> {:album" + idAlbum + " :hasPhoto :photo" + id + " }}"
				  		+ "USING <" + GRAPH_DEFAULT + "> WHERE {}";
				requeteCRUD(supprPhotoAlbum);
		
		try {
			System.out.println("File deleted : " + Files.deleteIfExists(Paths.get(filtre.FiltrePermissions.PATH_WORKSPACE + modele.Photo.path + url)));
		} catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public String parseQuois(String[] quoi, boolean ajout){
		String quoiString = "";
		String quoiURI = "";
		String quoiNom = "";
		String quoiFilter = "";
		
		if(ajout == false && quoi.length > 0) quoiFilter += "FILTER ( ";
		for (int i = 0; i < quoi.length; i++) {
			if(!quoi[i].trim().equals("")){
				if(ajout){
					String uriQuoi = ":" + quoi[i].trim().replace(" ", "_").toLowerCase();
					String insereQuoi = 
							  "INSERT DATA {GRAPH IMSS: {"
					        + uriQuoi + " a :Objet ;"
			        		+ ":title \"" + quoi[i] + "\" ."
					        + "} }";
					Sparql.getSparql().requeteCRUD(insereQuoi);
					quoiString += " :what " + uriQuoi + " ;";
				} else {
					quoiURI = "?photo :what ?objetURI" + i + " . ";
					quoiNom = "?objetURI" + i + " :title ?objet" + i + " . ";
					if(i == quoi.length - 1)
						quoiFilter += "(CONTAINS(lCASE(?objet" + i + "), \"" + quoi[i].trim().toLowerCase() + "\")))";
					else
						quoiFilter += "(CONTAINS(lCASE(?objet" + i + "), \"" + quoi[i].trim().toLowerCase() + "\")) || ";
				}
			}
		}
		return (ajout == false && quoi.length > 0) ? quoiURI + quoiNom + quoiFilter : quoiString;
	}
	
	public String parseQuis(String[] qui){
		String quiString = "";
		for (int i = 0; i < qui.length; i++) {
			if(!qui[i].equals("")){
				quiString += " :who <" + qui[i] + ">";
				quiString += " ;";
			}
		}
		return quiString;
	}
	
	public String parseType(String evenement){
		String evenementString = "";
		if(!evenement.equals("")){
			evenementString += ":type <" + evenement + "> ;";
		}
		return evenementString;
	}
	
	public String isSelfie(String[] qui, String createur){
		String selfie = "";
		if(qui.length == 1 && ("<" + qui[0] + ">").equals(createur)){
			selfie += "rdf:type :Selfie ;"; 
		}
		return selfie;
	}
	
	public void ajoutPhoto(int id, String titre, String date, String createur, int idAlbum, String ou, String[] qui, String[] quoi, String evenement) {	
		String quoiString = parseQuois(quoi, true);
		String selfie = isSelfie(qui, createur);
		String typeString = parseType(evenement);		
		String quisString = parseQuis(qui);
		
		String inserePhoto = 
				  "INSERT DATA {GRAPH IMSS: {"
		        + "		:photo" + id + " rdf:type :Photo ;"
		        + "     				 dc:title \"" + titre + "\" ;"
        		+ "						 :hasID \"" + id + "\" ;"
				+ "						 :when \"" + date + "\"^^xsd:date ;"
				+ "						 :creator " + createur + " ;"
				+ "						 :hasAlbum :album" + idAlbum + " ;"
				+ "						 " + selfie
				+ "						 :where " + ou + " ;"
				+ "						 " + quoiString
				+ "					 	 " + typeString
				+ "						 " + quisString
		        + "} }";
		Sparql.getSparql().requeteCRUD(inserePhoto);
				
		String insereDansAlbum = 
				  "INSERT DATA {GRAPH IMSS: {"
		        + ":album" + idAlbum + " :hasPhoto :photo" + id + " ."
		        + "} }";
		Sparql.getSparql().requeteCRUD(insereDansAlbum);
	}
	
	public void ajoutAlbum(int id, String titre) {
		String insereAlbum = 
		  "INSERT DATA {GRAPH IMSS: {"
	    + ":album" + id + " rdf:type :Album ;"
	    + "        dc:title \"" + titre + "\" ."
	    + "} }";
		Sparql.getSparql().requeteCRUD(insereAlbum);
	}
	
	public void supprimerAlbum(int id){
		String supprAlbum = 
				  "DELETE { GRAPH <" + Sparql.GRAPH_DEFAULT + "> {:album" + id + " ?p ?v}} "
			    + " USING <" + Sparql.GRAPH_DEFAULT + "> WHERE{"
				+ "		:album" + id + " ?p ?v ."
		        + "	}";
				Sparql.getSparql().requeteCRUD(supprAlbum);
	}

	public JsonArray getMatchPlace(String place) {
		//On récupère tous 6 premiers résultats correspondant à l'endroit entré par l'utilisateur
		String requete = "SELECT ?ville ?nomVille "
				   + "WHERE"
				   + "{"
				   + "	?ville a geo:SpatialThing ;"
				   + "		   foaf:name ?nomVille . "
				   + "	FILTER(contains(lcase(?nomVille), \"" + place.toLowerCase() + "\"))"
				   + "} LIMIT 6";
				
		//Execution de la requête sur le graph imss
		ResultSet  resultat =  Sparql.getSparql().requeteSPARQL(requete, GRAPH_DBPEDIA);
		JsonArray places = new JsonArray();
		
		while (resultat.hasNext()) {
			QuerySolution s = resultat.nextSolution();
			JsonObject resultatPlace = new JsonObject();
			resultatPlace.put("place", s.getLiteral("?nomVille").getString());
			resultatPlace.put("uri", s.getResource("?ville").toString());
			places.add(resultatPlace);
		}
		
		return places;
	}
	
	public String parseTitre(String titre){
		String titreRequete = "";
		if(!titre.equals("")){
			titreRequete += "FILTER(CONTAINS(UCASE(?titre), \"" + titre.toUpperCase() + "\"))";
		}
		return titreRequete;
	}
	
	public DefaultMutableTreeNode  traitementCaracteristiques(DefaultMutableTreeNode tree, String caracteristic, int index){
		String requeteCaracteristiques = "SELECT ?caracteristic "
		   + "WHERE"
		   + "{"
		   + "	?caracteristic rdfs:subPropertyOf :" + caracteristic + " ."
		   + "	FILTER (!isBlank(?caracteristic))"
		   + "}";
		//Execution de la requête sur le graph imss
		ResultSet  resultatCaracteristiques =  requeteSPARQL(requeteCaracteristiques, GRAPH_DEFAULT);
		while(resultatCaracteristiques.hasNext()){
			QuerySolution s = resultatCaracteristiques.nextSolution();
			tree.insert(traitementCaracteristiques(new DefaultMutableTreeNode(s.getResource("?caracteristic").getLocalName()), s.getResource("?caracteristic").getLocalName(), index+1), 0);
			index++;
		}
		return tree;
	}

	public ArrayList<Photo> getPhotos(String titre, String[] quis, String[] quois, String type, String ptVue, 
			String caracteristique, String createur, String ou, boolean etendu, boolean selfie, boolean aucun,
			boolean quelquun, String dateDebut, String dateFin) {
		String titreRequete = parseTitre(titre);
		String quisString = parseQuis(quis);
		String quoisString = parseQuois(quois, false);
		String typeString = parseType(type); 
		String caracteristiqueString = parseCaracteristique(caracteristique, ptVue);
		String createurString = parseCreateur(createur);
		String ouString = parseOu(ou, etendu);
		String selfieString = parseSelfie(selfie);
		String aucunString = parseAucun(aucun);
		String quelquunString = parseQuelquun(quelquun);
		String dateString = parseDate(dateDebut, dateFin);
		
		String requeteRecherche = "SELECT DISTINCT ?id " +
				  (ouString.equals("") ? "" : "FROM <" + GRAPH_DBPEDIA + ">" ) + ""
				   + "WHERE"
				   + "{"
				   + "	?photo	rdf:type :Photo ;"
				   + "			" + quisString
				   + "			" + typeString
				   + "			:hasID ?id ;"
				   + "			dc:title ?titre ."
				   + "			" + createurString
				   + "			" + selfieString
				   + "			" + caracteristiqueString
				   + "			" + quelquunString
				   + "			" + aucunString
				   + "			" + dateString
				   + "			" + ouString		
				   + "	" + quoisString
				   + "	" + titreRequete
				   + "}";
		
		//Execution de la requête sur le graph imss
		ResultSet  resultatRecherche =  requeteSPARQL(requeteRecherche, GRAPH_DEFAULT);
		ArrayList<modele.Photo> photos = new ArrayList<modele.Photo>();
		while (resultatRecherche.hasNext()) {
			QuerySolution s = resultatRecherche.nextSolution();
			modele.Photo photo = DAOFactory.getInstance().getPhotoDao().read(s.getLiteral("?id").getInt());
			photos.add(photo);
		}
		
		return photos;
	}

	private String parseDate(String dateDebut, String dateFin) {
		String dateString = "";
		if(!dateDebut.equals("")){
			if(!dateFin.equals("")){
				dateString += "?photo :when ?date . "
							+ "FILTER ((?date >= \"" + dateDebut + "\"^^xsd:date) && (?date <= \"" + dateFin + "\"^^xsd:date))";
			} else {
				dateString += "?photo :when \"" + dateDebut + "\"^^xsd:date .";
			}
		}
		return dateString;
	}

	private String parseQuelquun(boolean quelquun) {
		String quelquunString = "";
		if(quelquun){
			quelquunString += "?photo :who ?personne . ?personne rdf:type foaf:Person ";
		}
		return quelquunString;
	}

	private String parseAucun(boolean aucun) {
		String aucunString = "";
		if(aucun){
			aucunString += "FILTER NOT EXISTS { ?photo :who ?personne . ?personne rdf:type foaf:Person }";
		}
		return aucunString;
	}

	private String parseSelfie(boolean selfie) {
		String selfieString = "";
		if(selfie){
			selfieString += "?photo rdf:type :Selfie .";
		}
		return selfieString;
	}

	private String parseOu(String ou, boolean etendu) {
		String ouString = "";
		if(!ou.equals("")){
			ouString += "?photo :where ?where ."
					  + "?where ?connectedTo ?place . "
					  + " FILTER ( (?where = <" + ou + ">)";
			ouString += etendu ? " || (?place = <" + ou + ">))" : ")";
		}
		return ouString;
	}

	private String parseCreateur(String createur) {
		String createurString = "";
		if(!createur.equals("")){
			createurString += "?photo :creator <" + createur + "> . ";
		}
		return createurString;
	}

	private String parseCaracteristique(String caracteristique, String ptVue) {
		String caracteristiqueString = "";
		if(!caracteristique.equals("")){
			caracteristiqueString += "?photo :who ?qui . ";
			caracteristiqueString += "<" + ptVue + "> :" + caracteristique + " ?qui .";
		}
		return caracteristiqueString;
	}
}
