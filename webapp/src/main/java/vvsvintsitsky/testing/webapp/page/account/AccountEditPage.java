package vvsvintsitsky.testing.webapp.page.account;

import java.util.Arrays;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.RangeValidator;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;

import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.UserRole;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.webapp.app.AuthorizedSession;
import vvsvintsitsky.testing.webapp.common.UserRoleChoiceRenderer;
import vvsvintsitsky.testing.webapp.page.AbstractPage;
import vvsvintsitsky.testing.webapp.page.home.HomePage;

public class AccountEditPage extends AbstractPage {

	@Inject
	private AccountService accountService;

	private Account account;

	private AccountProfile accountProfile;

	private Logger logger;

	public AccountEditPage(PageParameters parameters) {
		super(parameters);
		this.logger = org.slf4j.LoggerFactory.getLogger(AccountEditPage.class);
	}

	public AccountEditPage(AccountProfile accountProfile) {
		super();
		this.accountProfile = accountProfile;
		if (accountProfile.getAccount() == null) {
			this.account = new Account();
		}
		if (accountProfile.getAccount() != null) {
			this.account = accountProfile.getAccount();
		}
		this.logger = org.slf4j.LoggerFactory.getLogger(AccountEditPage.class);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		WebMarkupContainer rowsContainer = new WebMarkupContainer("rowsContainer");
		rowsContainer.setOutputMarkupId(true);
		add(rowsContainer);
		
		Form<AccountProfile> formAccountProfile = new AccountProfileForm<AccountProfile>("formAccountProfile",
				new CompoundPropertyModel<AccountProfile>(accountProfile));
		rowsContainer.add(formAccountProfile);

		Form<Account> formAccount = new AccountForm<Account>("formAccount",
				new CompoundPropertyModel<Account>(account));
		rowsContainer.add(formAccount);
		formAccountProfile.add(formAccount);
		TextField<String> firstNameField = new TextField<>("firstName");
		firstNameField.setRequired(true);
		formAccountProfile.add(firstNameField);

		TextField<String> lastNameField = new TextField<>("lastName");
		lastNameField.setRequired(true);
		formAccountProfile.add(lastNameField);

		TextField<String> emailField = new TextField<>("email");
		emailField.setRequired(true);
		formAccount.add(emailField);

		TextField<String> passwordField = new TextField<>("password");
		passwordField.setRequired(true);
		formAccount.add(passwordField);

		DropDownChoice<UserRole> roleField = new DropDownChoice<>("role", Arrays.asList(UserRole.values()),
				UserRoleChoiceRenderer.INSTANCE);
		roleField.setRequired(true);
		roleField.setVisible(AuthorizedSession.get().isSignedIn()
				&& AuthorizedSession.get().getLoggedUser().getAccount().getRole().equals(UserRole.ADMIN));

		formAccount.add(roleField);

		rowsContainer.add(new SubmitLink("save") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				logger.warn("Account registration/edit attempt");
				try {
					if (account.getId() == null) {
						account.setRole(UserRole.USER);
						setResponsePage(new HomePage());
						accountService.register(account, accountProfile);
					} else {
						accountService.saveOrUpdate(account);
						accountProfile.setAccount(account);
						accountService.saveOrUpdate(accountProfile);
					}
				} catch (PersistenceException e) {
					// logger.error("User {} failed to submit
					// registration/edit",
					// AuthorizedSession.get().getLoggedUser().getId());
				}

			}
		});

		rowsContainer.add(new FeedbackPanel("feedback"));
	}

	// @AuthorizeAction(roles = { "ADMIN", "USER", "GUEST" }, action =
	// Action.ENABLE)
	private class AccountProfileForm<T> extends Form<T> {

		public AccountProfileForm(String id, IModel<T> model) {
			super(id, model);
		}
	}

	// @AuthorizeAction(roles = { "ADMIN", "USER", "GUEST" }, action =
	// Action.ENABLE)
	private class AccountForm<T> extends Form<T> {

		public AccountForm(String id, IModel<T> model) {
			super(id, model);
		}
	}
}
