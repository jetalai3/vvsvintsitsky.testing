package vvsvintsitsky.testing.dataaccess.filters;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.Account_;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.Subject_;

public class SubjectFilter extends AbstractFilter<Subject> {
	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Predicate getQueryPredicate(CriteriaBuilder cb, Root<Subject> from) {
		if (allParameters()) {
			return allParametersPredicate(cb, from);
		}
		if(idParameters()){
			return idPredicate(cb, from);
		}
		if(nameParameters()){
			return namePredicate(cb, from);
		}
		return null;
	}

	private boolean allParameters() {
		return getId() != null && getName() != null;
	}
	
	private boolean idParameters(){
		return getId() != null && getName() == null;
	}
	
	private boolean nameParameters(){
		return getId() == null && getName() != null;
	}
	
	private Predicate idPredicate(CriteriaBuilder cb, Root<Subject> from) {
		return cb.equal(from.get(Subject_.id), getId());
	}
	
	private Predicate namePredicate(CriteriaBuilder cb, Root<Subject> from) {
		return cb.equal(from.get(Subject_.name), getName());
	}
	
	private Predicate allParametersPredicate(CriteriaBuilder cb, Root<Subject> from){
		return cb.and(idPredicate(cb, from), namePredicate(cb, from));
	}
}
