package controleur;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import extra.Data;

@WebServlet(urlPatterns = { "/Album", "/Album/*" })
public class Album extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		System.out.println("BiduleDELETE !!");
		System.out.println(request.getParameter("idAlbum"));
		/*
		 * On récupére les paramètre en passant pas une fonction car dans un
		 * méthode avec la méthode DELETE les paramètres ne sont pas stockés
		 * dans une map
		 */
		// HashMap parameters = (HashMap)
		// parseParameters(request.getInputStream());
		// System.out.println("BiduleDELETE !!");
		// System.out.println(parameters.get("idAlbum"));
		//
		// Récupére l'album passé en paramètre
		modele.Album album = (modele.Album) DAOFactory.getInstance().getAlbumDao().read(Integer.getInteger(request.getParameter("idAlbum")));
		System.out.println("Album : " + album);
		// Si l'album existe
		if (album != null) {
			DAOFactory.getInstance().getAlbumDao().delete(album);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("messageErreur", "Je suis passé par la servlet Album !");
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// HashMap parameters = (HashMap)
		// parseParameters(request.getInputStream());
		System.out.println("BidulePut !!");
		System.out.println(request.getParameter("idAlbum"));
		// System.out.println(parameters.get("idAlbum"));
		//
		// modele.Album album = (modele.Album)
		// DAOFactory.getInstance().getAlbumDao().read((int)
		// parameters.get("idAlbum"));
		// System.out.println("Album : " + album);
		// if(album != null){
		// DAOFactory.getInstance().getAlbumDao().update(album);
		// }
	}

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
