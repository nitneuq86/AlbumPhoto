package controleur;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import modele.Personne;

@WebServlet(urlPatterns = {"/Photo", "/Photo/*"})
public class Photo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public Photo() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo() != null ? request.getPathInfo().substring(1) : "";
		if(path.equals("")) {
			EntityManager entityManager = DAOFactory.getInstance().getEntityManager();
			Query query = entityManager.createQuery("SELECT x FROM Photo x");
			List<modele.Photo> results = (List<modele.Photo>) query.getResultList();
			response.getWriter().append("<table border=\"1\"><tr><th>ID</th><th>URI</th><th>Album</th><th>Auteur</th></tr>");
			for(modele.Photo photo : results) {
				response.getWriter().append("<tr><td>"+photo.getId()+"</td><td>"+photo.getUri()+"</td><td>"+photo.getAlbum().getTitre()+" (ID = "+photo.getAlbum().getId()+")</td><td>"+photo.getAlbum().getCreateur().getPrenom()+" "+photo.getAlbum().getCreateur().getNom()+"</td></tr>");
			}
			response.getWriter().append("</table>");
		}
		else {
			/*modele.Photo photo = DAOFactory.getInstance().getPhotoDao().read(Integer.parseInt(path));
			if(photo != null) {
				request.setAttribute("photo", photo);
				this.getServletContext().getRequestDispatcher("/vue/photo.jsp").forward(request, response);
			}
			else this.getServletContext().getRequestDispatcher("/vue/ressourceIntrouvable.jsp").forward(request, response);*/
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("method") != null) {
			if(request.getParameter("method").equals("DELETE")) doDelete(request, response);
		}
		else {
			// Récupére les paramètre de la photo qui doit être créée
			String URI = request.getParameter("uri");
			modele.Album album = (modele.Album) DAOFactory.getInstance().getAlbumDao().read(Integer.parseInt(request.getParameter("idAlbum")));
			
			if(URI != "" && album != null) {
				Personne createur = ((modele.Utilisateur)request.getSession().getAttribute(Connexion.ATT_USER_SESSION)).getPersonne();
				
				modele.Photo photo = new modele.Photo(album, createur, URI);
				
				if(createur != null) DAOFactory.getInstance().getPhotoDao().create(photo);
				else {
					request.setAttribute("code", "400");
					request.setAttribute("message", "contrôle du créateur impossible");
				}
				
				request.setAttribute("code", "200");
				request.setAttribute("message", "photo ajoutée");
			}
			else {
				request.setAttribute("code", "400");
				request.setAttribute("message", "un ou plusieus champs ne sont pas remplis");
			}
			request.setAttribute("album", album);
			this.getServletContext().getRequestDispatcher("/vue/gestionnairePhotos.jsp").forward(request, response);
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idPhoto = request.getParameter("idPhoto");
		modele.Photo photo = (modele.Photo) DAOFactory.getInstance().getPhotoDao().read(Integer.parseInt(idPhoto));
		
		// Si la photo existe
		if (photo != null) {
			Personne personne = ((modele.Utilisateur)request.getSession().getAttribute(Connexion.ATT_USER_SESSION)).getPersonne();
			
			
			if(photo.getAlbum().getCreateur().getId() == personne.getId()){
				photo.getAlbum().getPhotos().remove(photo);
				DAOFactory.getInstance().getPhotoDao().delete(photo);
				
				request.setAttribute("code", "200");
				request.setAttribute("message", "photo supprimée");
			} else {
				request.setAttribute("code", "400");
				request.setAttribute("message", "vous n'êtes pas autorisé à supprimer cette photo");
			}
			
		}
		else {
			request.setAttribute("code", "400");
			request.setAttribute("message", "la photo n'existe pas");
		}
		request.setAttribute("album", photo.getAlbum());
		this.getServletContext().getRequestDispatcher("/vue/gestionnairePhotos.jsp").forward(request, response);
	}
}
