package vvsvintsitsky.testing.dataaccess.filters;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;

import vvsvintsitsky.testing.datamodel.Question_;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.Subject_;

public class SubjectFilter extends AbstractFilter<Subject> {
	private Long id;
	private String name;
	private boolean isFetchExaminations;
	private boolean isFetchQuestions;

	public boolean getIsFetchExaminations() {
		return isFetchExaminations;
	}

	public void setIsFetchExaminations(boolean isFetchExaminations) {
		this.isFetchExaminations = isFetchExaminations;
	}

	public boolean getIsFetchQuestions() {
		return isFetchQuestions;
	}

	public void setIsFetchQuestions(boolean isFetchQuestions) {
		this.isFetchQuestions = isFetchQuestions;
	}

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
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if (id != null) {
			predicateList.add(idPredicate(cb, from));
		}
		if (name != null) {
			predicateList.add(namePredicate(cb, from));
		}
		if (!(predicateList.isEmpty())) {
			return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
		}
		return null;
	}

	private Predicate idPredicate(CriteriaBuilder cb, Root<Subject> from) {
		return cb.equal(from.get(Subject_.id), getId());
	}

	private Predicate namePredicate(CriteriaBuilder cb, Root<Subject> from) {
		return cb.equal(from.get(Subject_.name), getName());
	}

	public void setFetching(Root<Subject> from) {
		if (isFetchExaminations) {
			from.fetch(Subject_.examinations, JoinType.LEFT);
		}
		if (isFetchQuestions) {
			from.fetch(Subject_.questions, JoinType.LEFT);
		}
	}

	@Override
	public void setSorting(CriteriaQuery<Subject> query, Root<Subject> from) {
		if (getSortProperty() == Subject_.id) {
			query.orderBy(new OrderImpl(from.get(getSortProperty()), isSortOrder()));
			return;
		}
		if (getSortProperty() == Subject_.name) {
			query.orderBy(new OrderImpl(from.get(getSortProperty()), isSortOrder()));
			return;

		}
	}
}
