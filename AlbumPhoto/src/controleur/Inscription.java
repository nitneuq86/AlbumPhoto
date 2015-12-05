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
