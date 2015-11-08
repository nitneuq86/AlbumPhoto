import java.io.IOException;
import java.nio.file.Path;

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
		// Rechercher un utilisateur
		if(path.compareTo("/")==0 || path==null) {
			this.getServletContext().getRequestDispatcher("/rechercher.jsp").forward(request, response);
		}
	}

}
