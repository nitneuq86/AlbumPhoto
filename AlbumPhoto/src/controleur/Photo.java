package controleur;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.DAOFactory;
import metier.Sparql;
import modele.Personne;

@WebServlet(urlPatterns = {"/Photo", "/Photo/*"})
@MultipartConfig
public class Photo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(GestionnairePhotos.class.getCanonicalName());
	
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
				response.getWriter().append("<tr><td>"+photo.getId()+"</td><td>"+photo.getUrl()+"</td><td>"+photo.getAlbum().getTitre()+" (ID = "+photo.getAlbum().getId()+")</td><td>"+photo.getAlbum().getCreateur().getPrenom()+" "+photo.getAlbum().getCreateur().getNom()+"</td></tr>");
			}
			response.getWriter().append("</table>");
		}
		else {
			modele.Photo photo = DAOFactory.getInstance().getPhotoDao().read(Integer.parseInt(path));
			photo = Sparql.getSparql().getSemAttributs(photo);
			if(photo != null) {
				request.setAttribute("pathImages", modele.Photo.path);
				request.setAttribute("photo", photo);
				this.getServletContext().getRequestDispatcher("/vue/photo.jsp").forward(request, response);
			}
			else this.getServletContext().getRequestDispatcher("/vue/ressourceIntrouvable.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("method") != null) {
			if(request.getParameter("method").equals("DELETE")) doDelete(request, response);
		}
		else {
			modele.Album album = (modele.Album) DAOFactory.getInstance().getAlbumDao().read(Integer.parseInt(request.getParameter("idAlbum")));

			if(album != null) {
				try {
					request.getParts();
					
					Personne createur = ((modele.Utilisateur)request.getSession().getAttribute(Connexion.ATT_USER_SESSION)).getPersonne();
					
					if(createur != null){
						String titre = request.getParameter("titre");
						String date = request.getParameter("date");
						String createurParametre = "<" + request.getParameter("createur") + ">";
						String ou = "<" + request.getParameter("ou-hidden") + ">";
						String[] qui = request.getParameterValues("qui");
						String[] quoi = request.getParameterValues("quoi");
						String evenement = request.getParameter("evenement");
						
						modele.Photo photo = new modele.Photo(album, createur, titre, date, createurParametre, ou, qui, quoi, evenement);
						DAOFactory.getInstance().getPhotoDao().create(photo);
						processRequest(request, response, photo.getUrl());
						
					} else {
						request.setAttribute("code", "400");
						request.setAttribute("message", "contrôle du créateur impossible");
					}
				} catch(Exception ex) {
					request.setAttribute("code", "400");
					request.setAttribute("message", "Un problème est survenue pendant l'enregistrement de la photo");
				}
				request.setAttribute("pathImages", modele.Photo.path);

				request.setAttribute("code", "200");
				request.setAttribute("message", "photo ajoutée");
			}
			else {
				request.setAttribute("code", "400");
				request.setAttribute("message", "un ou plusieus champs ne sont pas remplis");
			}
			response.sendRedirect(request.getContextPath() + "/GestionnairePhotos/" + album.getId());
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
		response.sendRedirect(request.getContextPath() + "/GestionnairePhotos/" + photo.getAlbum().getId());
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response, String filename)
	        throws ServletException, IOException {
	    response.setContentType("text/html;charset=UTF-8");

	    // Create path components to save the file
	    
	    final Part filePart = request.getPart("url");
	    OutputStream out = null;
	    InputStream filecontent = null;

	    try {
	    	if(new File(getServletContext().getRealPath("") + modele.Photo.path).exists()){
	    		new File(getServletContext().getRealPath("") + modele.Photo.path).mkdirs();
	    	}
	    	File file = new File(getServletContext().getRealPath("") + modele.Photo.path + filename);
	        out = new FileOutputStream(file);
	        filecontent = filePart.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[1024];

	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	        
	        
	        LOGGER.log(Level.INFO, "File {0} being uploaded to {1}", 
	                new Object[]{filename, modele.Photo.path});
	    } catch (FileNotFoundException fne) {

	        LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}", 
	                new Object[]{fne.getMessage()});
	    } finally {
	    	if (filecontent != null) {
	        	LOGGER.log(Level.INFO, "FILE CLOSED.");
	            filecontent.close();
	            filecontent = null;
	        }
	        if (out != null) {
	        	out.flush();
	        	out.close();
	        	out = null;
	        	LOGGER.log(Level.INFO, "BUFFER CLOSED.");
	        }
	        
	    }
	}
}
