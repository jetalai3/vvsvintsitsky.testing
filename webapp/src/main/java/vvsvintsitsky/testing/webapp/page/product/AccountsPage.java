package vvsvintsitsky.testing.webapp.page.product;

import javax.inject.Inject;

import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.webapp.page.AbstractPage;

public class AccountsPage extends AbstractPage {

    @Inject
    private AccountService accountService;

    public AccountsPage() {
        super();

        System.out.print(accountService);
    }

}
