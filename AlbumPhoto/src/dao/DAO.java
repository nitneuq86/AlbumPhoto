package dao;

import org.apache.openjpa.util.OpenJPAId;

public interface DAO<T, K> {

	public OpenJPAId create(T obj);

	public T read(K id);

	public void update(T obj);

	public void delete(T obj);

}
