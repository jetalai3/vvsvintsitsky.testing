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
public class Result extends AbstractModel {

	@ManyToOne(targetEntity = Examination.class, fetch = FetchType.LAZY)
	private Examination examination;
	
	@ManyToOne(targetEntity = AccountProfile.class, fetch = FetchType.LAZY)
	private AccountProfile accountProfile;
	
	@Column
	private Integer points;
	
	@JoinTable(name = "mistakes", joinColumns = { @JoinColumn(name = "result_id") }, inverseJoinColumns = { @JoinColumn(name = "answer_id") })
    @ManyToMany(targetEntity = Answer.class, fetch = FetchType.LAZY)
	private List<Answer> answers;
	
	public Examination getExamination() {
		return examination;
	}

	public void setExamination(Examination examination) {
		this.examination = examination;
	}

	public AccountProfile getAccountProfile() {
		return accountProfile;
	}

	public void setAccountProfile(AccountProfile accountProfile) {
		this.accountProfile = accountProfile;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
}
