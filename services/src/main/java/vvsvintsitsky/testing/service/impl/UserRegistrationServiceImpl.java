package vvsvintsitsky.testing.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import vvsvintsitsky.testing.dataaccess.AccountDao;
import vvsvintsitsky.testing.dataaccess.AccountProfileDao;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.UserRole;
import vvsvintsitsky.testing.service.UserRegistrationService;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
	@Inject
	private AccountDao accountDao;
	@Inject
	private AccountProfileDao accountProfileDao;

	@Override
	public boolean userRegistaration(String firstName, String lastName, String email, String password, String role) {
		AccountProfile accountProfile = new AccountProfile();
		Account account = new Account();
		accountProfile.setFirstName(firstName);
		accountProfile.setLastName(lastName);
		account.setEmail(email);
		account.setPassword(password);
		account.setRole(UserRole.valueOf(role));
		accountDao.insert(account);
		accountProfileDao.insert(accountProfile);
		return true;
	}

}
