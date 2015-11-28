package dao;

import javax.persistence.EntityManager;

import org.apache.openjpa.util.IntId;

import modele.Album;

public class AlbumDao extends JPADao<Album, Integer> {

	public AlbumDao(EntityManager em) {
		super(em);
	}

}
