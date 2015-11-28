package dao;

import javax.persistence.EntityManager;

import org.apache.openjpa.util.IntId;

import modele.Personne;

public class PersonneDao extends JPADao<Personne, IntId>{

	public PersonneDao(EntityManager em) {
		super(em);
	}
	
}
