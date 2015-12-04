package controleur;

import java.io.IOException;

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
		//TODO 404
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
