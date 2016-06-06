package vvsvintsitsky.testing.dataaccess.filters;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.Account_;

public class AccountFilter extends AbstractFilter<Account> {

	private Long id;
	private String password;
	private String email;
	private Integer role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	@Override
	public Predicate getQueryPredicate(CriteriaBuilder cb, Root<Account> from) {
		List<Predicate> predicateList = new ArrayList<Predicate>();
		if (id != null) {
			predicateList.add(idPredicate(cb, from));
		}
		if (password != null) {
			predicateList.add(passwordPredicate(cb, from));
		}
		if (email != null) {
			predicateList.add(emailPredicate(cb, from));
		}
		if (role != null) {
			predicateList.add(rolePredicate(cb, from));
		}
		if (!(predicateList.isEmpty())) {
			return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
		}
		return null;
	}

	private Predicate idPredicate(CriteriaBuilder cb, Root<Account> from) {
		return cb.equal(from.get(Account_.id), getId());
	}

	private Predicate emailPredicate(CriteriaBuilder cb, Root<Account> from) {
		return cb.equal(from.get(Account_.email), getEmail());
	}

	private Predicate passwordPredicate(CriteriaBuilder cb, Root<Account> from) {
		return cb.equal(from.get(Account_.password), getPassword());
	}

	private Predicate rolePredicate(CriteriaBuilder cb, Root<Account> from) {
		return cb.equal(from.get(Account_.role), getRole());
	}

	@Override
	public void setFetching(Root<Account> from) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSorting(CriteriaQuery<Account> query, Root<Account> from) {
		// TODO Auto-generated method stub
		
	}

}
