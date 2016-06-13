package vvsvintsitsky.testing.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import vvsvintsitsky.testing.dataaccess.ExaminationDao;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Examination_;
import vvsvintsitsky.testing.datamodel.LocalTexts_;
import vvsvintsitsky.testing.datamodel.Question_;
import vvsvintsitsky.testing.datamodel.Subject_;

@Repository
public class ExaminationDaoImpl extends AbstractDaoImpl<Examination, Long> implements ExaminationDao {

	protected ExaminationDaoImpl() {
		super(Examination.class);
	}

	@Override
	public Examination getWithAllTexts(Long id, String language) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Examination> cq = cb.createQuery(Examination.class).distinct(true);
		Root<Examination> from = cq.from(Examination.class);
		cq.select(from);
		
		cq.where(cb.equal(from.get(Examination_.id), id));
		from.fetch(Examination_.examinationNames, JoinType.LEFT).fetch(LocalTexts_.rusText, JoinType.LEFT);
		from.fetch(Examination_.examinationNames, JoinType.LEFT).fetch(LocalTexts_.engText, JoinType.LEFT);

		if(language.equals("ru")) {
			from.fetch(Examination_.subject, JoinType.LEFT).fetch(Subject_.subjectNames, JoinType.LEFT).fetch(LocalTexts_.rusText, JoinType.LEFT);
			from.fetch(Examination_.questions, JoinType.LEFT).fetch(Question_.questionTexts, JoinType.LEFT).fetch(LocalTexts_.rusText, JoinType.LEFT);
		}
		if(language.equals("en")) {
			from.fetch(Examination_.subject, JoinType.LEFT).fetch(Subject_.subjectNames, JoinType.LEFT).fetch(LocalTexts_.engText, JoinType.LEFT);
			from.fetch(Examination_.questions, JoinType.LEFT).fetch(Question_.questionTexts, JoinType.LEFT).fetch(LocalTexts_.engText, JoinType.LEFT);
		}
		
		TypedQuery<Examination> q = em.createQuery(cq);
		List<Examination> list = q.getResultList();
		
		if(list.size() > 1) {
			throw new IllegalStateException("more than one subject found!");
		} else if(list.size() != 1) {
			throw new IllegalStateException("none subject found!");
		} else {
			return list.get(0);
		}
	}

}
