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
}
