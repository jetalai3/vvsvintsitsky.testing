package vvsvintsitsky.testing.dataaccess.filters;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;

import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.AccountProfile_;
import vvsvintsitsky.testing.datamodel.Account_;
import vvsvintsitsky.testing.datamodel.Question_;
import vvsvintsitsky.testing.datamodel.Subject_;

public class AccountProfileFilter extends AbstractFilter<AccountProfile> {
	private Long accountProfileId;
	private String firstName;
	private String lastName;
	private boolean isFetchAccount;
	private boolean isFetchExaminations;
	private boolean isFetchResults;
	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsFetchAccount() {
		return isFetchAccount;
	}

	public void setIsFetchAccount(Boolean isFetchAccount) {
		this.isFetchAccount = isFetchAccount;
	}

	public boolean getIsFetchExaminations() {
		return isFetchExaminations;
	}

	public void setIsFetchExaminations(boolean isFetchExaminations) {
		this.isFetchExaminations = isFetchExaminations;
	}

	public boolean getIsFetchResults() {
		return isFetchResults;
	}

	public void setIsFetchResults(boolean isFetchResults) {
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
		if (email != null) {
			predicateList.add(emailPredicate(cb, from));
		}
		if (password != null) {
			predicateList.add(passwordPredicate(cb, from));
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

	private Predicate emailPredicate(CriteriaBuilder cb, Root<AccountProfile> from) {
		return cb.equal(from.get(AccountProfile_.account).get(Account_.email), getEmail());
	}
	
	private Predicate passwordPredicate(CriteriaBuilder cb, Root<AccountProfile> from) {
		return cb.equal(from.get(AccountProfile_.account).get(Account_.password), getPassword());
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

	@Override
	public void setSorting(CriteriaQuery<AccountProfile> query, Root<AccountProfile> from) {
		if (getSortProperty() == AccountProfile_.id) {
			query.orderBy(new OrderImpl(from.get(getSortProperty()), isSortOrder()));
			return;
		}
		if (getSortProperty() == AccountProfile_.firstName) {
			query.orderBy(new OrderImpl(from.get(getSortProperty()), isSortOrder()));
			return;
		}
		if (getSortProperty() == AccountProfile_.lastName) {
			query.orderBy(new OrderImpl(from.get(getSortProperty()), isSortOrder()));
			return;
		}
		if (getSortProperty() == Account_.email) {
			query.orderBy(new OrderImpl(from.get(AccountProfile_.account).get(getSortProperty()), isSortOrder()));
			return;
		}
		if (getSortProperty() == Account_.password) {
			query.orderBy(new OrderImpl(from.get(AccountProfile_.account).get(getSortProperty()), isSortOrder()));
			return;
		}
		if (getSortProperty() == Account_.role) {
			query.orderBy(new OrderImpl(from.get(AccountProfile_.account).get(getSortProperty()), isSortOrder()));
			return;
		}
		
	}
}
