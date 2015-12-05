package controleur;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import extra.Data;
import modele.Personne;

@WebServlet(urlPatterns = { "/Album", "/Album/*" })
public class Album extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String MESSAGE_DELETE_200 = "L'album a été supprimé avec succès";
	public static final String MESSAGE_DELETE_404 = "Une erreur est survenue. L'album n'existe pas.";
	public static final String MESSAGE_POST_UTILISATEUR_NULL_404 = "Cet utilisateur n'existe pas.";
	public static final String MESSAGE_POST_200 = "L'album a été créé";
	public static final String MESSAGE_POST_UTILISATEUR_DIFF_404 = "Vous n'avez pas les droits pour modifier cet utilisateur.";
	public static final String MESSAGE_POST_ATTRIBUTE_MISSING = "Le titre n'a pas été rempli.";
	public static final String MESSAGE_DELETE_ATTRIBUTE_MISSING = "L'id n'est pas renseigné.";
	
	public Album() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Personne personne = new Personne("Myriam", "Abdel-Fattah");
		// int idPersonne =
		// DAOFactory.getInstance().getPersonneDao().create(personne).getId();
		// DAOFactory.getInstance().getAlbumDao().create(new modele.Album("Mon
		// ALBUM", new Date(), personne));

		// request.setAttribute("album", new modele.Album("sdfdf", new Date(),
		// personne));

		// doPost(request, response);
		String path = request.getPathInfo();
		if (path == null || path.compareTo("/") == 0) {
			request.setAttribute("typeRecherche", "album");
			this.getServletContext().getRequestDispatcher("/vue/rechercher.jsp").forward(request, response);
		} else {
			try {

				int idRessource = Integer.parseInt(path.substring(1));

				if (idRessource == 123) {
					request.setAttribute("album", Data.getAlbumTest());
					this.getServletContext().getRequestDispatcher("/vue/album.jsp").forward(request, response);
				} else
					this.getServletContext().getRequestDispatcher("/vue/ressourceIntrouvable.jsp").forward(request, response);
			} catch (NumberFormatException ex) {
				this.getServletContext().getRequestDispatcher("/vue/ressourceIntrouvable.jsp").forward(request, response);
			}
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idAlbum = request.getParameter("idAlbum");
		if(idAlbum != ""){
			// Récupére l'album passé en paramètre
			modele.Album album = (modele.Album) DAOFactory.getInstance().getAlbumDao().read(Integer.getInteger(idAlbum));
			
			// Si l'album existe
			if (album != null) {
				DAOFactory.getInstance().getAlbumDao().delete(album);
				request.setAttribute("code", "200");
				request.setAttribute("message", MESSAGE_DELETE_200);
			} else {
				request.setAttribute("code", "400");
				request.setAttribute("message", MESSAGE_DELETE_404);
			}
		} else {
			request.setAttribute("code", "400");
			request.setAttribute("message", MESSAGE_DELETE_ATTRIBUTE_MISSING);
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Récupére les paramètre de l'album qui doit être créé
		String titre = request.getParameter("titre");
		
		if(titre != ""){
			Date date = new Date();
			Personne personne = ((modele.Utilisateur)request.getSession().getAttribute(Connexion.ATT_USER_SESSION)).getPersonne();
			modele.Album album = new modele.Album(titre, date, personne);
			DAOFactory.getInstance().getAlbumDao().create(album);
			
			request.setAttribute("code", "200");
			request.setAttribute("message", MESSAGE_POST_200);
		} else {
			request.setAttribute("code", "400");
			request.setAttribute("message", MESSAGE_POST_ATTRIBUTE_MISSING);
		}
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
