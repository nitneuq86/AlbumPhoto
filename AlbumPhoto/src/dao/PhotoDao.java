package dao;

import javax.persistence.EntityManager;

import org.apache.openjpa.util.IntId;

import modele.Photo;

public class PhotoDao extends JPADao<Photo, IntId> {

	public PhotoDao(EntityManager em) {
		super(em);
	}

}
