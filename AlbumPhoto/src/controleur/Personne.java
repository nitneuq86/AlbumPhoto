package controleur;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;

@WebServlet(urlPatterns = {"/Personne", "/Personne/*"})
public class Personne extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public Personne() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo() != null ? request.getPathInfo().substring(1) : "";
		if(path.equals("")) {
			EntityManager entityManager = DAOFactory.getInstance().getEntityManager();
			Query query = entityManager.createQuery("SELECT x FROM Personne x");
			List<modele.Personne> results = (List<modele.Personne>) query.getResultList();
			response.getWriter().append("<table border=\"1\"><tr><th>ID</th><th>Nom</th><th>Prénom</th><th>Login utilisateur</th><th>Nombre d'albums</th></tr>");
			for(modele.Personne personne : results) {
				String loginUtilisateur = personne.getUtilisateur() != null ? personne.getUtilisateur().getLogin() : "---";
				response.getWriter().append("<tr><td>"+personne.getId()+"</td><td>"+personne.getNom()+"</td><td>"+personne.getPrenom()+"</td><td>"+loginUtilisateur+"</td><td>"+personne.getAlbums().size()+"</td></tr>");
			}
			response.getWriter().append("</table>");
		}
		else {
			modele.Personne personne = DAOFactory.getInstance().getPersonneDao().read(Integer.parseInt(path));
			if(personne != null) {
				request.setAttribute("personne", personne);
				this.getServletContext().getRequestDispatcher("/vue/personne.jsp").forward(request, response);
			}
			else this.getServletContext().getRequestDispatcher("/vue/ressourceIntrouvable.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
