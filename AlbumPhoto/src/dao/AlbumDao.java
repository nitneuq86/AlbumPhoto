package dao;

import javax.persistence.EntityManager;

import org.apache.openjpa.util.OpenJPAId;

import metier.Sparql;
import modele.Album;

public class AlbumDao extends JPADao<Album, Integer> {

	public AlbumDao(EntityManager em) {
		super(em);
	}
	
	@Override
	public OpenJPAId create(Album obj) {
		OpenJPAId id = super.create(obj);
		ajoutAlbumGraph(obj.getId(), obj.getTitre());
		return id;
	}
	
	@Override
	public void delete(Album obj) {
		for (int i = 0; i < obj.getPhotos().size(); i++) {
			modele.Photo photo = obj.getPhotos().get(i);
			DAOFactory.getInstance().getPhotoDao().supprimerPhotoGraph(photo.getId(), obj.getId(), photo.getUrl());;
		}
		supprimerAlbumGraph(obj.getId());
		super.delete(obj);
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
}
