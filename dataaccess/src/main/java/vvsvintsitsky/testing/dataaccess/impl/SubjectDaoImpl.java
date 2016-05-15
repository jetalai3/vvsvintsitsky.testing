package vvsvintsitsky.testing.dataaccess.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;
import vvsvintsitsky.testing.dataaccess.SubjectDao;
import vvsvintsitsky.testing.dataaccess.filters.SubjectFilter;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.Subject_;

@Repository
public class SubjectDaoImpl extends AbstractDaoImpl<Subject, Long> implements SubjectDao {

	protected SubjectDaoImpl() {
		super(Subject.class);
	}

	@Override
	public List<Subject> find(SubjectFilter filter) {
		EntityManager em = getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Subject> cq = cb.createQuery(Subject.class);

		Root<Subject> from = cq.from(Subject.class);

		// set selection
		cq.select(from);
		cq.where(getQuerryPredicate(filter, cb, from));
		// set sort params
		setSorting(filter, cq, from);

		TypedQuery<Subject> q = em.createQuery(cq);

		// set paging
		setPaging(filter, q);

		// set execute query
		List<Subject> allitems = q.getResultList();
		return allitems;

	}

	private void setPaging(SubjectFilter filter, TypedQuery<Subject> q) {
		if (filter.getOffset() != null && filter.getLimit() != null) {
			q.setFirstResult(filter.getOffset());
			q.setMaxResults(filter.getLimit());
		}
	}

	private void setSorting(SubjectFilter filter, CriteriaQuery<Subject> cq, Root<Subject> from) {
		if (filter.getSortProperty() != null) {
			cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
		}
	}

	private Predicate getQuerryPredicate(SubjectFilter filter, CriteriaBuilder cb, Root<Subject> from) {
		if (allParameters(filter)) {
			return allParametersPredicate(filter, cb, from);
		}
		if (idParameters(filter)) {
			return idParametersPredicate(filter, cb, from);
		}
		if (nameParameters(filter)) {
			return nameParametersPredicate(filter, cb, from);
		}
		return null;
	}

	// conditions for getQuerry
	private boolean allParameters(SubjectFilter filter) {
		return filter.getId() != null && filter.getName() != null;
	}

	private boolean idParameters(SubjectFilter filter) {
		return filter.getId() != null;
	}

	private boolean nameParameters(SubjectFilter filter) {
		return filter.getName() != null;
	}

	private Predicate idParametersPredicate(SubjectFilter filter, CriteriaBuilder cb, Root<Subject> from) {
		return cb.equal(from.get(Subject_.id), filter.getId());
	}

	private Predicate nameParametersPredicate(SubjectFilter filter, CriteriaBuilder cb, Root<Subject> from) {
		return cb.equal(from.get(Subject_.name), filter.getName());
	}

	private Predicate allParametersPredicate(SubjectFilter filter, CriteriaBuilder cb, Root<Subject> from) {
		return cb.and(idParametersPredicate(filter, cb, from), nameParametersPredicate(filter, cb, from));
	}
}
