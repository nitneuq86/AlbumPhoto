package controleur;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;

@WebServlet("/GestionnairePhotos/*")
public class GestionnairePhotos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public GestionnairePhotos() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo() != null ? request.getPathInfo().substring(1) : "";
		if(path.equals("")) {
			this.getServletContext().getRequestDispatcher("/vue/errorPage.jsp").forward(request, response);
		}
		else {
			modele.Album album = DAOFactory.getInstance().getAlbumDao().read(Integer.parseInt(path));
			if(album != null) {
				request.setAttribute("album", album);
				this.getServletContext().getRequestDispatcher("/vue/gestionnairePhotos.jsp").forward(request, response);
			}
			else this.getServletContext().getRequestDispatcher("/vue/ressourceIntrouvable.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/Photo").forward(request, response);
	}

}
