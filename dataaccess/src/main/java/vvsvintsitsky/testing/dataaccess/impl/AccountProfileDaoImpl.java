package vvsvintsitsky.testing.dataaccess.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import vvsvintsitsky.testing.dataaccess.AccountProfileDao;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.AccountProfile_;
import vvsvintsitsky.testing.datamodel.Account_;

@Repository
public class AccountProfileDaoImpl extends AbstractDaoImpl<AccountProfile, Long> implements AccountProfileDao {

	protected AccountProfileDaoImpl() {
		super(AccountProfile.class);
	}

	@Override
	public AccountProfile getByEmailAndPassword(String email, String password) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<AccountProfile> cq = cb.createQuery(AccountProfile.class);
		Root<AccountProfile> from = cq.from(AccountProfile.class);
		cq.select(from);
		from.fetch(AccountProfile_.account, JoinType.LEFT);

		Predicate emailEqualsPredicate = cb.equal(from.get(AccountProfile_.account).get(Account_.email), email);
		Predicate passwordEqualsPredicate = cb.equal(from.get(AccountProfile_.account).get(Account_.password),
				password);

		cq.where(cb.and(emailEqualsPredicate, passwordEqualsPredicate));

		TypedQuery<AccountProfile> q = em.createQuery(cq);

		List<AccountProfile> list = q.getResultList();

		if (list.isEmpty()) {
			return null;
		} else if (list.size() == 1) {
			return list.get(0);
		} else {
			throw new IllegalArgumentException("More than one user found!");
		}

	}

}
