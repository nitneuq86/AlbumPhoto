package controleur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOFactory;
import filtre.FiltrePermissions;
import modele.Utilisateur;

@WebServlet("/Connexion")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static String ATT_USER_SESSION = "sessionUtilisateur";

	public Connexion() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/vue/connexion.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utilisateur utilisateur = DAOFactory.getInstance().getUtilisateurDao().read(getValeurChamp(request, "login"));
		HttpSession session = request.getSession();
		
		if(getValeurChamp(request, "login") == null || getValeurChamp(request, "pass") == null) {
			request.setAttribute("messageErreur", "un ou plusieus champs ne sont pas remplis");
			request.setAttribute("utilisateur", request.getParameter("login"));
			this.getServletContext().getRequestDispatcher("/vue/connexion.jsp").forward(request, response);
		}
		else {
			// Utilisateur trouvé
			if(utilisateur != null) {
				if(getValeurChamp(request, "pass").equals(utilisateur.getPassword())) {
					String requestedUrl = (String) session.getAttribute(FiltrePermissions.ATT_CONNECTION_REQUESTED_URL);
					session.setAttribute(ATT_USER_SESSION, utilisateur);
					session.removeAttribute(FiltrePermissions.ATT_CONNECTION_REQUESTED_URL);
					if (requestedUrl != null)
						response.sendRedirect(requestedUrl);
					else
						response.sendRedirect(request.getContextPath()+"/Personne/"+utilisateur.getPersonne().getId());
				}
				// Mauvais mot de passe
				else {
					request.setAttribute("messageErreur", "mot de passe eronné");
					request.setAttribute("utilisateur", request.getParameter("login"));
					this.getServletContext().getRequestDispatcher("/vue/connexion.jsp").forward(request, response);
				}
			}
			// Utilisateur introuvable
			else {
				request.setAttribute("messageErreur", "le login entré ne correspond à aucun utilisateur enregistré");
				request.setAttribute("utilisateur", request.getParameter("login"));
				this.getServletContext().getRequestDispatcher("/vue/connexion.jsp").forward(request, response);
			}
		}
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
