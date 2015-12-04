package controleur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOFactory;
import extra.Data;
import filtre.FiltrePermissions;
import modele.Personne;
import modele.Utilisateur;

/**
 * Servlet implementation class Inscription
 */
@WebServlet("/Inscription")
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Inscription() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/vue/inscription.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		System.out.println(getValeurChamp(request, "login"));
		Utilisateur utilisateur = DAOFactory.getInstance().getUtilisateurDao().read(getValeurChamp(request, "login"));
		if(utilisateur == null){
			Personne personne = new Personne(getValeurChamp(request, "prenom"), getValeurChamp(request, "nom"), null);
			DAOFactory.getInstance().getPersonneDao().create(personne);
			utilisateur = new Utilisateur(personne, getValeurChamp(request, "login"), getValeurChamp(request, "pass"));
			DAOFactory.getInstance().getUtilisateurDao().create(utilisateur);
			System.out.println(personne.getUtilisateur());
			request.setAttribute("message", "Vous êtes inscrit.");
		} else {
			request.setAttribute("message", "FAILED BOUM DANS TA FACE !");
		}
		this.getServletContext().getRequestDispatcher("/vue/inscription.jsp").forward(request, response);
		
//		if (utilisateur != null) {
//			String requestedUrl = (String) session.getAttribute(FiltrePermissions.ATT_CONNECTION_REQUESTED_URL);
//			session.setAttribute(ATT_USER_SESSION, utilisateur);
//			session.removeAttribute(FiltrePermissions.ATT_CONNECTION_REQUESTED_URL);
//			if (requestedUrl != null)
//				response.sendRedirect(requestedUrl);
//			else
//				response.sendRedirect(request.getContextPath());
//		} else {
//			request.setAttribute("utilisateur", request.getParameter("login"));
//			this.getServletContext().getRequestDispatcher("/vue/connexion.jsp").forward(request, response);
//		}
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
