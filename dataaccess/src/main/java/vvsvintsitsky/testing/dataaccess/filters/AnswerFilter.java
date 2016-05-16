package vvsvintsitsky.testing.dataaccess.filters;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import vvsvintsitsky.testing.datamodel.Answer;

public class AnswerFilter extends AbstractFilter<Answer> {
	private Long Id;
	
	@Override
	public Predicate getQueryPredicate(CriteriaBuilder cb, Root<Answer> from) {
		// TODO Auto-generated method stub
		return null;
	}

	//from.get(Answer_.question).get(Question_.id).getQuestion()

}
