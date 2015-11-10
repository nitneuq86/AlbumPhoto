package controlleur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns={"/Utilisateur","/Utilisateur/*"})
public class Utilisateur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public Utilisateur() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if(path==null || path.compareTo("/")==0) {
			request.setAttribute("typeRecherche", "utilisateur");
			this.getServletContext().getRequestDispatcher("/rechercher.jsp").forward(request, response);
		}
		else {
			this.getServletContext().getRequestDispatcher("/utilisateur.jsp").forward(request, response);
		}
	}

}
