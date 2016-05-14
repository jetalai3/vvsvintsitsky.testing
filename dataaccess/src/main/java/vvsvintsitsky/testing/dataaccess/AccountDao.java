package vvsvintsitsky.testing.dataaccess;

import java.util.List;

import vvsvintsitsky.testing.dataaccess.filters.AccountFilter;
import vvsvintsitsky.testing.datamodel.Account;

public interface AccountDao extends AbstractDao<Account, Long> {
	List<Account> find(AccountFilter filter);
}
