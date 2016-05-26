package vvsvintsitsky.testing.dataaccess.filters;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.AccountProfile_;

public class AccountProfileFilter extends AbstractFilter<AccountProfile> {
	private Long accountProfileId;
	private String firstName;
	private String lastName;
	private boolean isFetchAccount;
	private boolean isFetchExaminations;
	private boolean isFetchResults;

	public Boolean getIsFetchAccount() {
		return isFetchAccount;
	}

	public void setIsFetchAccount(Boolean isFetchAccount) {
		this.isFetchAccount = isFetchAccount;
	}

	public Boolean getIsFetchExaminations() {
		return isFetchExaminations;
	}

	public void setIsFetchExaminations(Boolean isFetchExaminations) {
		this.isFetchExaminations = isFetchExaminations;
	}

	public Boolean getIsFetchResults() {
		return isFetchResults;
	}

	public void setIsFetchResults(Boolean isFetchResults) {
		this.isFetchResults = isFetchResults;
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

	public void setLastName(String lastNname) {
		this.lastName = lastNname;
	}

	public Long getAccountProfileId() {
		return accountProfileId;
	}

	public void setAccountProfileId(Long accountProfileId) {
		this.accountProfileId = accountProfileId;
	}

	public boolean isFetchAccount() {
		return isFetchAccount;
	}

	public void setFetchAccount(boolean isFetchAccount) {
		this.isFetchAccount = isFetchAccount;
	}

	@Override
	public Predicate getQueryPredicate(CriteriaBuilder cb, Root<AccountProfile> from) {
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if (accountProfileId != null) {
			predicateList.add(idPredicate(cb, from));
		}
		if (firstName != null) {
			predicateList.add(firstNamePredicate(cb, from));
		}
		if (lastName != null) {
			predicateList.add(lastNamePredicate(cb, from));
		}
		
		
		if (!(predicateList.isEmpty())) {
			return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
		}

		return null;
	}

	private Predicate idPredicate(CriteriaBuilder cb, Root<AccountProfile> from) {
		return cb.equal(from.get(AccountProfile_.id), getAccountProfileId());
	}

	private Predicate firstNamePredicate(CriteriaBuilder cb, Root<AccountProfile> from) {
		return cb.equal(from.get(AccountProfile_.firstName), getFirstName());
	}

	private Predicate lastNamePredicate(CriteriaBuilder cb, Root<AccountProfile> from) {
		return cb.equal(from.get(AccountProfile_.lastName), getLastName());
	}

	public void setFetching(Root<AccountProfile> from) {
		if (isFetchAccount) {
			from.fetch(AccountProfile_.account, JoinType.LEFT);
		}
		if (isFetchExaminations) {
			from.fetch(AccountProfile_.examintations, JoinType.LEFT);
		}
		if (isFetchResults) {
			from.fetch(AccountProfile_.results, JoinType.LEFT);
		}
	}
}
