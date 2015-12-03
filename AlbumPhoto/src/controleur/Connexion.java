package controleur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import extra.Data;
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
		Utilisateur utilisateur = Data.verificationUtilisateur(getValeurChamp(request, "login"), getValeurChamp(request, "pass"));
		HttpSession session = request.getSession();
		if (utilisateur != null) {
			String requestedUrl = (String) session.getAttribute(FiltrePermissions.ATT_CONNECTION_REQUESTED_URL);
			session.setAttribute(ATT_USER_SESSION, utilisateur);
			session.removeAttribute(FiltrePermissions.ATT_CONNECTION_REQUESTED_URL);
			if (requestedUrl != null)
				response.sendRedirect(requestedUrl);
			else
				response.sendRedirect(request.getContextPath());
		} else {
			request.setAttribute("utilisateur", request.getParameter("login"));
			this.getServletContext().getRequestDispatcher("/vue/connexion.jsp").forward(request, response);
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
