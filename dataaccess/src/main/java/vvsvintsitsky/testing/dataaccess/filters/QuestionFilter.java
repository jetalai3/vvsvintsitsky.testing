package vvsvintsitsky.testing.dataaccess.filters;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import vvsvintsitsky.testing.datamodel.Question;

public class QuestionFilter extends AbstractFilter<Question> {

	@Override
	public Predicate getQueryPredicate(CriteriaBuilder cb, Root<Question> from) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
