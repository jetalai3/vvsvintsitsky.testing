package vvsvintsitsky.testing.webapp.page.product;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import vvsvintsitsky.testing.datamodel.Account;

import vvsvintsitsky.testing.webapp.page.AbstractPage;

public class AccountDetailsPage extends AbstractPage {

    public AccountDetailsPage(PageParameters parameters) {
        super(parameters);
    }

    public AccountDetailsPage(Account account) {
        super();

    }

}
