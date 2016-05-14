package vvsvintsitsky.testing.datamodel;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class AccountProfile extends AbstractModel {

	@MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(nullable = false, updatable = false, name = "id")
	private Account account;
	
	@Column
	private String firstName;
	
	@Column
	private String lastName;
	
	@OneToMany(mappedBy = "accountProfile", fetch = FetchType.LAZY)
    private List<Examination> examintations;

	@OneToMany(mappedBy = "accountProfile", fetch = FetchType.LAZY)
    private List<Result> results;
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Examination> getExamintations() {
		return examintations;
	}

	public void setExamintations(List<Examination> examintations) {
		this.examintations = examintations;
	}
	
}
