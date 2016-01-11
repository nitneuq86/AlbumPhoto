package dao;

import java.lang.reflect.ParameterizedType;
import javax.persistence.EntityManager;

import org.apache.openjpa.util.OpenJPAId;

public abstract class JPADao<T, K> implements DAO<T, K> {

	protected EntityManager em;
	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public JPADao(EntityManager em) {
		this.em = em;
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}

	@Override
	public OpenJPAId create(T obj) {
		em.getTransaction().begin();
		em.persist(obj);
		em.getTransaction().commit();
		return (OpenJPAId) em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(obj);
	}

	@Override
	public T read(K id) {
		return em.find(entityClass, id);
	}

	@Override
	public void update(T obj) {
		em.getTransaction().begin();
		em.merge(obj);
		em.getTransaction().commit();
	}

	@Override
	public void delete(T obj) {
		em.getTransaction().begin();
		em.remove(obj);
		em.getTransaction().commit();
	}

}
