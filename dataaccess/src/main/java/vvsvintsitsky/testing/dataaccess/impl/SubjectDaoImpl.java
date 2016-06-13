package vvsvintsitsky.testing.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import vvsvintsitsky.testing.dataaccess.SubjectDao;
import vvsvintsitsky.testing.datamodel.LocalTexts_;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.Subject_;

@Repository
public class SubjectDaoImpl extends AbstractDaoImpl<Subject, Long> implements SubjectDao {

	protected SubjectDaoImpl() {
		super(Subject.class);
	}

	@Override
	public Subject getWithAllTexts(Long id) {
		
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Subject> cq = cb.createQuery(Subject.class).distinct(true);
		Root<Subject> from = cq.from(Subject.class);
		cq.select(from);
		
		cq.where(cb.equal(from.get(Subject_.id), id));
		from.fetch(Subject_.subjectNames, JoinType.LEFT).fetch(LocalTexts_.rusText, JoinType.LEFT);
		from.fetch(Subject_.subjectNames, JoinType.LEFT).fetch(LocalTexts_.engText, JoinType.LEFT);

		TypedQuery<Subject> q = em.createQuery(cq);
		List<Subject> list = q.getResultList();
		
		if(list.size() > 1) {
			throw new IllegalStateException("more than one subject found!");
		} else if(list.size() != 1) {
			throw new IllegalStateException("none subject found!");
		} else {
			return list.get(0);
		}
		
	}

	@Override
	public List<Subject> getAllWithLanguageText(String language) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Subject> cq = cb.createQuery(Subject.class).distinct(true);
		Root<Subject> from = cq.from(Subject.class);
		cq.select(from);

		if(language.equals("ru")) {
			from.fetch(Subject_.subjectNames, JoinType.LEFT).fetch(LocalTexts_.rusText, JoinType.LEFT);
		}
		if(language.equals("en")) {
			from.fetch(Subject_.subjectNames, JoinType.LEFT).fetch(LocalTexts_.engText, JoinType.LEFT);
		}

		TypedQuery<Subject> q = em.createQuery(cq);
		List<Subject> list = q.getResultList();
		
		return list;
	}

}
