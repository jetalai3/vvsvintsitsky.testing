package vvsvintsitsky.testing.webapp.page.user;

import vvsvintsitsky.testing.webapp.page.AbstractPage;
import vvsvintsitsky.testing.webapp.page.account.panel.AccountsListPanel;
import vvsvintsitsky.testing.webapp.page.user.panel.UsersListPanel;

public class UsersPage extends AbstractPage {

    public UsersPage() {
        super();
        add(new AccountsListPanel("list-panel"));
    }

}
