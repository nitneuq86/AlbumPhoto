package dao;

import javax.persistence.*;

public class DAOFactory {

	private static final DAOFactory INSTANCE = new DAOFactory();
	private EntityManagerFactory emf;
	private EntityManager em;
	private PersonneDao personneDao;
	private AlbumDao albumDao;
	private PhotoDao photoDao;
	

	private DAOFactory() {
		emf = Persistence.createEntityManagerFactory("DB");
		em = emf.createEntityManager();
	}

	public static DAOFactory getInstance() {
		return INSTANCE;
	}

	public PersonneDao getPersonneDao() {
		if (personneDao == null) {
			personneDao = new PersonneDao(em);
		}
		return personneDao;
	}

	public AlbumDao getAlbumDao() {
		if (albumDao == null) {
			albumDao = new AlbumDao(em);
		}
		return albumDao;
	}
	
	public PhotoDao getPhotoDao() {
		if (photoDao==null) {
			photoDao=new PhotoDao(em);
		}
		return photoDao;
	}
}
