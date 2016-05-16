package vvsvintsitsky.testing.dataaccess.filters;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.AccountProfile_;
import vvsvintsitsky.testing.datamodel.Account_;

public class AccountProfileFilter extends AbstractFilter<AccountProfile> {
	private Long accountProfileId;
	private String firstName;
	private String lastNname;
	private boolean isFetchAccount;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastNname() {
		return lastNname;
	}

	public void setLastNname(String lastNname) {
		this.lastNname = lastNname;
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

	public void setFetchAccounte(boolean isFetchAccount) {
		this.isFetchAccount = isFetchAccount;
	}

	@Override
	public Predicate getQueryPredicate(CriteriaBuilder cb, Root<AccountProfile> from) {
		if (allParameters()) {
			return allParametersPredicate(cb, from);
		}
		if (idFirstNameParameters()) {
			return idFirstNamePredicate(cb, from);
		}
		if (idLastNameParameters()) {
			return idLastNamePredicate(cb, from);
		}
		if (firstNameLastNameParameters()) {
			return firstNameLastNamePredicate(cb, from);
		}
		if (idParameters()) {
			return idPredicate(cb, from);
		}
		if (firstNameParameters()) {
			return firstNamePredicate(cb, from);
		}
		if (lastNameParameters()) {
			return lastNamePredicate(cb, from);
		}
		return null;
	}

	private boolean allParameters() {
		return getAccountProfileId() != null && getFirstName() != null && getLastNname() != null;
	}

	private boolean idFirstNameParameters() {
		return getAccountProfileId() != null && getFirstName() != null && getLastNname() == null;
	}

	private boolean idLastNameParameters() {
		return getAccountProfileId() != null && getFirstName() == null && getLastNname() != null;
	}

	private boolean firstNameLastNameParameters() {
		return getAccountProfileId() == null && getFirstName() != null && getLastNname() != null;
	}

	private boolean idParameters() {
		return getAccountProfileId() != null && getFirstName() == null && getLastNname() == null;
	}

	private boolean firstNameParameters() {
		return getAccountProfileId() == null && getFirstName() != null && getLastNname() == null;
	}

	private boolean lastNameParameters() {
		return getAccountProfileId() == null && getFirstName() == null && getLastNname() != null;
	}

	private Predicate idPredicate(CriteriaBuilder cb, Root<AccountProfile> from) {
		return cb.equal(from.get(AccountProfile_.id), getAccountProfileId());
	}

	private Predicate firstNamePredicate(CriteriaBuilder cb, Root<AccountProfile> from) {
		return cb.equal(from.get(AccountProfile_.firstName), getFirstName());
	}

	private Predicate lastNamePredicate(CriteriaBuilder cb, Root<AccountProfile> from) {
		return cb.equal(from.get(AccountProfile_.lastName), getLastNname());
	}

	private Predicate idFirstNamePredicate(CriteriaBuilder cb, Root<AccountProfile> from) {
		return cb.and(idPredicate(cb, from), firstNamePredicate(cb, from));
	}

	private Predicate idLastNamePredicate(CriteriaBuilder cb, Root<AccountProfile> from) {
		return cb.and(idPredicate(cb, from), lastNamePredicate(cb, from));
	}

	private Predicate firstNameLastNamePredicate(CriteriaBuilder cb, Root<AccountProfile> from) {
		return cb.and(firstNamePredicate(cb, from), lastNamePredicate(cb, from));
	}

	private Predicate allParametersPredicate(CriteriaBuilder cb, Root<AccountProfile> from) {
		return cb.and(idFirstNamePredicate(cb, from), lastNamePredicate(cb, from));
	}
}
