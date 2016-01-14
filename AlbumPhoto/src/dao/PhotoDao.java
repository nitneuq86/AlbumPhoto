package dao;

import javax.persistence.EntityManager;

import metier.Sparql;
import modele.Photo;

public class PhotoDao extends JPADao<Photo, Integer> {

	public PhotoDao(EntityManager em) {
		super(em);
	}

	@Override
	public void delete(Photo obj) {
		supprimerPhotoGraph(((modele.Photo) obj).getId(), ((modele.Photo) obj).getAlbum().getId());
		super.delete(obj);
	}
	
	private void supprimerPhotoGraph(int id, int idAlbum){
		String supprPhoto = 
				  "DELETE { GRAPH " + Sparql.GRAPH_DEFAULT + " {?photo ?p ?v}} "
			    + " USING " + Sparql.GRAPH_DEFAULT + " WHERE{"
				+ "		?photo ?p ?v ."
				+ "		FILTER (?photo = :photo" + id + ")"
		        + "	}";
				Sparql.getSparql().requeteCRUD(supprPhoto);
				
		String supprPhotoAlbum = 
				  "DELETE { GRAPH " + Sparql.GRAPH_DEFAULT + " {?album ?p ?v}} "
			    + " USING " + Sparql.GRAPH_DEFAULT + " WHERE{"
				+ "		?album ?p ?v ."
				+ "		FILTER (?album = :album" + idAlbum + " && ?p = :hasPhoto && ?v = :photo" + id + " )"
		        + "	}";
				Sparql.getSparql().requeteCRUD(supprPhotoAlbum);
	}
}
