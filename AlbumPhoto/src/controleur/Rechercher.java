package controleur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.lang.ArrayUtils;
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
//		ArrayList<modele.Personne> personnes = Sparql.getSparql().getPersonnes();
//		ArrayList<modele.Personne> animaux = Sparql.getSparql().getAnimaux();
//		ArrayList<modele.Evenement> evenements = Sparql.getSparql().getEvenement();
//		List<String> caracteristiques = Sparql.getSparql().getCaracteristiques();
		
//		request.setAttribute("personnes", personnes);
//		request.setAttribute("animaux", animaux);
//		request.setAttribute("evenements", evenements);
//		request.setAttribute("caracteristiques", caracteristiques);

		this.getServletContext().getRequestDispatcher("/vue/rechercher.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String titre = request.getParameter("titre");
		List<String>  quis = new ArrayList<String>(Arrays.asList(request.getParameterValues("qui")));
		List<String>  quisAnimal = new ArrayList<String>(Arrays.asList(request.getParameterValues("quiAnimal")));
		List<String> quois = new ArrayList<String>(Arrays.asList(request.getParameterValues("quoi")));
		String ptVue = request.getParameter("ptVue");
		String type = request.getParameter("type");
		String caracteristique = request.getParameter("caracteristique");
		String createur = request.getParameter("createur");
		String ou = request.getParameter("ou");
		String ouHidden = request.getParameter("ou-hidden");
		boolean ouEtendu = request.getParameter("ouEtendu") != null ? true : false;
		boolean selfie = request.getParameter("selfie") != null ? true : false;
		boolean aucun = request.getParameter("aucune") != null ? true : false;
		boolean quelquun = request.getParameter("quelquun") != null ? true : false;
		String dateDebut = request.getParameter("dateDebut");
		String dateFin = request.getParameter("dataFin");
		
		quis.removeAll(Collections.singleton(""));
		quisAnimal.removeAll(Collections.singleton(""));
		quis.addAll(quisAnimal);
		quois.removeAll(Collections.singleton(""));
		
		ArrayList<modele.Personne> personnes = Sparql.getSparql().getPersonnes();
		ArrayList<modele.Personne> animaux = Sparql.getSparql().getAnimaux();
		ArrayList<modele.Evenement> evenements = Sparql.getSparql().getEvenement();
		List<String> caracteristiques = Sparql.getSparql().getCaracteristiques();
		ArrayList<modele.Photo> photos = Sparql.getSparql().getPhotos(titre, quis.toArray(new String[quis.size()]), 
				quois.toArray(new String[quois.size()]), type, ptVue, caracteristique, createur, ouHidden, ouEtendu, 
				selfie, aucun, quelquun, dateDebut, dateFin);
		
		request.setAttribute("titreRequete", titre);
		request.setAttribute("quis", quis.size() == 0 ? null : quis);
		request.setAttribute("quisAnimal", quisAnimal.size() == 0 ? null : quisAnimal);
		request.setAttribute("quois", quois.size() == 0 ? null : quois);
		request.setAttribute("ptVueRequete", ptVue);
		request.setAttribute("evenementRequete", type);
		request.setAttribute("caracteristiqueRequete", caracteristique);
		request.setAttribute("createurRequete", createur);
		request.setAttribute("ouRequete", ou);
		request.setAttribute("ouHiddenRequete", ouHidden);
		request.setAttribute("selfieRequete", selfie);
		request.setAttribute("aucunRequete", aucun);
		request.setAttribute("quelquunRequete", quelquun);
		request.setAttribute("dateDebutRequete", dateDebut);
		request.setAttribute("dateFinRequete", dateFin);
		
		request.setAttribute("personnes", personnes);
		request.setAttribute("animaux", animaux);
		request.setAttribute("evenements", evenements);
		request.setAttribute("photos", photos);
		request.setAttribute("pathImages", modele.Photo.path);
		request.setAttribute("caracteristiques", caracteristiques);
		
		this.getServletContext().getRequestDispatcher("/vue/rechercher.jsp").forward(request, response);
	}
}
