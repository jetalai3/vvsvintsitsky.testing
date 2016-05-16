package vvsvintsitsky.testing.service;

import java.lang.reflect.Field;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vvsvintsitsky.testing.dataaccess.AccountProfileDao;
import vvsvintsitsky.testing.dataaccess.filters.AccountFilter;
import vvsvintsitsky.testing.dataaccess.impl.AbstractDaoImpl;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.UserRole;
import vvsvintsitsky.testing.service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:service-context-test.xml")
public class AccountServiceTest {

	@Inject
	private AccountService accountService;
	@Inject
	private UserRegistrationService userRegistrationService;
	

	@Test
    public void testRegistration() {
//        AccountProfile accountProfile = new AccountProfile();
//        Account account = new Account();
//
//        accountProfile.setFirstName("testFName");
//        accountProfile.setLastName("testLName");
//
//        account.setEmail(System.currentTimeMillis() + "mail@test.by");
//        account.setPassword("pswd");
//        account.setRole(UserRole.ADMIN);
//        accountService.register(account, accountProfile);
        userRegistrationService.userRegistaration("firstName", "lastName", "email", "password", "ADMIN");
	}
	
	@Test
	public void testFilter(){
		
		 AccountFilter filter = new AccountFilter();
		 filter.setEmail("jetalai3@gmail.com");
//		 filter.setPassword("qqq");
//		 filter.setId(3L);
//		 filter.setRole(1);
		 List<Account> accounts = accountService.find(filter);
		 for(Account account : accounts){
			 System.out.println(account.getEmail() +" " + account.getPassword());
			 
		 }
	}
}
