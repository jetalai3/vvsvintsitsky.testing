package vvsvintsitsky.testing.datamodel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Subject extends AbstractModel {
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	private LocalTexts subjectNames;
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
	private List<Examination> examinations;
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
	private List<Question> questions;

	public LocalTexts getSubjectNames() {
		return subjectNames;
	}

	public void setSubjectNames(LocalTexts subjectNames) {
		this.subjectNames = subjectNames;
	}

	public List<Examination> getExaminations() {
		return examinations;
	}

	public void setExaminations(List<Examination> examinations) {
		this.examinations = examinations;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

}
