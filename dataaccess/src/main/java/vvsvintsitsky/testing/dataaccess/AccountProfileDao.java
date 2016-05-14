package vvsvintsitsky.testing.dataaccess;

import java.util.List;

import vvsvintsitsky.testing.dataaccess.filters.AccountProfileFilter;
import vvsvintsitsky.testing.datamodel.AccountProfile;

public interface AccountProfileDao extends AbstractDao<AccountProfile, Long> {

	List<AccountProfile> find(AccountProfileFilter accountProfileFilter);
}
