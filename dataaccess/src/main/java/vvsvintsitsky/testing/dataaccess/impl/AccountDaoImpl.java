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

	        if(filter.getId() != null) {
	        	Predicate idEqualCondition = cb.equal(from.get(Account_.id), filter.getId());
	        	cq.where(idEqualCondition);
	        }
	        if(filter.getPassword() != null){
	        	Predicate passwordEqualCondition = cb.equal(from.get(Account_.password), filter.getPassword());
	        	cq.where(passwordEqualCondition);
	        }
	        if(filter.getEmail() != null){
	        	Predicate emailEqualCondition = cb.equal(from.get(Account_.email), filter.getEmail());
	        	cq.where(emailEqualCondition);
	        }
	        if(filter.getRole() != null){
	        	Predicate roleEqualCondition = cb.equal(from.get(Account_.role), filter.getRole());
	        	cq.where(roleEqualCondition);
	        }
	        
	        // set sort params
	        if (filter.getSortProperty() != null) {
	            cq.orderBy(new OrderImpl(from.get(filter.getSortProperty()), filter.isSortOrder()));
	        }

	        TypedQuery<Account> q = em.createQuery(cq);

	        // set paging
	        if (filter.getOffset() != null && filter.getLimit() != null) {
	            q.setFirstResult(filter.getOffset());
	            q.setMaxResults(filter.getLimit());
	        }

	        // set execute query
	        List<Account> allitems = q.getResultList();
	        return allitems;
		
	}
   
}
