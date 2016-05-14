package vvsvintsitsky.testing.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import vvsvintsitsky.testing.dataaccess.AccountDao;
import vvsvintsitsky.testing.dataaccess.filters.AccountFilter;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.Account_;

@Repository
public class AccountDaoImpl extends AbstractDaoImpl<Account, Long> implements AccountDao {

	protected AccountDaoImpl() {
		super(Account.class);
	}

	@Override
	public List<Account> find(AccountFilter filter) {

		EntityManager em = getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Account> cq = cb.createQuery(Account.class);

		Root<Account> from = cq.from(Account.class);

		// set selection
		cq.select(from);
		cq.where(getQuerryPredicate(filter, cb, from));
		// set sort params
		setSorting(filter, cq, from);

		TypedQuery<Account> q = em.createQuery(cq);

		// set paging
		setPaging(filter, q);

		// set execute query
		List<Account> allitems = q.getResultList();
		return allitems;

	}

	private void setPaging(AccountFilter filter, TypedQuery<Account> q) {
		if (filter.getOffset() != null && filter.getLimit() != null) {
			q.setFirstResult(filter.getOffset());
			q.setMaxResults(filter.getLimit());
		}
	}

	private void setSorting(AccountFilter filter, CriteriaQuery<Account> cq, Root<Account> from) {
		if (filter.getSortProperty() != null) {
			cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
		}
	}

	private Predicate getQuerryPredicate(AccountFilter filter, CriteriaBuilder cb, Root<Account> from) {
		if (allParameters(filter)) {
			return allParametersPredicate(filter, cb, from);
		}
		if (emailPasswordRoleParameters(filter)) {
			return emailPasswordRolePredicate(filter, cb, from);
		}
		if (idPasswordRoleParameters(filter)) {
			return idPasswordRolePredicate(filter, cb, from);
		}
		if (idEmailRoleParameters(filter)) {
			return idEmailRolePredicate(filter, cb, from);
		}
		if (idEmailPasswordParameters(filter)) {
			return idEmailPasswordPredicate(filter, cb, from);
		}
		if (idEmailParameters(filter)) {
			return idEmailPredicate(filter, cb, from);
		}
		if (idPasswordParameters(filter)) {
			return idPasswordPredicate(filter, cb, from);
		}
		if (idRoleParameters(filter)) {
			return idRolePredicate(filter, cb, from);
		}
		if (emailPasswordParameters(filter)) {
			return emailPasswordPredicate(filter, cb, from);
		}
		if (emailRoleParameters(filter)) {
			return emailRolePredicate(filter, cb, from);
		}
		if (passwordRoleParameters(filter)) {
			return passwordRolePredicate(filter, cb, from);
		}
		if (idParameters(filter)) {
			return idPredicate(filter, cb, from);
		}
		if (emailParameters(filter)) {
			return emailPredicate(filter, cb, from);
		}
		if (passwordParameters(filter)) {
			return passwordPredicate(filter, cb, from);
		}
		if (roleParameters(filter)) {
			return rolePredicate(filter, cb, from);
		}
		return null;
	}

	// conditions for getQuerry
	private boolean allParameters(AccountFilter filter) {
		return filter.getId() != null && filter.getEmail() != null && filter.getPassword() != null
				&& filter.getRole() != null;
	}

	private boolean emailPasswordRoleParameters(AccountFilter filter) {
		return filter.getId() == null && filter.getEmail() != null && filter.getPassword() != null
				&& filter.getRole() != null;
	}

	private boolean idPasswordRoleParameters(AccountFilter filter) {
		return filter.getId() != null && filter.getEmail() == null && filter.getPassword() != null
				&& filter.getRole() != null;
	}

	private boolean idEmailRoleParameters(AccountFilter filter) {
		return filter.getId() != null && filter.getEmail() != null && filter.getPassword() == null
				&& filter.getRole() != null;
	}

	private boolean idEmailPasswordParameters(AccountFilter filter) {
		return filter.getId() != null && filter.getEmail() != null && filter.getPassword() != null
				&& filter.getRole() == null;
	}

	private boolean idEmailParameters(AccountFilter filter) {
		return filter.getId() != null && filter.getEmail() != null && filter.getPassword() == null
				&& filter.getRole() == null;
	}

	private boolean idPasswordParameters(AccountFilter filter) {
		return filter.getId() != null && filter.getEmail() == null && filter.getPassword() != null
				&& filter.getRole() == null;
	}

	private boolean idRoleParameters(AccountFilter filter) {
		return filter.getId() != null && filter.getEmail() == null && filter.getPassword() == null
				&& filter.getRole() != null;
	}

