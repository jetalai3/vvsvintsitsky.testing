package vvsvintsitsky.testing.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import vvsvintsitsky.testing.dataaccess.AccountDao;
import vvsvintsitsky.testing.dataaccess.AccountProfileDao;
import vvsvintsitsky.testing.dataaccess.filters.AccountFilter;
import vvsvintsitsky.testing.dataaccess.filters.AccountProfileFilter;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Inject
	private AccountDao accountDao;

	@Inject
	private AccountProfileDao accountProfileDao;

	@Override
	public void register(Account account, AccountProfile accountProfile) {
		accountDao.insert(account);

		accountProfile.setAccount(account);
		accountProfileDao.insert(accountProfile);

	}

	@Override
	public Account getAccount(Long id) {
		return accountDao.get(id);
	}

	@Override
	public AccountProfile getAccountProfile(Long id) {
		return accountProfileDao.get(id);
	}

	@Override
	public void update(AccountProfile accountProfile) {
		accountProfileDao.update(accountProfile);
	}

	@Override
	public void delete(Long id) {
		accountProfileDao.delete(id);
		accountDao.delete(id);
	}

	@Override
	public List<AccountProfile> getAll() {
		return accountProfileDao.getAll();
	}

	@Override
	public List<AccountProfile> find(AccountProfileFilter filter) {
		return accountProfileDao.find(filter);
	}
	
	@Override
	public List<Account> find(AccountFilter filter) {
		return accountDao.find(filter);
	}

	@Override
	public long count(AccountProfileFilter accountProfileFilter) {
		return accountProfileDao.count(accountProfileFilter);
	}

	@Override
	public void saveOrUpdate(AccountProfile accountProfile) {
		if (accountProfile.getId() != null) {
			accountProfileDao.update(accountProfile);
		} else {
			accountProfileDao.insert(accountProfile);
		}
	}
	
	@Override
	public void saveOrUpdate(Account account){
		if (account.getId() != null) {
			accountDao.update(account);
		} else {
			accountDao.insert(account);
		}
	}

	@Override
	public Collection<? extends String> resolveRoles(Long id) {
		Account account = accountDao.get(id);
        return Collections.singletonList(account.getRole().name());
	}

	
}
