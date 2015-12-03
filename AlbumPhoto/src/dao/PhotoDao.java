package dao;

import javax.persistence.EntityManager;

import modele.Photo;

public class PhotoDao extends JPADao<Photo, Integer> {

	public PhotoDao(EntityManager em) {
		super(em);
	}

}
