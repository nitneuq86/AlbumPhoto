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
		Sparql.getSparql().ajoutAlbumGraph(obj.getId(), obj.getTitre());
		return id;
	}
	
	@Override
	public void delete(Album obj) {
		for (int i = 0; i < obj.getPhotos().size(); i++) {
			modele.Photo photo = obj.getPhotos().get(i);
			Sparql.getSparql().supprimerPhotoGraph(photo.getId(), obj.getId(), photo.getUrl());;
		}
		Sparql.getSparql().supprimerAlbumGraph(obj.getId());
		super.delete(obj);
	}
}
