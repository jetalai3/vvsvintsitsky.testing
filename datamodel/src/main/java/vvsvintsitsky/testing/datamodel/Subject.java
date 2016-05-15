package vvsvintsitsky.testing.datamodel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Subject extends AbstractModel {
	@Column
	private String name;
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
	private List<Examination> examinations;
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
	private List<Question> questions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
