package extra;

import java.util.ArrayList;

import org.apache.jena.atlas.web.auth.HttpAuthenticator;
import org.apache.jena.atlas.web.auth.SimpleAuthenticator;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

import metier.Sparql;
import modele.Album;
import modele.Utilisateur;

public class Data {
	private static ArrayList<Utilisateur> bddUtilisateurs = new ArrayList<Utilisateur>();

	public static Album getAlbumTest() {
//		Album album = new Album("Mes photos perso", new Date(), new Personne("Myriam", "Abdel-Fattah"));
//		album.ajouterPhoto("http://img0.mxstatic.com/wallpapers/44e535006cffbc1b6e41f72d5e9df1e4_large.jpeg");
//		album.ajouterPhoto("http://www.unesourisetmoi.info/wall32/images/paysage-fonds-ecran_04.jpg");
//		album.ajouterPhoto("http://lemag.promovacances.com/wp-content/uploads/2013/08/Paysage-portugal.jpg");
//		album.ajouterPhoto("http://en.lesalondelaphoto.com/var/comexposium/storage/images/media/salon-de-la-photo-medias/images/suivre-le-salon-de-la-photo-sur-les-reseaux-sociaux/1482353-1-fre-FR/Suivre-le-Salon-de-la-photo-sur-les-reseaux-sociaux_Tuile_980x430.jpg");
//		album.ajouterPhoto("http://www.lechemin.ch/wp-content/uploads/2015/05/connaitre.jpg");
//		album.ajouterPhoto("http://www.laboiteverte.fr/wp-content/uploads/2015/01/meilleure-image-flickr-29.jpg");
//		album.ajouterPhoto("http://cssslider.com/sliders/demo-26/data1/images/summerfield336672_1280.jpg");
//		album.ajouterPhoto("http://img00.deviantart.net/ae17/i/2013/118/4/6/rainbow_flower_by_i_is_kitty-d5l8o1g.jpg");
//		album.ajouterPhoto("http://cdn.theatlantic.com/assets/media/img/photo/2015/10/photos-of-the-week-926-1002/w04_RTS2PNN/main_900.jpg?1443979188");
		return null;
	}

	public static Utilisateur verificationUtilisateur(String login, String pass) {
//		bddUtilisateurs.add(new Utilisateur(new Personne("Myriam", "Abdel-Fattah"), "Myrga"));
//		bddUtilisateurs.add(new Utilisateur(new Personne("Quentin", "Durand"), "Nitneuq"));
//
//		boolean UtilisateurTrouve = false;
//		int i = 0;
//		while (!UtilisateurTrouve && i < bddUtilisateurs.size()) {
//			if (bddUtilisateurs.get(i).getLogin().equals(login)) {
//				UtilisateurTrouve = true;
//				if (pass.equals("prout")) {
//					return bddUtilisateurs.get(i);
//				}
//			}
//			i++;
//		}
		return null;
	}
	
	public static void main(String[] args) {
//		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
		
//		String supprPhotoAlbum = 
//				"DELETE { GRAPH " + Sparql.GRAPH_DEFAULT + " {?album ?p ?v}} "
//					    + " USING " + Sparql.GRAPH_DEFAULT + " WHERE{"
//						+ "		?album ?p ?v ."
//						+ "		FILTER (?album = :album 6002 && ?p = :hasPhoto && ?v = :photo 6351 )"
//				        + "	}";
//		System.out.println("Machin");
		
//		HttpAuthenticator authenticator = new SimpleAuthenticator("abdelfam", "abdelfam2015".toCharArray());
//		UpdateRequest req = UpdateFactory.create("CLEAR GRAPH  <http://imss.upmf-grenoble.fr/abdelfam>");
//		UpdateProcessor up = UpdateExecutionFactory.createRemoteForm(req, "https://imss-www.upmf-grenoble.fr/sparql", authenticator);
//		up.execute();
		
//		String insertAlbum = "PREFIX : <http://myrquent.org/albumz#> "
//				+ "PREFIX IMSS: <http://imss.upmf-grenoble.fr/abdelfam>"
//				+ "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
//				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
//				+ "INSERT DATA {GRAPH IMSS: {"
//                + ":album1 rdf:type :Album ;"
//                + "        dc:title \"Album 1\" ;"
//                + "		   :hasPhoto :photo1 ."
//                + "} }";
//		
//		String insertPhoto = "PREFIX : <http://myrquent.org/albumz#> "
//							+ "PREFIX IMSS: <http://imss.upmf-grenoble.fr/abdelfam>"
//							+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
//							+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
//							+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
//							+ "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
//            				+ "INSERT DATA {GRAPH IMSS: {"
//			                + ":photo1 rdf:type :Photo ;"
//			                + "        :when \"2015-09-24\"^^xsd:dateTime ;"
//			                + "        dc:title \"Photo 1\"^^xsd:string ;"
//			                + "		   :hasAlbum :album1 ;"
//			                + "        :who :dahlia ,"
//			                + "             :isabelle ,"
//			                + "				:mohamed ,"
//			                + "				:myriam , "
//			                + "				:quentin ,"
//			                + "				:samy ."
//			                + "} }";
//		
//		HttpAuthenticator authenticator = new SimpleAuthenticator("abdelfam", "abdelfam2015".toCharArray());
//		UpdateRequest req = UpdateFactory.create(insertAlbum);
//		UpdateProcessor up = UpdateExecutionFactory.createRemoteForm(req, "https://imss-www.upmf-grenoble.fr/sparql", authenticator);
//		up.execute();
        
		
		
//		try (QueryExecution qe = QueryExecutionFactory.sparqlService("https://imss-www.upmf-grenoble.fr/sparql",
//                "SELECT ?s ?p ?o WHERE { ?s ?p ?o }", 
//                "http://imss.upmf-grenoble.fr/abdelfam",
//                authenticator)) {
//
//				ResultSet rs = qe.execSelect();
//				while (rs.hasNext()) {
//					QuerySolution s = rs.nextSolution();
//					System.out.println(s.getResource("?s") + " " + s.getResource("?p") + " " + s.getResource("?o"));
//				}
//		}	
	}
}
