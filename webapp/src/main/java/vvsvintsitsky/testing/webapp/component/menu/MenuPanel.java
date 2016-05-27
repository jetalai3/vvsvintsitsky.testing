package vvsvintsitsky.testing.webapp.component.menu;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import vvsvintsitsky.testing.webapp.app.AuthorizedSession;
import vvsvintsitsky.testing.webapp.page.account.AccountsPage;
import vvsvintsitsky.testing.webapp.page.home.HomePage;
import vvsvintsitsky.testing.webapp.page.login.LoginPage;

public class MenuPanel extends Panel {

    public MenuPanel(String id) {
        super(id);
        // setRenderBodyOnly(true);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Link("link-home") {
            @Override
            public void onClick() {
                setResponsePage(new HomePage());
            }
        });

        add(new Link("link-accounts") {
            @Override
            public void onClick() {
                setResponsePage(new AccountsPage());
            }
        });
        Link link = new Link("link-logout") {
            @Override
            public void onClick() {
                getSession().invalidate();
                setResponsePage(LoginPage.class);
            }
        };
        link.setVisible(AuthorizedSession.get().isSignedIn());
        add(link);
    }
}
