package controleur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.openjpa.util.IntId;
import dao.DAOFactory;
import modele.Personne;
import modele.Utilisateur;

/**
 * Servlet implementation class Inscription
 */
@WebServlet("/Inscription")
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public Inscription() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/vue/inscription.jsp").forward(request, response);
	}
<<<<<<< HEAD
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(
			getValeurChamp(request, "login") != null &&
			getValeurChamp(request, "pass") != null &&
			getValeurChamp(request, "nom") != null &&
			getValeurChamp(request, "prenom") != null)
		{
			this.getServletContext().getRequestDispatcher("/Utilisateur").forward(request, response);
		}
		else {
			request.setAttribute("messageErreur", "un ou plusieus champs ne sont pas remplis");
			this.getServletContext().getRequestDispatcher("/vue/inscription.jsp").forward(request, response);
		}
=======

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		System.out.println(getValeurChamp(request, "login"));
		Utilisateur utilisateur = DAOFactory.getInstance().getUtilisateurDao().read(getValeurChamp(request, "login"));
		
		if(utilisateur == null){
			Personne personne = new Personne(getValeurChamp(request, "prenom"), getValeurChamp(request, "nom"));
			DAOFactory.getInstance().getPersonneDao().create(personne);
			utilisateur = new Utilisateur(personne, getValeurChamp(request, "login"), getValeurChamp(request, "pass"));
			DAOFactory.getInstance().getUtilisateurDao().create(utilisateur);
			System.out.println(personne.getUtilisateur());
			request.setAttribute("message", "Vous êtes inscrit.");
		} else {
			request.setAttribute("message", "Ce login est déjà pris !");
		}
		this.getServletContext().getRequestDispatcher("/vue/inscription.jsp").forward(request, response);
>>>>>>> 353310f0b903e65fdb0c43875de1f2546b6c3e74
	}
	
	private static String getValeurChamp(HttpServletRequest request, String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur.trim();
		}
	}

}
