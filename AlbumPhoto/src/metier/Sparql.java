package metier;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonObject;
import org.apache.jena.atlas.web.auth.HttpAuthenticator;
import org.apache.jena.atlas.web.auth.SimpleAuthenticator;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.jena.sparql.expr.aggregate.AggGroupConcatDistinct;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

import modele.Photo;
import sun.applet.AppletThreadGroup;

public class Sparql {
	public static final String ONTOLOGIE = "http://myrquent.org/albumz#";
	public static final String GRAPH_DEFAULT = "<http://imss.upmf-grenoble.fr/abdelfam>";
	public static final String SPARQL_ENDPOINT = "https://imss-www.upmf-grenoble.fr/sparql";
	
	private static final Sparql SPARQL = new Sparql();
	private HttpAuthenticator authenticator;
	private String prefixs;
	
	private Sparql() {
		authenticator = new SimpleAuthenticator("abdelfam", "abdelfam2015".toCharArray());
		prefixs = "PREFIX : <" + ONTOLOGIE + ">"
				+ "PREFIX IMSS: " + GRAPH_DEFAULT + ""
				+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
				+ "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>";
	}
	
	public static Sparql getSparql(){
		return SPARQL;
	}
	
	public ResultSet requeteSPARQL(String requete, String graph){
		try (QueryExecution qe = QueryExecutionFactory.sparqlService(SPARQL_ENDPOINT,
				prefixs + " " + requete, 
				graph,
				authenticator)) {

			ResultSet rs = qe.execSelect();;
			rs = ResultSetFactory.copyResults(rs) ;
			AggGroupConcatDistinct group;
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
		ResultSet  resultatPersonnes =  requeteSPARQL(requetePersonnes, "http://imss.upmf-grenoble.fr/abdelfam");
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
		ResultSet  resultatAnimaux =  requeteSPARQL(requeteAnimaux, "http://imss.upmf-grenoble.fr/abdelfam");
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
		ResultSet  resultatEvenement =  requeteSPARQL(requeteEvenement, "http://imss.upmf-grenoble.fr/abdelfam");
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
		
		ResultSet  resultatPlace =  Sparql.getSparql().requeteSPARQL(requetePlace, "http://dbpedia.org");
		String resultat = "";
		while (resultatPlace.hasNext()) {
			QuerySolution s = resultatPlace.nextSolution();
			resultat = s.getLiteral("?nom").getString();
		}
		
		return resultat;
	}
	
	public Photo getSemAttributs(modele.Photo photo) {
		String requeteAttributsPhoto = "SELECT ?date ?photographe ?ou (GROUP_CONCAT ( DISTINCT ?qui;separator=\"|\") as ?quis) (GROUP_CONCAT ( DISTINCT ?quoi;separator=\"|\") as ?quois) ?evenement "
				   + "WHERE"
				   + "{"
				   + "	?photo :when ?date ;"
				   + "		    :creator ?photographeURI ;"
				   + "			:where ?ou ."
				   + "	?photographeURI foaf:firstName ?prenom ;"
				   + "					foaf:familyName ?nomFamille ."
				   + "	OPTIONAL { "
				   + "		?photo :who ?quiURI ."
				   + "		?quiURI	foaf:firstName ?quiPrenom ;"
				   + "				foaf:familyName ?quiNomFamille"
				   + "		BIND(CONCAT(?quiPrenom, \" \", ?quiNomFamille) AS ?qui)"
				   + "	}"
                   + "	OPTIONAL { "
                   + "		?photo :what ?quoiURI ."
                   + "		?quoiURI :title ?quoi"
                   + "	}"
                   + "	OPTIONAL { ?photo :type ?evenement}"
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
			String[] quis = s.getLiteral("?quis") == null ? null : s.getLiteral("?quis").getString().split("\\|");
			String[] quois = s.getLiteral("?quois") == null ? null : s.getLiteral("?quois").getString().split("\\|");
			String evenement = s.getResource("?evenement") == null ? "" : s.getResource("?evenement").getURI();
			
			photo.setDate(date);
			photo.setPhotographe(photographe);
			photo.setOu(ou);
			photo.setQui(quis);
			photo.setQuoi(quois);
			photo.setEvenement(evenement);
		}
		
		return photo;
	}
	
