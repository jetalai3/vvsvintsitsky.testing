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
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Question_;
import vvsvintsitsky.testing.datamodel.Subject_;
import vvsvintsitsky.testing.datamodel.VariousTexts;
import vvsvintsitsky.testing.datamodel.VariousTexts_;

public class QuestionFilter extends AbstractFilter<Question> {
	private Long id;
	private String text;
	private boolean isFetchSubject;
	private boolean isFetchTexts;
	private String subjectName;
	private String language;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isFetchSubject() {
		return isFetchSubject;
	}

	public void setFetchSubject(boolean isFetchSubject) {
		this.isFetchSubject = isFetchSubject;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public boolean isFetchTexts() {
		return isFetchTexts;
	}

	public void setFetchTexts(boolean isFetchTexts) {
		this.isFetchTexts = isFetchTexts;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public Predicate getQueryPredicate(CriteriaBuilder cb, Root<Question> from) {
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if (id != null) {
			predicateList.add(idPredicate(cb, from));
		}
		if (subjectName != null) {
			predicateList.add(subjectNamePredicate(cb, from));
		}
		if (!(predicateList.isEmpty())) {
			return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
		}
		return null;
	}

	private Predicate idPredicate(CriteriaBuilder cb, Root<Question> from) {
		return cb.equal(from.get(Question_.id), getId());
	}

	private Predicate subjectNamePredicate(CriteriaBuilder cb, Root<Question> from) {
		if (language.equals("ru")) {
			return cb.equal(from.get(Question_.subject).get(Subject_.subjectNames).get(LocalTexts_.rusText)
					.get(VariousTexts_.txt), getSubjectName());
		}
		if (language.equals("en")) {
			return cb.equal(from.get(Question_.subject).get(Subject_.subjectNames).get(LocalTexts_.engText)
					.get(VariousTexts_.txt), getSubjectName());
		}
		return null;
	}

	public void setFetching(Root<Question> from) {
		if (isFetchTexts) {
			if (language == "ru") {
				from.fetch(Question_.questionTexts, JoinType.LEFT).fetch(LocalTexts_.rusText, JoinType.LEFT);
			}
			if (language == "en") {
				from.fetch(Question_.questionTexts, JoinType.LEFT).fetch(LocalTexts_.engText, JoinType.LEFT);
			}
		}
		if (isFetchSubject) {
			from.fetch(Question_.subject, JoinType.LEFT);
			if (language == "ru") {
				from.fetch(Question_.subject, JoinType.LEFT).fetch(Subject_.subjectNames).fetch(LocalTexts_.rusText,
						JoinType.LEFT);
			}
			if (language == "en") {
				from.fetch(Question_.subject, JoinType.LEFT).fetch(Subject_.subjectNames).fetch(LocalTexts_.engText,
						JoinType.LEFT);
			}
		}
	}

	@Override
	public void setSorting(CriteriaQuery<Question> query, Root<Question> from) {
		if (getSortProperty() == Question_.id) {
			query.orderBy(new OrderImpl(from.get(getSortProperty()), isSortOrder()));
			return;
		}
		if (getSortProperty() == Subject_.subjectNames) {
			if (language.equals("ru")) {
				query.orderBy(new OrderImpl(from.get(Question_.subject).get(Subject_.subjectNames)
						.get(LocalTexts_.rusText).get(VariousTexts_.txt), isSortOrder()));
				return;
			}
			if (language.equals("en")) {
				query.orderBy(new OrderImpl(from.get(Question_.subject).get(Subject_.subjectNames)
						.get(LocalTexts_.engText).get(VariousTexts_.txt), isSortOrder()));
				return;
			}
		}
		if (getSortProperty() == Question_.questionTexts) {
			if (language.equals("ru")) {
				query.orderBy(
						new OrderImpl(from.get(Question_.questionTexts).get(LocalTexts_.rusText).get(VariousTexts_.txt),
								isSortOrder()));
				return;
			}
			if (language.equals("en")) {
				query.orderBy(
						new OrderImpl(from.get(Question_.questionTexts).get(LocalTexts_.engText).get(VariousTexts_.txt),
								isSortOrder()));
				return;
			}
		}
	}

}
