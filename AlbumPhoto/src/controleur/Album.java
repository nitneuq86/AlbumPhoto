package controleur;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import extra.Data;

@WebServlet(urlPatterns={"/Album","/Album/*"})
public class Album extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public Album() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		if(path==null || path.compareTo("/")==0) {
			request.setAttribute("typeRecherche", "album");
			this.getServletContext().getRequestDispatcher("/vue/rechercher.jsp").forward(request, response);
		}
		else {
			try {
				int idRessource = Integer.parseInt(path.substring(1));
				
				if(idRessource == 123) {
					request.setAttribute("album", Data.getAlbumTest());
					this.getServletContext().getRequestDispatcher("/vue/album.jsp").forward(request, response);
				}
				else this.getServletContext().getRequestDispatcher("/vue/ressourceIntrouvable.jsp").forward(request, response);
			}
			catch(NumberFormatException ex) {
				this.getServletContext().getRequestDispatcher("/vue/ressourceIntrouvable.jsp").forward(request, response);
			}
		}
	}

}