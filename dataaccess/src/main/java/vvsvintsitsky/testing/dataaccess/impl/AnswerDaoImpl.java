package vvsvintsitsky.testing.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import vvsvintsitsky.testing.dataaccess.AnswerDao;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Answer_;
import vvsvintsitsky.testing.datamodel.LocalTexts_;
import vvsvintsitsky.testing.datamodel.Subject_;

@Repository
public class AnswerDaoImpl extends AbstractDaoImpl<Answer, Long> implements AnswerDao {

	protected AnswerDaoImpl() {
		super(Answer.class);
	}
	
	@Override
	public void deleteAnswerByQuestionId(Long id){
		EntityManager em = getEntityManager();
		Query query = em.createQuery("Delete from Answer a where a.question.id = :qId");
		query.setParameter("qId", id);
		query.executeUpdate();
	}

	@Override
	public Answer getWithAllTexts(Long id) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Answer> cq = cb.createQuery(Answer.class).distinct(true);
		Root<Answer> from = cq.from(Answer.class);
		cq.select(from);
		
		cq.where(cb.equal(from.get(Subject_.id), id));
		from.fetch(Answer_.answerTexts, JoinType.LEFT).fetch(LocalTexts_.rusText, JoinType.LEFT);
		from.fetch(Answer_.answerTexts, JoinType.LEFT).fetch(LocalTexts_.engText, JoinType.LEFT);

		TypedQuery<Answer> q = em.createQuery(cq);
		List<Answer> list = q.getResultList();
		
		if(list.size() > 1) {
			throw new IllegalStateException("more than one subject found!");
		} else if(list.size() != 1) {
			throw new IllegalStateException("none subject found!");
		} else {
			return list.get(0);
		}
	}
}
