package vvsvintsitsky.testing.dataaccess.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import vvsvintsitsky.testing.dataaccess.AnswerDao;
import vvsvintsitsky.testing.datamodel.Answer;

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
}
