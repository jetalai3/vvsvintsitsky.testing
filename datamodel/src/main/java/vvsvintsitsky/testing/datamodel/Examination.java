package vvsvintsitsky.testing.datamodel;

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

@Entity
public class Examination extends AbstractModel {

	@ManyToOne(targetEntity = AccountProfile.class, fetch = FetchType.LAZY)
	private AccountProfile accountProfile;
	
	@Column
	private Date beginDate;
	
	@Column
	private Date endDate;
	
	@Column
	private String name;
	
	@JoinTable(name = "examination_2_question", joinColumns = { @JoinColumn(name = "examination_id") }, inverseJoinColumns = { @JoinColumn(name = "question_id") })
    @ManyToMany(targetEntity = Question.class, fetch = FetchType.LAZY)
    private List<Question> questions;
	
	@OneToMany(mappedBy = "examination", fetch = FetchType.LAZY)
    private List<Result> results;
	
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
