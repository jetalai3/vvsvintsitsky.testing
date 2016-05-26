package vvsvintsitsky.testing.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;

import vvsvintsitsky.testing.dataaccess.AbstractDao;
import vvsvintsitsky.testing.dataaccess.filters.AbstractFilter;
import vvsvintsitsky.testing.dataaccess.filters.AccountFilter;
import vvsvintsitsky.testing.datamodel.AbstractModel;
import vvsvintsitsky.testing.datamodel.Account;

public class AbstractDaoImpl<T, ID> implements AbstractDao<T, ID> {

	@PersistenceContext
	private EntityManager entityManager;

	private final Class<T> entityClass;

	protected AbstractDaoImpl(final Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public List<T> getAll() {
		final CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(getEntityClass());
		query.from(getEntityClass());
		final List<T> lst = entityManager.createQuery(query).getResultList();
		return lst;
	}

	@Override
	public T get(final ID id) {
		return entityManager.find(getEntityClass(), id);
	}

	@Override
	public T insert(final T entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public T update(T entity) {
		entity = entityManager.merge(entity);
		entityManager.flush();
		return entity;
	}

	@Override
	public void delete(ID id) {
		entityManager.createQuery(String.format("delete from %s e where e.id = :id", entityClass.getSimpleName()))
				.setParameter("id", id).executeUpdate();
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public <FL extends AbstractFilter<T>> List<T> find(FL filter) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<T> cq = cb.createQuery(getEntityClass());

		Root<T> from = cq.from(getEntityClass());

		// set selection
		cq.select(from);

		Predicate queryPredicate = filter.getQueryPredicate(cb, from);
		filter.setFetching(from);
		if (queryPredicate != null) {

			cq.where(queryPredicate);
		}
		// set sort params

		setSorting(filter, cq, from);

		TypedQuery<T> q = entityManager.createQuery(cq);

		// set paging
		setPaging(filter, q);

		// set execute query
		List<T> allitems = q.getResultList();

		return allitems;

	}

	private <FL extends AbstractFilter<T>> void setPaging(FL filter, TypedQuery<T> q) {
		if (filter.getOffset() != null && filter.getLimit() != null) {
			q.setFirstResult(filter.getOffset());
			q.setMaxResults(filter.getLimit());
		}
	}

	private <FL extends AbstractFilter<T>> void setSorting(FL filter, CriteriaQuery<T> cq, Root<T> from) {
		if (filter.getSortProperty() != null) {
			cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
		}
	}

	@Override
	public <FL extends AbstractFilter<T>> Long count(FL filter) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<T> from = cq.from(getEntityClass());
		cq.select(cb.count(from));
		Predicate queryPredicate = filter.getQueryPredicate(cb, from);

		if (queryPredicate != null) {

			cq.where(queryPredicate);
		}
		TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();

	}
}
