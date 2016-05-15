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

import vvsvintsitsky.testing.dataaccess.AccountProfileDao;
import vvsvintsitsky.testing.dataaccess.filters.AccountFilter;
import vvsvintsitsky.testing.dataaccess.filters.AccountProfileFilter;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.AccountProfile_;
import vvsvintsitsky.testing.datamodel.Account_;

@Repository
public class AccountProfileDaoImpl extends AbstractDaoImpl<AccountProfile, Long> implements AccountProfileDao {

	protected AccountProfileDaoImpl() {
		super(AccountProfile.class);
	}

	@Override
	public List<AccountProfile> find(AccountProfileFilter filter) {

		EntityManager em = getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<AccountProfile> cq = cb.createQuery(AccountProfile.class);

		Root<AccountProfile> from = cq.from(AccountProfile.class);

		// set selection
		cq.select(from);
		Predicate pr = cb.equal(from.get(AccountProfile_.account).get(Account_.id), filter.getAccountProfileId());
		 cq.where(getQuerryPredicate(filter, cb, from));
		 // set sort params
		 setSorting(filter, cq, from);
		
		 TypedQuery<AccountProfile> q = em.createQuery(cq);
		
		 // set paging
		 setPaging(filter, q);
		
		 // set execute query
		 List<AccountProfile> allitems = q.getResultList();
		 return allitems;
	}
	private void setPaging(AccountProfileFilter filter, TypedQuery<AccountProfile> q) {
		if (filter.getOffset() != null && filter.getLimit() != null) {
			q.setFirstResult(filter.getOffset());
			q.setMaxResults(filter.getLimit());
		}
	}

	private void setSorting(AccountProfileFilter filter, CriteriaQuery<AccountProfile> cq, Root<AccountProfile> from) {
		if (filter.getSortProperty() != null) {
			cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
		}
	}

	private Predicate getQuerryPredicate(AccountProfileFilter filter, CriteriaBuilder cb, Root<AccountProfile> from){
		
		return null;
	}
	
}
