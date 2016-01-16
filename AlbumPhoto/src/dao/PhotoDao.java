package dao;

import javax.persistence.EntityManager;

import org.apache.openjpa.util.OpenJPAId;

import metier.Sparql;
import modele.Photo;

public class PhotoDao extends JPADao<Photo, Integer> {

	public PhotoDao(EntityManager em) {
		super(em);
	}

	@Override
	public void delete(Photo obj) {
		Sparql.getSparql().supprimerPhotoGraph(((modele.Photo) obj).getId(), ((modele.Photo) obj).getAlbum().getId(), obj.getUrl());
		super.delete(obj);
	}
	
	@Override
	public OpenJPAId create(Photo obj) {
		OpenJPAId id = super.create(obj);
		obj.genererURL();
		update(obj);
		Sparql.getSparql().ajoutPhotoGraph(obj.getId(), obj.getTitre(), obj.getDate(), obj.getPhotographe(), obj.getAlbum().getId(), obj.getOu(), obj.getQui(), obj.getQuoi(), obj.getEvenement());
		return id;
	}
	

	@Override
	public Photo read(Integer id) {
		modele.Photo photo = super.read(id);
		photo = Sparql.getSparql().getSemAttributs(photo);
		return photo;
	}
}
