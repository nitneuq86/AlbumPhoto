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
				ArrayList<modele.Personne> personnes = Sparql.getSparql().getPersonnes();
				ArrayList<modele.Personne> animaux = Sparql.getSparql().getAnimaux();
				ArrayList<modele.Evenement> evenements = Sparql.getSparql().getEvenement();
				
				request.setAttribute("album", album);
				request.setAttribute("pathImages", modele.Photo.path);	
				request.setAttribute("personnes", personnes);
				request.setAttribute("animaux", animaux);
				request.setAttribute("evenements", evenements);
				
				this.getServletContext().getRequestDispatcher("/vue/gestionnairePhotos.jsp").forward(request, response);
			}
			else this.getServletContext().getRequestDispatcher("/vue/ressourceIntrouvable.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			request.getParts();
		} catch(Exception ex){
			
		}
		this.getServletContext().getRequestDispatcher("/Photo").forward(request, response);
	}
}
