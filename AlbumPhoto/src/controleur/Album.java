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
import metier.Sparql;
import modele.Personne;

@WebServlet(urlPatterns = {"/Album", "/Album/*", "/Album_delete/*"})
public class Album extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String MESSAGE_DELETE_200 = "L'album a été supprimé avec succès";
	public static final String MESSAGE_DELETE_404 = "Une erreur est survenue. L'album n'existe pas.";
	public static final String MESSAGE_DELETE_403 = "Une erreur est survenue. Vous n'êtes pas autorisé à supprimer cet album.";
	public static final String MESSAGE_POST_UTILISATEUR_NULL_404 = "Cet utilisateur n'existe pas.";
	public static final String MESSAGE_POST_200 = "L'album a été créé";
	public static final String MESSAGE_POST_UTILISATEUR_DIFF_404 = "Vous n'avez pas les droits pour modifier cet utilisateur.";
	public static final String MESSAGE_POST_ATTRIBUTE_MISSING = "le champs titre n'a pas été rempli";
	public static final String MESSAGE_DELETE_ATTRIBUTE_MISSING = "L'id n'est pas renseigné.";
	
	public Album() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo() != null ? request.getPathInfo().substring(1) : "";
		if(path.equals("")) {
			EntityManager entityManager = DAOFactory.getInstance().getEntityManager();
			Query query = entityManager.createQuery("SELECT x FROM Album x");
			List<modele.Album> results = (List<modele.Album>) query.getResultList();
			response.getWriter().append("<table border=\"1\"><tr><th>ID</th><th>Titre</th><th>Créateur</th><th>Date création</th><th>Nombre de photos</th></tr>");
			for(modele.Album album : results) {
				response.getWriter().append("<tr><td>"+album.getId()+"</td><td>"+album.getTitre()+"</td><td>"+album.getCreateur().getPrenom()+" "+album.getCreateur().getNom()+"</td><td>"+album.getDateCreation()+"</td><td>"+album.getPhotos().size()+"</td></tr>");
			}
			response.getWriter().append("</table>");
		}
		else {
			modele.Album album = DAOFactory.getInstance().getAlbumDao().read(Integer.parseInt(path));
			if(album != null) {
				request.setAttribute("album", album);
				this.getServletContext().getRequestDispatcher("/vue/album.jsp").forward(request, response);
			}
			else this.getServletContext().getRequestDispatcher("/vue/ressourceIntrouvable.jsp").forward(request, response);
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idAlbum = request.getParameter("idAlbum");
		if(idAlbum != ""){
			// Récupére l'album passé en paramètre
			modele.Album album = (modele.Album) DAOFactory.getInstance().getAlbumDao().read(Integer.parseInt(idAlbum));
			
			// Si l'album existe
			if (album != null) {
				Personne personne = ((modele.Utilisateur)request.getSession().getAttribute(Connexion.ATT_USER_SESSION)).getPersonne();
				
				if(album.getCreateur().getId() == personne.getId()){
					personne.getAlbums().remove(album);
					supprimerAlbumGraph(album.getId());
					DAOFactory.getInstance().getAlbumDao().delete(album);
					
					request.setAttribute("code", "200");
					request.setAttribute("message", MESSAGE_DELETE_200);
				} else {
					request.setAttribute("code", "400");
					request.setAttribute("message", MESSAGE_DELETE_403);
				}
				
			} else {
				request.setAttribute("code", "400");
				request.setAttribute("message", MESSAGE_DELETE_404);
			}
		} else {
			request.setAttribute("code", "400");
			request.setAttribute("message", MESSAGE_DELETE_ATTRIBUTE_MISSING);
		}
		this.getServletContext().getRequestDispatcher("/vue/gestionnaireAlbums.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("method") != null) {
			if(request.getParameter("method").equals("DELETE")) doDelete(request, response);
		}
		else {
		
			// Récupére les paramètre de l'album qui doit être créé
			String titre = request.getParameter("titre");
			
			if(titre != ""){
				Date date = new Date();
				Personne personne = ((modele.Utilisateur)request.getSession().getAttribute(Connexion.ATT_USER_SESSION)).getPersonne();
				modele.Album album = new modele.Album(titre, date, personne);
				DAOFactory.getInstance().getAlbumDao().create(album);
				
				ajoutAlbumGraph(album.getId(), album.getTitre());
				
				request.setAttribute("code", "200");
				request.setAttribute("message", MESSAGE_POST_200);
			} else {
				request.setAttribute("code", "400");
				request.setAttribute("message", MESSAGE_POST_ATTRIBUTE_MISSING);
			}
			this.getServletContext().getRequestDispatcher("/vue/gestionnaireAlbums.jsp").forward(request, response);
		}
	}

	private void ajoutAlbumGraph(int id, String titre) {
		String insereAlbum = 
		  "INSERT DATA {GRAPH IMSS: {"
        + ":album" + id + " rdf:type :Album ;"
        + "        dc:title \"" + titre + "\" ."
        + "} }";
		Sparql.getSparql().requeteCRUD(insereAlbum);
	}
	
	private void supprimerAlbumGraph(int id){
		String supprAlbum = 
				  "DELETE { GRAPH " + Sparql.GRAPH_DEFAULT + " {?album ?p ?v}} "
			    + " USING " + Sparql.GRAPH_DEFAULT + " WHERE{"
				+ "		?album ?p ?v ."
				+ "		FILTER (?album = :album" + id + ")"
		        + "	}";
				Sparql.getSparql().requeteCRUD(supprAlbum);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("BidulePut !!");
		System.out.println(request.getParameter("idAlbum"));
	}
	
	/**********************************************************************************/
	/**********************************************************************************/
	/**********************************************************************************/
	//TODO Ne pas oublier de faire figurer les changements au niveau du serveur tomcat (accepter le parse du body pour les méthodesput et delete
	/**********************************************************************************/
	/**********************************************************************************/
	/**********************************************************************************/
	
//	private Map<String, String> parseParameters(ServletInputStream is) throws IOException {
//		BufferedReader br = new BufferedReader(new InputStreamReader(is));
//		String data = br.readLine();
//
//		HashMap<String, String> parameters = new HashMap<String, String>();
//		String[] parametersArray = data.split("&");
//		for (String parameter : parametersArray) {
//			String[] parameterArray = parameter.split("=");
//			parameters.put(parameterArray[0], parameterArray[1]);
//		}
//
//		return parameters;
//	}

}
