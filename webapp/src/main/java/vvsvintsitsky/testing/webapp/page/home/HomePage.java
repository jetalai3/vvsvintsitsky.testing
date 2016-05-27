package vvsvintsitsky.testing.webapp.page.home;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

import vvsvintsitsky.testing.webapp.page.AbstractPage;
@AuthorizeInstantiation(value = { "ADMIN" })
public class HomePage extends AbstractPage {

    public HomePage() {
        super();

    }

}
