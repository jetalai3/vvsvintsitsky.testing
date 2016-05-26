package vvsvintsitsky.testing.datamodel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Answer extends AbstractModel {

	@ManyToOne(targetEntity = Question.class, fetch = FetchType.LAZY)
	private Question question;

	@Column
	private String text;

	@Column
	private Boolean correct;

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

}
