package vvsvintsitsky.testing.webapp.page.account;

import javax.inject.Inject;

import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.RangeValidator;

import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.webapp.page.AbstractPage;

public class AccountEditPage extends AbstractPage {

    @Inject
    private AccountService accountService;

    private Account account;

    private AccountProfile accountProfile;
    public AccountEditPage(PageParameters parameters) {
        super(parameters);
    }

    public AccountEditPage(AccountProfile accountProfile) {
        super();
        this.accountProfile = accountProfile;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        Form<AccountProfile> form = new AccountForm<AccountProfile>("form", new CompoundPropertyModel<AccountProfile>(accountProfile));
        add(form);

        TextField<String> emailField = new TextField<>("firstName");
        emailField.setRequired(true);
        form.add(emailField);

        TextField<String> passwordField = new TextField<>("lastName");
        passwordField.setRequired(true);
        form.add(passwordField);

        
        

        form.add(new SubmitLink("save") {
            @Override
            public void onSubmit() {
                super.onSubmit();
                accountService.saveOrUpdate(accountProfile);
                setResponsePage(new AccountsPage());
            }
        });

        add(new FeedbackPanel("feedback"));
    }

    @AuthorizeAction(roles = { "admin" }, action = Action.ENABLE)
    private class AccountForm<T> extends Form<T> {

        public AccountForm(String id, IModel<T> model) {
            super(id, model);
        }
    }
}
