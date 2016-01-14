package metier;

import javax.persistence.Persistence;

import org.apache.jena.atlas.web.auth.HttpAuthenticator;
import org.apache.jena.atlas.web.auth.SimpleAuthenticator;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;

public class Sparql {
	private static final Sparql SPARQL = new Sparql();
	private HttpAuthenticator authenticator;
	private String prefixs;
	
	private Sparql() {
		authenticator = new SimpleAuthenticator("abdelfam", "abdelfam2015".toCharArray());
		prefixs = "PREFIX : <http://myrquent.org/albumz#>"
				+ "PREFIX IMSS: <http://imss.upmf-grenoble.fr/abdelfam>"
				+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "PREFIX dc: <http://purl.org/dc/elements/1.1/>"
				+ "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>";
	}
	
	public static Sparql getSparql(){
		return SPARQL;
	}
	
	public ResultSet requete(String requete, String graph){
		try (QueryExecution qe = QueryExecutionFactory.sparqlService("https://imss-www.upmf-grenoble.fr/sparql",
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
}
