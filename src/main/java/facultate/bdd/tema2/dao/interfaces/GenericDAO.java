package facultate.bdd.tema2.dao.interfaces;

import java.util.List;

public interface GenericDAO<T> {
	public void close();

	public T createOrUpdate(T entity);

	public T findById(int id);

	public T update(T entity);

	public void delete(T entity);

	public void deleteAll();

	public List<T> readAll();

}
