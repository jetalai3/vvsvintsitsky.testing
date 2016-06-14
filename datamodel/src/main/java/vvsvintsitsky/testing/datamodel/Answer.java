package vvsvintsitsky.testing.datamodel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Answer extends AbstractModel {

	@ManyToOne(targetEntity = Question.class, fetch = FetchType.LAZY)
	private Question question;

	@Column
	private Boolean correct;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	private LocalTexts answerTexts;
	
	@Transient
	private Boolean answered = new Boolean(false);
	
	@JoinTable(name = "mistakes", joinColumns = { @JoinColumn(name = "answer_id") }, inverseJoinColumns = {
			@JoinColumn(name = "result_id") })
	@ManyToMany(targetEntity = Answer.class, fetch = FetchType.LAZY)
	private List<Result> results;

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Boolean getCorrect() {
		return correct;
	}

	public void setCorrect(Boolean correct) {
		this.correct = correct;
	}

	public LocalTexts getAnswerTexts() {
		return answerTexts;
	}

	public void setAnswerTexts(LocalTexts answerTexts) {
		this.answerTexts = answerTexts;
	}

	@Transient
	public Boolean getAnswered() {
		return answered;
	}

	@Transient
	public void setAnswered(Boolean answered) {
		this.answered = answered;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answerTexts == null) ? 0 : answerTexts.hashCode());
		result = prime * result + ((answered == null) ? 0 : answered.hashCode());
		result = prime * result + ((correct == null) ? 0 : correct.hashCode());
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		result = prime * result + ((results == null) ? 0 : results.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Answer other = (Answer) obj;
		if (answerTexts == null) {
			if (other.answerTexts != null)
				return false;
		} else if (!answerTexts.equals(other.answerTexts))
			return false;
		if (answered == null) {
			if (other.answered != null)
				return false;
		} else if (!answered.equals(other.answered))
			return false;
		if (correct == null) {
			if (other.correct != null)
				return false;
		} else if (!correct.equals(other.correct))
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		if (results == null) {
			if (other.results != null)
				return false;
		} else if (!results.equals(other.results))
			return false;
		return true;
	}
	

}
