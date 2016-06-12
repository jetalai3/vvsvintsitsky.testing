package vvsvintsitsky.testing.service;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import vvsvintsitsky.testing.dataaccess.filters.AccountFilter;
import vvsvintsitsky.testing.dataaccess.filters.AccountProfileFilter;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;

public interface AccountService {

	@Transactional
    void register(Account account, AccountProfile accountProfile);

	Account getAccount(Long id);

	AccountProfile getAccountProfile(Long id);
	
	AccountProfile getByEmailAndPassword(String email, String password);
	
    @Transactional
    void update(AccountProfile accountProfile);

    @Transactional
    void delete(Long id);

    List<AccountProfile> getAll();
    
    List<AccountProfile> find(AccountProfileFilter filter);

	long count(AccountProfileFilter accountProfileFilter);
	@Transactional
	void saveOrUpdate(AccountProfile accountProfile);
	
	@Transactional
	void saveOrUpdate(Account account);

	Collection<? extends String> resolveRoles(Long id);

	List<Account> find(AccountFilter filter);

	@Transactional
	void deleteAll();

	@Transactional
	void update(Account account);
}
