package vvsvintsitsky.testing.webapp.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import vvsvintsitsky.testing.webapp.component.menu.MenuPanel;
import vvsvintsitsky.testing.webapp.component.menu.MenuPanelLoggedUser;
import vvsvintsitsky.testing.webapp.page.product.AccountsPage;

public abstract class AbstractPage extends WebPage {

    public AbstractPage() {
        super();
    }

    public AbstractPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        if (getPage().getClass().equals(AccountsPage.class)) {
            add(new MenuPanelLoggedUser("menu-panel"));
        } else {
            add(new MenuPanel("menu-panel"));
        }

    }

}
