//package vvsvintsitsky.testing.service;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.inject.Inject;
//import javax.persistence.PersistenceException;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import vvsvintsitsky.testing.dataaccess.filters.AccountFilter;
//import vvsvintsitsky.testing.dataaccess.filters.AccountProfileFilter;
//import vvsvintsitsky.testing.datamodel.Account;
//import vvsvintsitsky.testing.datamodel.AccountProfile;
//import vvsvintsitsky.testing.datamodel.AccountProfile_;
//import vvsvintsitsky.testing.datamodel.Examination;
//import vvsvintsitsky.testing.datamodel.Result;
//import vvsvintsitsky.testing.datamodel.Subject;
//import vvsvintsitsky.testing.datamodel.UserRole;
//import vvsvintsitsky.testing.service.AccountService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:service-context-test.xml")
//public class AccountServiceTest {
//
//	@Inject
//	private AccountService accountService;
//
//	@Inject
//	private ExaminationService examinationService;
//	
//	@Inject
//	private ResultService resultService;
//	
//	@Test
//	public void testRegistration() {
//		clearDataTables();
//
//		AccountProfile adminProfile = new AccountProfile();
//		Account adminAccount = new Account();
//
//		adminProfile.setFirstName("admin");
//		adminProfile.setLastName("admin");
//
//		adminAccount.setEmail("admin");
//		adminAccount.setPassword("admin");
//		adminAccount.setRole(UserRole.ADMIN);
//		accountService.register(adminAccount, adminProfile);
//
//		AccountProfile userProfile = new AccountProfile();
//		Account userAccount = new Account();
//
//		userProfile.setFirstName("user");
//		userProfile.setLastName("user");
//
//		userAccount.setEmail("user");
//		userAccount.setPassword("user");
//		userAccount.setRole(UserRole.USER);
//		accountService.register(userAccount, userProfile);
//
//		clearDataTables();
//
//	}
//
//	@Test
//	public void searchProfiles() {
//		clearDataTables();
//
//		List<AccountProfile> profiles = fillDatabaseWithAccountsAndAccountProfiles(20);
//
//		AccountProfileFilter filter = new AccountProfileFilter();
//		AccountProfile profile = profiles.get(profiles.size() - 1);
//
//		filter.setFirstName(profile.getFirstName());
//		filter.setLastName(profile.getLastName());
//		filter.setEmail(profile.getAccount().getEmail());
//		filter.setRole(profile.getAccount().getRole());
//		filter.setFetchAccount(true);
//		filter.setSortProperty(AccountProfile_.id);
//		filter.setSortOrder(false);
//		filter.setLimit(30);
//
//		if (accountService.find(filter).size() != 1) {
//			throw new IllegalStateException("more than 1 user found");
//		}
//
//		clearDataTables();
//	}
//
//	@Test
//	public void searchAccount() {
//		clearDataTables();
//
//		List<AccountProfile> profiles = fillDatabaseWithAccountsAndAccountProfiles(20);
//
//		AccountFilter filter = new AccountFilter();
//		Account account = profiles.get(profiles.size() - 1).getAccount();
//
//		filter.setEmail(account.getEmail());
//		filter.setPassword(account.getPassword());
//		filter.setRole(account.getRole());
//
//		List<Account> result = accountService.find(filter);
//
//		if (result.size() != 1) {
//			throw new IllegalStateException("more than 1 user found");
//		}
//
//		clearDataTables();
//	}
//
//	@Test
//	public void updateProfile() {
//		clearDataTables();
//
//		List<AccountProfile> profiles = fillDatabaseWithAccountsAndAccountProfiles(20);
//
//		AccountProfile profile = profiles.get(profiles.size() - 1);
//		AccountProfile updProfile = profiles.get(profiles.size() - 2);
//
//		updProfile.setFirstName(profile.getFirstName());
//		updProfile.setLastName(profile.getLastName());
//
//		try {
//			accountService.update(updProfile);
//		} catch (PersistenceException e) {
//			System.out.println("failed to update profile, reason: " + e.getCause().getCause().getMessage());
//		}
//
//		updProfile.setAccount(profile.getAccount());
//
//		try {
//			accountService.update(updProfile);
//		} catch (PersistenceException e) {
//			System.out.println("failed to update profile, reason: " + e.getCause().getCause().getMessage());
//		}
//
//		clearDataTables();
//	}
//
//	@Test
//	public void updateAccount() {
//		clearDataTables();
//
//		List<AccountProfile> profiles = fillDatabaseWithAccountsAndAccountProfiles(20);
//
//		Account account = profiles.get(profiles.size() - 1).getAccount();
//		Account updAccount = profiles.get(profiles.size() - 2).getAccount();
//
//		updAccount.setPassword(account.getPassword());
//		updAccount.setRole(account.getRole());
//
//		try {
//			accountService.update(updAccount);
//		} catch (PersistenceException e) {
//			System.out.println("failed to update account, reason: " + e.getCause().getCause().getMessage());
//		}
//
//		updAccount.setEmail(account.getEmail());
//
//		try {
//			accountService.update(updAccount);
//		} catch (PersistenceException e) {
//			System.out.println("failed to update profile, reason: " + e.getCause().getCause().getMessage());
//		}
//
//		clearDataTables();
//	}
//
//	@Test
//	public void delete() {
//		clearDataTables();
//
//		List<AccountProfile> profiles = fillDatabaseWithAccountsAndAccountProfiles(20);
//
//		AccountProfile profile = profiles.get(0);
//
//		try {
//			accountService.delete(profile.getId());
//		} catch (PersistenceException e) {
//			System.out.println("failed to delete profile, reason: " + e.getCause().getCause().getMessage());
//		}
//
//		profile = profiles.get(1);
//		Examination examination = createExamination(profile);
//		createResult(examination, profile);
//
//		try {
//			accountService.delete(profile.getId());
//		} catch (PersistenceException e) {
//			System.out.println("failed to delete profile, reason: " + e.getCause().getCause().getMessage());
//		}
//
//		clearDataTables();
//	}
//
//	@Test
//	public void findByEmailAndPassword() {
//		clearDataTables();
//
//		List<AccountProfile> profiles = fillDatabaseWithAccountsAndAccountProfiles(20);
//
//		Account account = profiles.get(0).getAccount();
//		AccountProfile profile = accountService.getByEmailAndPassword(account.getEmail(), account.getPassword());
//		if (profile == null) {
//			throw new IllegalStateException("no profile found");
//		}
//
//		clearDataTables();
//	}
//
//	@Test
//	public void countProfiles() {
//		clearDataTables();
//
//		List<AccountProfile> profiles = fillDatabaseWithAccountsAndAccountProfiles(20);
//
//		AccountProfileFilter accountProfileFilter = new AccountProfileFilter();
//
//		if (profiles.size() != accountService.count(accountProfileFilter)) {
//			throw new IllegalStateException("not all users found");
//		}
//
//		clearDataTables();
//	}
//
//	private List<AccountProfile> fillDatabaseWithAccountsAndAccountProfiles(int quantity) {
//		List<AccountProfile> profiles = new ArrayList<AccountProfile>();
//		Account account;
//		AccountProfile profile;
//
//		for (int i = 0; i < quantity; i++) {
//			account = new Account();
//			account.setEmail("email" + i);
//			account.setPassword("password" + i);
//			account.setRole(UserRole.ADMIN);
//
//			profile = new AccountProfile();
//			profile.setFirstName("firstName" + i);
//			profile.setLastName("lastName" + i);
//
//			accountService.register(account, profile);
//			profiles.add(profile);
//		}
//
//		return profiles;
//	}
//
//	private Examination createExamination(AccountProfile accountProfile) {
//		Examination examination = new Examination();
//		examination.setName("name");
//		examination.setBeginDate(new Date());
//		examination.setEndDate(new Date());
//		examination.setSubject(new Subject());
//		examination.setAccountProfile(accountProfile);
//		return examination;
//	}
//
//	private Result createResult(Examination examination, AccountProfile accountProfile) {
//		Result result = new Result();
//		result.setPoints(100);
//		result.setExamination(examination);
//		result.setAccountProfile(accountProfile);
//		return result;
//	}
//
//	private void clearDataTables() {
//		resultService.deleteAll();
//		examinationService.deleteAll();
//		accountService.deleteAll();
//		
//	}
//}
