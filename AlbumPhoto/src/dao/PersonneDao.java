package dao;

import javax.persistence.EntityManager;

import modele.Personne;

public class PersonneDao extends JPADao<Personne, Integer> {

	public PersonneDao(EntityManager em) {
		super(em);
	}

}
