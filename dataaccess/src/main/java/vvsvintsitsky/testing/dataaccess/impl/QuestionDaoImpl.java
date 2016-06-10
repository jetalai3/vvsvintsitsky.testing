package vvsvintsitsky.testing.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Question_;

@Repository
public class QuestionDaoImpl extends AbstractDaoImpl<Question, Long> implements QuestionDao {

	protected QuestionDaoImpl() {
		super(Question.class);
	}

	@Override
	public List<Question> getQuestionsWithAnswers(Long id) {
		EntityManager em = getEntityManager();
		Query query = em.createQuery(
				"select distinct q from Question q left join fetch q.answers a left join q.examinations e where e.id = :ex order by q.id");
		query.setParameter("ex", id);
		return query.getResultList();

	}
	
	@Override
	public Question getQuestionWithAnswers(Long id){
		EntityManager em = getEntityManager();
		Query query = em.createQuery(
				"select distinct q from Question q left join fetch q.answers a where q.id = :questionId order by q.id");
		query.setParameter("questionId", id);
		return (Question) query.getResultList();
		
	}
}
