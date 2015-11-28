package dao;

public interface DAO<T,K> {
	
	public K create(T obj);
	public T read(K id);
	public void update(T obj);
	public void delete(T obj);
	
	
}
