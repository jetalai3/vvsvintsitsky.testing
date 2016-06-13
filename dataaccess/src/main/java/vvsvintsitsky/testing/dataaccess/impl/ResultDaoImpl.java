package vvsvintsitsky.testing.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import vvsvintsitsky.testing.dataaccess.ResultDao;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Result;

@Repository
public class ResultDaoImpl extends AbstractDaoImpl<Result, Long> implements ResultDao {

	protected ResultDaoImpl() {
		super(Result.class);
	}
	
	@Override
	public Result getResultWithAnswersAndQuestions(Long id){
		EntityManager em = getEntityManager();
//		Query query = em.createQuery("Select distinct q from Question q left join fetch q.answers a");
		
		Query qq = em.createQuery("select distinct r from Result r left join fetch r.answers a left join fetch a.question q where r.id = :res order by q.id");
		qq.setParameter("res", id);
		
		List<Result> result = (List<Result>) qq.getResultList();
		
		if(result.isEmpty()){
			throw new IllegalStateException("No result found");
		} else if(result.size() != 1) {
			throw new IllegalStateException("More than one result found");
		} else {
			return result.get(0);
		}
		
	}

	@Override
	public List<Question> getResultQuestionsWithAnswers(Long id, String language) {
		EntityManager em = getEntityManager();
//		Query query = em.createQuery("Select distinct q from Question q left join fetch q.answers a");
		
		Query query = null;
		if (language.equals("ru")) {
			query = em.createQuery(
					"select distinct q from Question q left join fetch q.questionTexts qt left join fetch qt.rusText left join fetch q.answers a left join fetch a.answerTexts rt left join fetch rt.rusText left join a.results resl where resl.id = :res order by q.id");
		}
		if (language.equals("en")) {
			query = em.createQuery(
					"select distinct q from Question q left join fetch q.questionTexts qt left join fetch qt.engText left join fetch q.answers a left join fetch a.answerTexts rt left join fetch rt.engText left join a.results resl where resl.id = :res order by q.id");
		}
		query.setParameter("res", id);
		
		List<Question> result = (List<Question>) query.getResultList();
		
		return result;
	}
}
