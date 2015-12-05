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
import modele.Personne;

@WebServlet("/Utilisateur")
public class Utilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Utilisateur() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManager entityManager = DAOFactory.getInstance().getEntityManager();
		Query query = entityManager.createQuery("SELECT x FROM Utilisateur x");
		List<modele.Utilisateur> results = (List<modele.Utilisateur>) query.getResultList();
		response.getWriter().append("<table border=\"1\"><tr><th>Login</th><th>Password</th><th>Personne</th></tr>");
		for(modele.Utilisateur utilisateur : results) {
			response.getWriter().append("<tr><td>"+utilisateur.getLogin()+"</td><td>"+utilisateur.getPassword()+"</td><td>"+utilisateur.getPersonne().getPrenom()+" "+utilisateur.getPersonne().getNom()+"</td></tr>");
		}
		response.getWriter().append("</table>");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		modele.Utilisateur utilisateur = DAOFactory.getInstance().getUtilisateurDao().read(request.getParameter("login"));
		if(utilisateur == null){
			Personne personne = new Personne(request.getParameter("prenom"), request.getParameter("nom"), null);
			DAOFactory.getInstance().getPersonneDao().create(personne);
			utilisateur = new modele.Utilisateur(personne, request.getParameter("login"), request.getParameter("pass"));
			DAOFactory.getInstance().getUtilisateurDao().create(utilisateur);
			this.getServletContext().getRequestDispatcher("/Connexion").forward(request, response);
		}
		else {
			request.setAttribute("messageErreur", "ce login est déjà utilisé par un autre utilisateur");
			this.getServletContext().getRequestDispatcher("/vue/inscription.jsp").forward(request, response);
		}
	}
	
	
}
