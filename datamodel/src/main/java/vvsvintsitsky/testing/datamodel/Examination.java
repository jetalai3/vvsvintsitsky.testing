package vvsvintsitsky.testing.datamodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NamedQuery;

@Entity
@NamedQuery(name = "examinationWithQuestionsAndAnswers",
query = "SELECT DISTINCT e, q, a FROM Examination e LEFT JOIN e.questions q LEFT JOIN q.answers a WHERE e.id = :exId")

public class Examination extends AbstractModel {

	@ManyToOne(targetEntity = AccountProfile.class, fetch = FetchType.LAZY)
	private AccountProfile accountProfile;

	@Column
	private Date beginDate;

	@Column
	private Date endDate;

	@Column
	private String name;

	@JoinTable(name = "examination_2_question", joinColumns = {
			@JoinColumn(name = "examination_id") }, inverseJoinColumns = { @JoinColumn(name = "question_id") })
	@ManyToMany(targetEntity = Question.class, fetch = FetchType.LAZY)
	private List<Question> questions = new ArrayList<Question>();

	@OneToMany(mappedBy = "examination", fetch = FetchType.LAZY)
	private List<Result> results;

	@ManyToOne(targetEntity = Subject.class, fetch = FetchType.LAZY)
	private Subject subject;

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public AccountProfile getAccountProfile() {
		return accountProfile;
	}

	public void setAccountProfile(AccountProfile accountProfile) {
		this.accountProfile = accountProfile;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

}
