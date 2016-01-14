package metier;

import org.apache.jena.atlas.web.auth.HttpAuthenticator;
import org.apache.jena.atlas.web.auth.SimpleAuthenticator;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

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
			return rs ;
		} catch(Exception ex){
			return null;
		}
	}
	
	public void requeteCRUD(String requete ){
		UpdateRequest req = UpdateFactory.create(prefixs + " " + requete);
		UpdateProcessor up = UpdateExecutionFactory.createRemoteForm(req, SPARQL_ENDPOINT, authenticator);
		up.execute();
	}
}
