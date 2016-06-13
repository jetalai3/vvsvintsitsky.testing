package vvsvintsitsky.testing.datamodel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Question extends AbstractModel {

	@OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
	private List<Answer> answers;

	@ManyToOne(targetEntity = Subject.class, fetch = FetchType.LAZY)
	private Subject subject;

	@JoinTable(name = "examination_2_question", joinColumns = {
			@JoinColumn(name = "question_id") }, inverseJoinColumns = { @JoinColumn(name = "examination_id") })
	@ManyToMany(targetEntity = Examination.class, fetch = FetchType.LAZY)
	private List<Examination> examinations;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	private LocalTexts questionTexts;

	public List<Examination> getExaminations() {
		return examinations;
	}

	public void setExaminations(List<Examination> examinations) {
		this.examinations = examinations;
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

	public LocalTexts getQuestionTexts() {
		return questionTexts;
	}

	public void setQuestionTexts(LocalTexts questionTexts) {
		this.questionTexts = questionTexts;
	}

	

}
