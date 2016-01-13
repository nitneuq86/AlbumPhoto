package controleur;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetRewindable;

import dao.DAOFactory;
import metier.Sparql;

@WebServlet("/GestionnairePhotos/*")
@MultipartConfig
public class GestionnairePhotos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(GestionnairePhotos.class.getCanonicalName());
	
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
				//Requête permettant de récupérer toutes les personnes présentes dans le graphe
				String requete = "SELECT ?person ?firstName ?familyName "
							   + "WHERE"
							   + "{"
							   + "	?person rdf:type foaf:Person ;"
							   + "	foaf:firstName ?firstName ;"
							   + "	foaf:familyName ?familyName ."
							   + "}";
				//Execution de la requête sur le graph imss
				ResultSet  resultat =  Sparql.getSparql().requete(requete);
				ArrayList<modele.Personne> personnes = new ArrayList<modele.Personne>();
				//Pour chaque résultat, on stocke les personnes dans un tableau de string
				while (resultat.hasNext()) {
					QuerySolution s = resultat.nextSolution();
					personnes.add(new modele.Personne(s.getLiteral("?firstName").toString(), s.getLiteral("?familyName").toString(), s.getResource("?person").toString()));
				}
				
				request.setAttribute("album", album);
				request.setAttribute("pathImages", modele.Photo.path);	
				request.setAttribute("personnes", personnes);
				
				this.getServletContext().getRequestDispatcher("/vue/gestionnairePhotos.jsp").forward(request, response);
			}
			else this.getServletContext().getRequestDispatcher("/vue/ressourceIntrouvable.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.getParts();
			processRequest(request, response);
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		request.setAttribute("pathImages", modele.Photo.path);
		this.getServletContext().getRequestDispatcher("/Photo").forward(request, response);
	}
	
	protected void processRequest(HttpServletRequest request,
	        HttpServletResponse response)
	        throws ServletException, IOException {
	    response.setContentType("text/html;charset=UTF-8");

	    // Create path components to save the file
	    
	    final Part filePart = request.getPart("url");
	    final String fileName = getFileName(filePart);
	    request.setAttribute("urlPhoto", fileName);
	    OutputStream out = null;
	    InputStream filecontent = null;

	    try {
	    	System.out.println();
	    	new File(getServletContext().getRealPath("") + modele.Photo.path).mkdirs();
	    	File file = new File(getServletContext().getRealPath("") + modele.Photo.path + fileName);
	        out = new FileOutputStream(file);
	        filecontent = filePart.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[1024];

	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	        LOGGER.log(Level.INFO, "File{0}being uploaded to {1}", 
	                new Object[]{fileName, modele.Photo.path});
	    } catch (FileNotFoundException fne) {

	        LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}", 
	                new Object[]{fne.getMessage()});
	    } finally {
	        if (out != null) {
	            out.close();
	        }
	        if (filecontent != null) {
	            filecontent.close();
	        }
	    }
	}
	
	private String getFileName(final Part part) {
	    final String partHeader = part.getHeader("content-disposition");
	    LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}

}
