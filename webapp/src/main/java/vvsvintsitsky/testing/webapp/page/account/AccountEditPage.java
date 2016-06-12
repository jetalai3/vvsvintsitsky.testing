package vvsvintsitsky.testing.webapp.page.account;

import java.util.Arrays;

import javax.inject.Inject;

import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.UserRole;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.webapp.app.AuthorizedSession;
import vvsvintsitsky.testing.webapp.common.UserRoleChoiceRenderer;
import vvsvintsitsky.testing.webapp.common.mailsender.MailSender;
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
		if (accountProfile.getAccount() == null) {
			this.account = new Account();
		}
		if (accountProfile.getAccount() != null) {
			this.account = accountProfile.getAccount();
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Form<AccountProfile> formAccountProfile = new AccountProfileForm<AccountProfile>("formAccountProfile",
				new CompoundPropertyModel<AccountProfile>(accountProfile));
		add(formAccountProfile);

		Form<Account> formAccount = new AccountForm<Account>("formAccount",
				new CompoundPropertyModel<Account>(account));
		add(formAccount);
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

		formAccountProfile.add(new SubmitLink("save") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				boolean condition = account.getId() == null;

				if (condition) {
					accountService.register(account, accountProfile);
					MailSender mailSender = new MailSender();
					mailSender.setFrom("jetalai3@gmail.com");
					mailSender.setTo(account.getEmail());
					mailSender.setUserId("jetalai3");
					mailSender.setPassword("Epsilonomikron1");
					mailSender.setMessageSubject("Registration complete");
					mailSender.setMessageText("You have registered successfully!");
					mailSender.sendMail();
				} else {
					accountService.register(account, accountProfile);
				}
				setResponsePage(new AccountsPage());
			}
		});

		add(new FeedbackPanel("feedback"));
	}

	@AuthorizeAction(roles = { "ADMIN", "USER" }, action = Action.ENABLE)
	private class AccountProfileForm<T> extends Form<T> {

		public AccountProfileForm(String id, IModel<T> model) {
			super(id, model);
		}
	}

	@AuthorizeAction(roles = { "ADMIN", "USER" }, action = Action.ENABLE)
	private class AccountForm<T> extends Form<T> {

		public AccountForm(String id, IModel<T> model) {
			super(id, model);
		}
	}
}
