package vvsvintsitsky.testing.dataaccess.filters;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import vvsvintsitsky.testing.datamodel.Examination;

public class ExaminationFilter extends AbstractFilter<Examination> {

	@Override
	public Predicate getQueryPredicate(CriteriaBuilder cb, Root<Examination> from) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
