package vvsvintsitsky.testing.dataaccess;

import java.util.List;

import vvsvintsitsky.testing.dataaccess.filters.AbstractFilter;

public interface AbstractDao<T, ID> {

	public <FL extends AbstractFilter<T>> List<T> find(FL filter);
	
    List<T> getAll();

    T get(final ID id);

    T insert(final T entity);

    T update(T entity);

    void delete(ID id);
}
