package vvsvintsitsky.testing.dataaccess.filters;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Question_;

public class QuestionFilter extends AbstractFilter<Question> {
	private Long id;
	private String text;
	private boolean isFetchSubject;
	private boolean isFetchAnswers;

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

	public boolean isFetchAnswers() {
		return isFetchAnswers;
	}

	public void setFetchAnswers(boolean isFetchAnswers) {
		this.isFetchAnswers = isFetchAnswers;
	}

	public void setFetchSubject(boolean isFetchSubject) {
		this.isFetchSubject = isFetchSubject;
	}

	public boolean isFetchSubject() {
		return isFetchSubject;
	}

	@Override
	public Predicate getQueryPredicate(CriteriaBuilder cb, Root<Question> from) {
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if (id != null) {
			predicateList.add(idPredicate(cb, from));
		}
		if (text != null) {
			predicateList.add(textPredicate(cb, from));
		}
		if (!(predicateList.isEmpty())) {
			return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
		}
		return null;
	}

	private Predicate idPredicate(CriteriaBuilder cb, Root<Question> from) {
		return cb.equal(from.get(Question_.id), getId());
	}

	private Predicate textPredicate(CriteriaBuilder cb, Root<Question> from) {
		return cb.equal(from.get(Question_.text), getId());
	}

	public void setFetching(Root<Question> from) {
		if (isFetchSubject) {
			from.fetch(Question_.subject, JoinType.LEFT);
		}
		if (isFetchAnswers) {
			from.fetch(Question_.answers, JoinType.LEFT);
		}
	}

}
