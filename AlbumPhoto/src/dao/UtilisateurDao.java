package dao;

import javax.persistence.EntityManager;

import modele.Utilisateur;

public class UtilisateurDao extends JPADao<Utilisateur, String>{

	public UtilisateurDao(EntityManager em) {
		super(em);
	}

}
