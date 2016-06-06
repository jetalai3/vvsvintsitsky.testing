package vvsvintsitsky.testing.dataaccess.filters;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Answer_;
import vvsvintsitsky.testing.datamodel.Question_;

public class AnswerFilter extends AbstractFilter<Answer> {
	private Long id;
	private String text;
	private Boolean correct;
	private boolean isFetchResults;
	private boolean isFetchQuestion;
	private Long questionId;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public void setFetchResults(boolean isFetchResults) {
		this.isFetchResults = isFetchResults;
	}

	public void setFetchQuestion(boolean isFetchQuestion) {
		this.isFetchQuestion = isFetchQuestion;
	}

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

	public Boolean getCorrect() {
		return correct;
	}

	public void setCorrect(Boolean correct) {
		this.correct = correct;
	}

	public boolean getIsFetchResults() {
		return isFetchResults;
	}

	public void setIsFetchResults(boolean isFetchResults) {
		this.isFetchResults = isFetchResults;
	}

	public boolean getIsFetchQuestion() {
		return isFetchQuestion;
	}

	public void setIsFetchQuestion(boolean isFetchQuestion) {
		this.isFetchQuestion = isFetchQuestion;
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
		if (correct != null) {
			predicateList.add(correctPredicate(cb, from));
		}
		if (questionId != null) {
			predicateList.add(questionIdPredicate(cb, from));
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

	private Predicate questionIdPredicate(CriteriaBuilder cb, Root<Answer> from) {
		return cb.equal(from.get(Answer_.question).get(Question_.id), getQuestionId());
	}
	
	public void setFetching(Root<Answer> from) {
		if (isFetchResults) {
			from.fetch(Answer_.results, JoinType.LEFT);
		}
		if (isFetchQuestion) {
			from.fetch(Answer_.question, JoinType.LEFT);
		}
	}
	// from.get(Answer_.question).get(Question_.id).getQuestion()

	@Override
	public void setSorting(CriteriaQuery<Answer> query, Root<Answer> from) {
		// TODO Auto-generated method stub
		
	}

}
