package controleur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import metier.FormConnexion;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/Connexion")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String ATT_USER         = "utilisateur";
    public static final String ATT_FORM         = "form";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String VUE              = "/connexion.jsp";
       
    public Connexion() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FormConnexion formConnexion = new FormConnexion();
		modele.Utilisateur utilisateur = formConnexion.connexionUtilisateur(request);
		HttpSession session = request.getSession();
		 if (formConnexion.getErreurs().isEmpty()) {
	            session.setAttribute(ATT_SESSION_USER, utilisateur);
	        } else {
	            session.setAttribute(ATT_SESSION_USER, null);
	        }
		request.setAttribute(ATT_FORM, formConnexion);
        request.setAttribute(ATT_USER, utilisateur);
        this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
