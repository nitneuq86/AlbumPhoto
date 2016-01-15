package controleur;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

import com.sun.xml.internal.ws.resources.ModelerMessages;

import dao.DAOFactory;
import metier.Sparql;

/**
 * Servlet implementation class Rechercher
 */
@WebServlet("/Rechercher")
public class Rechercher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Rechercher() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<modele.Personne> personnes = Sparql.getSparql().getPersonnes();
		ArrayList<modele.Personne> animaux = Sparql.getSparql().getAnimaux();
		
//		request.setAttribute("pathImages", modele.Photo.path);	
		request.setAttribute("personnes", personnes);
		request.setAttribute("animaux", animaux);
		
		this.getServletContext().getRequestDispatcher("/vue/rechercher.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<modele.Personne> personnes = Sparql.getSparql().getPersonnes();
		ArrayList<modele.Personne> animaux = Sparql.getSparql().getAnimaux();
		String personne = request.getParameter("personne");
		
		String requeteRecherche = "SELECT ?id "
				   + "WHERE"
				   + "{"
				   + "	?photo rdf:type :Photo ;"
				   + "	?photo :hasID ?id"
				   + " "
				   + "}";
		//Execution de la requête sur le graph imss
		ResultSet  resultatRecherche =  Sparql.getSparql().requeteSPARQL(requeteRecherche, "http://imss.upmf-grenoble.fr/abdelfam");
		ArrayList<modele.Photo> photos = new ArrayList<modele.Photo>();
		while (resultatRecherche.hasNext()) {
			QuerySolution s = resultatRecherche.nextSolution();
			modele.Photo photo = DAOFactory.getInstance().getPhotoDao().read(s.getLiteral("?id").getInt());
			photos.add(photo);
		}
		
		request.setAttribute("personnes", personnes);
		request.setAttribute("animaux", animaux);
		request.setAttribute("photos", photos);
		
		this.getServletContext().getRequestDispatcher("/vue/rechercher.jsp").forward(request, response);
	}
}
