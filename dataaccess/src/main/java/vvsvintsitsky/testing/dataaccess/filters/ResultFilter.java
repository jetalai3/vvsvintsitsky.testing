package vvsvintsitsky.testing.dataaccess.filters;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import vvsvintsitsky.testing.datamodel.Result;

public class ResultFilter extends AbstractFilter<Result> {

	@Override
	public Predicate getQueryPredicate(CriteriaBuilder cb, Root<Result> from) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
