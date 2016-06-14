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
import vvsvintsitsky.testing.datamodel.Answer_;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.LanguageVariant;
import vvsvintsitsky.testing.datamodel.LocalTexts_;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Question_;

@Repository
public class QuestionDaoImpl extends AbstractDaoImpl<Question, Long> implements QuestionDao {

	protected QuestionDaoImpl() {
		super(Question.class);
	}

	@Override
	public List<Question> getQuestionsWithAnswers(Long id, String language) {
		EntityManager em = getEntityManager();

		Query query = null;
		if (language.equals("ru")) {
			query = em.createQuery(
					"select distinct q from Question q left join fetch q.questionTexts qt left join fetch qt.rusText left join fetch q.answers a left join fetch a.answerTexts rt left join fetch rt.rusText left join q.examinations e where e.id = :ex order by q.id");
		}
		if (language.equals("en")) {
			query = em.createQuery(
					"select distinct q from Question q left join fetch q.questionTexts qt left join fetch qt.engText left join fetch q.answers a left join fetch a.answerTexts rt left join fetch rt.engText left join q.examinations e where e.id = :ex order by q.id");
		}

		query.setParameter("ex", id);
		return query.getResultList();

	}

	@Override
	public Question getQuestionWithAnswers(Long id) {
		EntityManager em = getEntityManager();

		Query q = em.createQuery("select distinct q from Question q left join fetch q.questionTexts qt left "
				+ "join fetch qt.rusText rusT left join fetch qt.engText engT left join "
				+ "fetch q.answers a left join fetch a.answerTexts rt left join fetch "
				+ "rt.rusText art left join fetch rt.engText aet where q.id = " + ":questionId order by q.id");
		q.setParameter("questionId", id);

		return (Question) q.getResultList().get(0);

	}
}