	private boolean emailPasswordParameters(AccountFilter filter) {
		return filter.getId() == null && filter.getEmail() != null && filter.getPassword() != null
				&& filter.getRole() == null;
	}

	private boolean emailRoleParameters(AccountFilter filter) {
		return filter.getId() == null && filter.getEmail() != null && filter.getPassword() == null
				&& filter.getRole() != null;
	}

	private boolean passwordRoleParameters(AccountFilter filter) {
		return filter.getId() == null && filter.getEmail() == null && filter.getPassword() != null
				&& filter.getRole() != null;
	}

	private boolean idParameters(AccountFilter filter) {
		return filter.getId() != null && filter.getEmail() == null && filter.getPassword() == null
				&& filter.getRole() == null;
	}

	private boolean emailParameters(AccountFilter filter) {
		return filter.getId() == null && filter.getEmail() != null && filter.getPassword() == null
				&& filter.getRole() == null;
	}

	private boolean passwordParameters(AccountFilter filter) {
		return filter.getId() == null && filter.getEmail() == null && filter.getPassword() != null
				&& filter.getRole() == null;
	}

	private boolean roleParameters(AccountFilter filter) {
		return filter.getId() == null && filter.getEmail() == null && filter.getPassword() == null
				&& filter.getRole() != null;
	}

	// predicate generators
	private Predicate allParametersPredicate(AccountFilter filter, CriteriaBuilder cb, Root<Account> from) {
		return cb.and(idEmailPredicate(filter, cb, from), passwordRolePredicate(filter, cb, from));
	}

	private Predicate emailPasswordRolePredicate(AccountFilter filter, CriteriaBuilder cb, Root<Account> from) {
		return cb.and(emailPasswordPredicate(filter, cb, from), rolePredicate(filter, cb, from));
	}

	private Predicate idPasswordRolePredicate(AccountFilter filter, CriteriaBuilder cb, Root<Account> from) {
		return cb.and(idPasswordPredicate(filter, cb, from), rolePredicate(filter, cb, from));
	}

	private Predicate idEmailRolePredicate(AccountFilter filter, CriteriaBuilder cb, Root<Account> from) {
		return cb.and(idEmailPredicate(filter, cb, from), rolePredicate(filter, cb, from));
	}

	private Predicate idEmailPasswordPredicate(AccountFilter filter, CriteriaBuilder cb, Root<Account> from) {
		return cb.and(idEmailPredicate(filter, cb, from), passwordPredicate(filter, cb, from));
	}

	private Predicate idEmailPredicate(AccountFilter filter, CriteriaBuilder cb, Root<Account> from) {
		return cb.and(idPredicate(filter, cb, from), emailPredicate(filter, cb, from));
	}

	private Predicate idPasswordPredicate(AccountFilter filter, CriteriaBuilder cb, Root<Account> from) {
		return cb.and(idPredicate(filter, cb, from), passwordPredicate(filter, cb, from));
	}

	private Predicate idRolePredicate(AccountFilter filter, CriteriaBuilder cb, Root<Account> from) {
		return cb.and(idPredicate(filter, cb, from), rolePredicate(filter, cb, from));
	}

	private Predicate emailPasswordPredicate(AccountFilter filter, CriteriaBuilder cb, Root<Account> from) {
		return cb.and(emailPredicate(filter, cb, from), passwordPredicate(filter, cb, from));
	}

	private Predicate emailRolePredicate(AccountFilter filter, CriteriaBuilder cb, Root<Account> from) {
		return cb.and(emailPredicate(filter, cb, from), rolePredicate(filter, cb, from));
	}

	private Predicate passwordRolePredicate(AccountFilter filter, CriteriaBuilder cb, Root<Account> from) {
		return cb.and(passwordPredicate(filter, cb, from), rolePredicate(filter, cb, from));
	}

	private Predicate idPredicate(AccountFilter filter, CriteriaBuilder cb, Root<Account> from) {
		return cb.equal(from.get(Account_.id), filter.getId());
	}

	private Predicate emailPredicate(AccountFilter filter, CriteriaBuilder cb, Root<Account> from) {
		return cb.equal(from.get(Account_.email), filter.getEmail());
	}

	private Predicate passwordPredicate(AccountFilter filter, CriteriaBuilder cb, Root<Account> from) {
		return cb.equal(from.get(Account_.password), filter.getPassword());
	}

	private Predicate rolePredicate(AccountFilter filter, CriteriaBuilder cb, Root<Account> from) {
		return cb.equal(from.get(Account_.role), filter.getRole());
	}

}
