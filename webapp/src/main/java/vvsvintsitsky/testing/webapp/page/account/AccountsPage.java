package vvsvintsitsky.testing.webapp.page.account;

import javax.inject.Inject;

import org.apache.wicket.markup.html.link.Link;

import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.webapp.page.AbstractPage;
import vvsvintsitsky.testing.webapp.page.account.panel.AccountsListPanel;

public class AccountsPage extends AbstractPage {

    @Inject
    private AccountService accountService;

    public AccountsPage() {
        super();
        add(new AccountsListPanel("list-panel"));

        add(new Link("create") {
            @Override
            public void onClick() {
                setResponsePage(new AccountEditPage(new AccountProfile()));
            }
        });
    }
}
