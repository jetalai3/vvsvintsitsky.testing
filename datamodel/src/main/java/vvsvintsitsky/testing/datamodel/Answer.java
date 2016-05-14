package vvsvintsitsky.testing.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class Answer extends AbstractModel {
	
	@ManyToOne(targetEntity = Question.class, fetch = FetchType.LAZY)
	private Question question;

	@Column
	private String text;
	
	@Column
	private Boolean correct;

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