	public void supprimerPhotoGraph(int id, int idAlbum, String url){
		String supprPhoto = 
				  "DELETE { GRAPH " + Sparql.GRAPH_DEFAULT + " {:photo" + id + " ?p ?v}} "
			    + " USING " + Sparql.GRAPH_DEFAULT + " WHERE{"
				+ "		:photo" + id + " ?p ?v ."
		        + "	}";
		Sparql.getSparql().requeteCRUD(supprPhoto);
				
		String supprPhotoAlbum = 
				  "DELETE { GRAPH " + Sparql.GRAPH_DEFAULT + " {:album" + idAlbum + " :hasPhoto :photo" + id + " }}"
				  		+ "USING " + Sparql.getSparql().GRAPH_DEFAULT + " WHERE {}";
				Sparql.getSparql().requeteCRUD(supprPhotoAlbum);
		
		try {
			System.out.println("File deleted : " + Files.deleteIfExists(Paths.get(filtre.FiltrePermissions.PATH_WORKSPACE + modele.Photo.path + url)));
		} catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public void ajoutPhotoGraph(int id, String titre, String date, String createur, int idAlbum, String ou, String[] qui, String[] quoi, String evenement) {	
		String quoiString = "";
		String selfie = "";
		String evenementString = "";
		for (int i = 0; i < quoi.length; i++) {
			if(!quoi[i].trim().equals("")){
				String uriQuoi = ":" + quoi[i].trim().replace(" ", "_").toLowerCase();
				String insereQuoi = 
						  "INSERT DATA {GRAPH IMSS: {"
				        + uriQuoi + " a :Objet ;"
		        		+ ":title \"" + quoi[i] + "\" ."
				        + "} }";
				Sparql.getSparql().requeteCRUD(insereQuoi);
				
				quoiString += " :what " + uriQuoi + " ;";
			}
		}
		
		String quiString = "";
		for (int i = 0; i < qui.length; i++) {
			if(!qui[i].equals("")){
				quiString += " :who <" + qui[i] + ">";
				if(i == qui.length - 1){
					quiString += " .";
				} else {
					quiString += " ;";
				}
			}
		}
		
		if(qui.length == 1 && ("<" + qui[0] + ">").equals(createur)){
			selfie += "rdf:type :Selfie ;"; 
		}
		
		if(!evenement.equals("")){
			evenementString += ":type <" + evenement + "> ;";
		}
		
		String inserePhoto = 
				  "INSERT DATA {GRAPH IMSS: {"
		        + "		:photo" + id + " rdf:type :Photo ;"
		        + "     				 dc:title \"" + titre + "\" ;"
        		+ "						 :hasID \"" + id + "\" ;"
				+ "						 :when \"" + date + "\" ;"
				+ "						 :creator " + createur + " ;"
				+ "						 :hasAlbum :album" + idAlbum + " ;"
				+ "						 " + selfie
				+ "						 :where " + ou + " ;"
				+ "						 " + quoiString
				+ "					 	 " + evenementString
				+ "						 " + quiString
		        + "} }";
		Sparql.getSparql().requeteCRUD(inserePhoto);
				
		String insereDansAlbum = 
				  "INSERT DATA {GRAPH IMSS: {"
		        + ":album" + idAlbum + " :hasPhoto :photo" + id + " ."
		        + "} }";
		Sparql.getSparql().requeteCRUD(insereDansAlbum);
	}
	
	public void ajoutAlbumGraph(int id, String titre) {
		String insereAlbum = 
		  "INSERT DATA {GRAPH IMSS: {"
	    + ":album" + id + " rdf:type :Album ;"
	    + "        dc:title \"" + titre + "\" ."
	    + "} }";
		Sparql.getSparql().requeteCRUD(insereAlbum);
	}
	
	public void supprimerAlbumGraph(int id){
		String supprAlbum = 
				  "DELETE { GRAPH " + Sparql.GRAPH_DEFAULT + " {:album" + id + " ?p ?v}} "
			    + " USING " + Sparql.GRAPH_DEFAULT + " WHERE{"
				+ "		:album" + id + " ?p ?v ."
		        + "	}";
				Sparql.getSparql().requeteCRUD(supprAlbum);
	}

	public JsonArray getMatchPlace(String place) {
		//On récupère tous 6 premiers résultats correspondant à l'endroit entré par l'utilisateur
		String requete = "SELECT ?ville ?nomVille "
				   + "WHERE"
				   + "{"
				   + "	?ville a <http://www.w3.org/2003/01/geo/wgs84_pos#SpatialThing> ;"
				   + "		   foaf:name ?nomVille . "
				   + "	FILTER(contains(lcase(?nomVille), \"" + place.toLowerCase() + "\"))"
				   + "} LIMIT 6";
				
		//Execution de la requête sur le graph imss
		ResultSet  resultat =  Sparql.getSparql().requeteSPARQL(requete, "http://dbpedia.org");
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
}
