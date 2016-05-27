package vvsvintsitsky.testing.dataaccess.impl;

import org.springframework.stereotype.Repository;

import vvsvintsitsky.testing.dataaccess.AccountDao;
import vvsvintsitsky.testing.datamodel.Account;

@Repository
public class AccountDaoImpl extends AbstractDaoImpl<Account, Long> implements AccountDao {

	protected AccountDaoImpl() {
		super(Account.class);
	}

	
	

}
