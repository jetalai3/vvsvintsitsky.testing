package vvsvintsitsky.testing.dataaccess.filters;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.Account_;

public class AccountFilter extends AbstractFilter<Account> {

	private Long Id;
	private String password;
	private String email;
	private Integer role;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
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
		if (allParameters()) {
			return allParametersPredicate(cb, from);
		}
		if (emailPasswordRoleParameters()) {
			return emailPasswordRolePredicate(cb, from);
		}
		if (idPasswordRoleParameters()) {
			return idPasswordRolePredicate(cb, from);
		}
		if (idEmailRoleParameters()) {
			return idEmailRolePredicate(cb, from);
		}
		if (idEmailPasswordParameters()) {
			return idEmailPasswordPredicate(cb, from);
		}
		if (idEmailParameters()) {
			return idEmailPredicate(cb, from);
		}
		if (idPasswordParameters()) {
			return idPasswordPredicate(cb, from);
		}
		if (idRoleParameters()) {
			return idRolePredicate(cb, from);
		}
		if (emailPasswordParameters()) {
			return emailPasswordPredicate(cb, from);
		}
		if (emailRoleParameters()) {
			return emailRolePredicate(cb, from);
		}
		if (passwordRoleParameters()) {
			return passwordRolePredicate(cb, from);
		}
		if (idParameters()) {
			return idPredicate(cb, from);
		}
		if (emailParameters()) {
			return emailPredicate(cb, from);
		}
		if (passwordParameters()) {
			return passwordPredicate(cb, from);
		}
		if (roleParameters()) {
			return rolePredicate(cb, from);
		}
		return null;
	}

	private boolean allParameters() {
		return getId() != null && getEmail() != null && getPassword() != null && getRole() != null;
	}

	private boolean emailPasswordRoleParameters() {
		return getId() == null && getEmail() != null && getPassword() != null && getRole() != null;
	}

	private boolean idPasswordRoleParameters() {
		return getId() != null && getEmail() == null && getPassword() != null && getRole() != null;
	}

	private boolean idEmailRoleParameters() {
		return getId() != null && getEmail() != null && getPassword() == null && getRole() != null;
	}

	private boolean idEmailPasswordParameters() {
		return getId() != null && getEmail() != null && getPassword() != null && getRole() == null;
	}

	private boolean idEmailParameters() {
		return getId() != null && getEmail() != null && getPassword() == null && getRole() == null;
	}

	private boolean idPasswordParameters() {
		return getId() != null && getEmail() == null && getPassword() != null && getRole() == null;
	}

	private boolean idRoleParameters() {
		return getId() != null && getEmail() == null && getPassword() == null && getRole() != null;
	}

	private boolean emailPasswordParameters() {
		return getId() == null && getEmail() != null && getPassword() != null && getRole() == null;
	}

	private boolean emailRoleParameters() {
		return getId() == null && getEmail() != null && getPassword() == null && getRole() != null;
	}

	private boolean passwordRoleParameters() {
		return getId() == null && getEmail() == null && getPassword() != null && getRole() != null;
	}

	private boolean idParameters() {
		return getId() != null && getEmail() == null && getPassword() == null && getRole() == null;
	}

	private boolean emailParameters() {
		return getId() == null && getEmail() != null && getPassword() == null && getRole() == null;
	}

	private boolean passwordParameters() {
		return getId() == null && getEmail() == null && getPassword() != null && getRole() == null;
	}

	private boolean roleParameters() {
		return getId() == null && getEmail() == null && getPassword() == null && getRole() != null;
	}

	// predicate generators
	private Predicate allParametersPredicate(CriteriaBuilder cb, Root<Account> from) {
		return cb.and(idEmailPredicate(cb, from), passwordRolePredicate(cb, from));
	}

	private Predicate emailPasswordRolePredicate(CriteriaBuilder cb, Root<Account> from) {
		return cb.and(emailPasswordPredicate(cb, from), rolePredicate(cb, from));
	}

	private Predicate idPasswordRolePredicate(CriteriaBuilder cb, Root<Account> from) {
		return cb.and(idPasswordPredicate(cb, from), rolePredicate(cb, from));
	}

	private Predicate idEmailRolePredicate(CriteriaBuilder cb, Root<Account> from) {
		return cb.and(idEmailPredicate(cb, from), rolePredicate(cb, from));
	}

	private Predicate idEmailPasswordPredicate(CriteriaBuilder cb, Root<Account> from) {
		return cb.and(idEmailPredicate(cb, from), passwordPredicate(cb, from));
	}

	private Predicate idEmailPredicate(CriteriaBuilder cb, Root<Account> from) {
		return cb.and(idPredicate(cb, from), emailPredicate(cb, from));
	}

	private Predicate idPasswordPredicate(CriteriaBuilder cb, Root<Account> from) {
		return cb.and(idPredicate(cb, from), passwordPredicate(cb, from));
	}

	private Predicate idRolePredicate(CriteriaBuilder cb, Root<Account> from) {
		return cb.and(idPredicate(cb, from), rolePredicate(cb, from));
	}

	private Predicate emailPasswordPredicate(CriteriaBuilder cb, Root<Account> from) {
		return cb.and(emailPredicate(cb, from), passwordPredicate(cb, from));
	}

	private Predicate emailRolePredicate(CriteriaBuilder cb, Root<Account> from) {
		return cb.and(emailPredicate(cb, from), rolePredicate(cb, from));
	}

	private Predicate passwordRolePredicate(CriteriaBuilder cb, Root<Account> from) {
		return cb.and(passwordPredicate(cb, from), rolePredicate(cb, from));
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

}
