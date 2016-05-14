package vvsvintsitsky.testing.service;

import java.util.List;

import javax.transaction.Transactional;

import vvsvintsitsky.testing.dataaccess.filters.AccountFilter;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;

public interface AccountService {

	@Transactional
    void register(Account account, AccountProfile accountProfile);

	Account getAccount(Long id);

	AccountProfile getAccountProfile(Long id);
	
    @Transactional
    void update(AccountProfile accountProfile);

    @Transactional
    void delete(Long id);

    List<AccountProfile> getAll();
    
    List<Account> find(AccountFilter filter);
}
