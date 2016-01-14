package controleur;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

import dao.DAOFactory;
import metier.Sparql;

@WebServlet("/GestionnairePhotos/*")
@MultipartConfig
public class GestionnairePhotos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public GestionnairePhotos() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo() != null ? request.getPathInfo().substring(1) : "";
		if(path.equals("")) {
			this.getServletContext().getRequestDispatcher("/vue/errorPage.jsp").forward(request, response);
		}
		else {
			modele.Album album = DAOFactory.getInstance().getAlbumDao().read(Integer.parseInt(path));
			if(album != null) {
				//Requête permettant de récupérer toutes les personnes présentes dans le graphe
				String requetePersonnes = "SELECT ?person ?firstName ?familyName "
							   + "WHERE"
							   + "{"
							   + "	?person rdf:type foaf:Person ;"
							   + "	foaf:firstName ?firstName ;"
							   + "	foaf:familyName ?familyName ."
							   + "}";
				//Execution de la requête sur le graph imss
				ResultSet  resultatPersonnes =  Sparql.getSparql().requete(requetePersonnes, "http://imss.upmf-grenoble.fr/abdelfam");
				ArrayList<modele.Personne> personnes = new ArrayList<modele.Personne>();
				//Pour chaque résultat, on stocke les personnes dans un tableau de Personne
				while (resultatPersonnes.hasNext()) {
					QuerySolution s = resultatPersonnes.nextSolution();
					personnes.add(new modele.Personne(s.getLiteral("?firstName").toString(), s.getLiteral("?familyName").toString(), s.getResource("?person").toString()));
				}
				
				String requeteAnimaux = "SELECT ?animal ?title "
						   + "WHERE"
						   + "{"
						   + "	?animal a :Animal ;"
						   + "	:title ?title ;"
						   + "}";
				
				//Execution de la requête sur le graph imss
				ResultSet  resultatAnimaux =  Sparql.getSparql().requete(requeteAnimaux, "http://imss.upmf-grenoble.fr/abdelfam");
				ArrayList<modele.Personne> animaux = new ArrayList<modele.Personne>();
				//Pour chaque résultat, on stocke les animaux dans un tableau de Personne
				while (resultatAnimaux.hasNext()) {
					QuerySolution s = resultatAnimaux.nextSolution();
					animaux.add(new modele.Personne(s.getLiteral("?title").toString(), "", s.getResource("?animal").toString()));
				}
				
				request.setAttribute("album", album);
				request.setAttribute("pathImages", modele.Photo.path);	
				request.setAttribute("personnes", personnes);
				request.setAttribute("animaux", animaux);
				
				this.getServletContext().getRequestDispatcher("/vue/gestionnairePhotos.jsp").forward(request, response);
			}
			else this.getServletContext().getRequestDispatcher("/vue/ressourceIntrouvable.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/Photo").forward(request, response);
	}
}
