package vvsvintsitsky.testing.dataaccess.filters;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;

import vvsvintsitsky.testing.datamodel.LocalTexts_;
import vvsvintsitsky.testing.datamodel.Question_;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.Subject_;
import vvsvintsitsky.testing.datamodel.VariousTexts_;

public class SubjectFilter extends AbstractFilter<Subject> {
	private Long id;
	private String name;
	private String language;

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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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
		if (language.equals("ru")) {
			return cb.equal(from.get(Subject_.subjectNames).get(LocalTexts_.rusText).get(VariousTexts_.txt), getName());
		}
		if (language.equals("en")) {
			return cb.equal(from.get(Subject_.subjectNames).get(LocalTexts_.engText).get(VariousTexts_.txt), getName());
		}
		return null;
	}

	public void setFetching(Root<Subject> from) {
		if (language.equals("ru")) {
			from.fetch(Subject_.subjectNames, JoinType.LEFT).fetch(LocalTexts_.rusText, JoinType.LEFT);
		}
		if (language.equals("en")) {
			from.fetch(Subject_.subjectNames, JoinType.LEFT).fetch(LocalTexts_.engText, JoinType.LEFT);
		}
	}

	@Override
	public void setSorting(CriteriaQuery<Subject> query, Root<Subject> from) {
		if (getSortProperty() == Subject_.id) {
			query.orderBy(new OrderImpl(from.get(getSortProperty()), isSortOrder()));
			return;
		}
		if (getSortProperty() == Subject_.subjectNames) {
			if (language.equals("ru")) {
				query.orderBy(new OrderImpl(from.get(Subject_.subjectNames).get(LocalTexts_.rusText).get(VariousTexts_.txt), isSortOrder()));
				return;
			}
			if (language.equals("en")) {
				query.orderBy(new OrderImpl(from.get(Subject_.subjectNames).get(LocalTexts_.engText).get(VariousTexts_.txt), isSortOrder()));
				return;
			}
		}
	}
}
