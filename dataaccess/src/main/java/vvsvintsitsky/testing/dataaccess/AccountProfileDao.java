package vvsvintsitsky.testing.dataaccess;

import vvsvintsitsky.testing.datamodel.AccountProfile;

public interface AccountProfileDao extends AbstractDao<AccountProfile, Long> {

	AccountProfile getByEmailAndPassword(String email, String password);
}
