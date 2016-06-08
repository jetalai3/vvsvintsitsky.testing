package vvsvintsitsky.testing.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import vvsvintsitsky.testing.dataaccess.QuestionDao;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.AccountProfile_;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Question_;

@Repository
public class QuestionDaoImpl extends AbstractDaoImpl<Question, Long> implements QuestionDao {

	protected QuestionDaoImpl() {
		super(Question.class);
	}

	@Override
	public Question getQuestionWithAnswers(Long id) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Question> cq = cb.createQuery(Question.class).distinct(true);
		Root<Question> from = cq.from(Question.class);
		cq.select(from);
		from.fetch(Question_.answers, JoinType.LEFT);

		Predicate idEqualsPredicate = cb.equal(from.get(Question_.id), id);

		cq.where(idEqualsPredicate);

		TypedQuery<Question> q = em.createQuery(cq);

		List<Question> list = q.getResultList();
		for(Question qq : list){
			//System.out.println(qq.getText());
		}
 		if (list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			throw new IllegalArgumentException("More than one question found!");
		}
	}
}
