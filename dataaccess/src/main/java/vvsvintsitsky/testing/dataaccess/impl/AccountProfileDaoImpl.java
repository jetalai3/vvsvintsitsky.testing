package vvsvintsitsky.testing.dataaccess.impl;

import org.springframework.stereotype.Repository;

import vvsvintsitsky.testing.dataaccess.AccountProfileDao;
import vvsvintsitsky.testing.datamodel.AccountProfile;

@Repository
public class AccountProfileDaoImpl extends AbstractDaoImpl<AccountProfile, Long> implements AccountProfileDao {

	protected AccountProfileDaoImpl() {
		super(AccountProfile.class);
	}

	
	
}
