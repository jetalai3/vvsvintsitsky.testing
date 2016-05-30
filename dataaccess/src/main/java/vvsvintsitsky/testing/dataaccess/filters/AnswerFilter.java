package vvsvintsitsky.testing.dataaccess.filters;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Answer_;

public class AnswerFilter extends AbstractFilter<Answer> {
	private Long id;
	private String text;
	private boolean correct;
	private boolean isFetchResults;
	private boolean isFetchQuestions;

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

	public boolean getCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public boolean getIsFetchResults() {
		return isFetchResults;
	}

	public void setIsFetchResults(boolean isFetchResults) {
		this.isFetchResults = isFetchResults;
	}

	public boolean getIsFetchQuestions() {
		return isFetchQuestions;
	}

	public void setIsFetchQuestions(boolean isFetchQuestions) {
		this.isFetchQuestions = isFetchQuestions;
	}

	@Override
	public Predicate getQueryPredicate(CriteriaBuilder cb, Root<Answer> from) {
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if (id != null) {
			predicateList.add(idPredicate(cb, from));
		}
		if (text != null) {
			predicateList.add(textPredicate(cb, from));
		}
		if (correct = true) {
			predicateList.add(correctPredicate(cb, from));
		}
		if (!(predicateList.isEmpty())) {
			return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
		}
		return null;
	}

	private Predicate idPredicate(CriteriaBuilder cb, Root<Answer> from) {
		return cb.equal(from.get(Answer_.id), getId());
	}

	private Predicate textPredicate(CriteriaBuilder cb, Root<Answer> from) {
		return cb.equal(from.get(Answer_.text), getText());
	}

	private Predicate correctPredicate(CriteriaBuilder cb, Root<Answer> from) {
		return cb.equal(from.get(Answer_.correct), getCorrect());
	}

	public void setFetching(Root<Answer> from) {
		if (isFetchResults) {
			from.fetch(Answer_.results, JoinType.LEFT);
		}
		if (isFetchQuestions) {
			from.fetch(Answer_.question, JoinType.LEFT);
		}
	}
	// from.get(Answer_.question).get(Question_.id).getQuestion()

}
