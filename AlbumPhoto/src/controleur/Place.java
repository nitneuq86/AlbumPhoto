package controleur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.atlas.json.JsonArray;
import org.apache.jena.atlas.json.JsonBuilder;
import org.apache.jena.atlas.json.JsonObject;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

import com.fasterxml.jackson.annotation.JsonValue;

import metier.Sparql;

/**
 * Servlet implementation class Place
 * Cela permet de récupérer une string qui contient un endroit possible (ou une substring) et de vérifier sur la dbpédia
 * quels sont les endroits qui correspondent.
 */
@WebServlet("/Place")
public class Place extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Place() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/vue/errorPage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String place = request.getParameter("place");
		
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
		
		response.setContentType("text/plain");  
		response.setCharacterEncoding("UTF-8"); 
		response.getWriter().write(places.toString());
	}

}
