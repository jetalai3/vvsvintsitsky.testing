package vvsvintsitsky.testing.datamodel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Question extends AbstractModel {

	@Column
	private String text;

	@OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
	private List<Answer> answers;

	@ManyToOne(targetEntity = Subject.class, fetch = FetchType.LAZY)
	private Subject subject;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

}
