package vvsvintsitsky.testing.webapp.page.account.panel;

import java.io.Serializable;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import vvsvintsitsky.testing.dataaccess.filters.AccountFilter;
import vvsvintsitsky.testing.dataaccess.filters.AccountProfileFilter;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.AccountProfile_;
import vvsvintsitsky.testing.datamodel.Account_;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.webapp.page.account.AccountEditPage;
import vvsvintsitsky.testing.webapp.page.account.AccountsPage;

public class AccountsListPanel extends Panel {

	@Inject
	private AccountService accountService;

	public AccountsListPanel(String id) {
		super(id);

		AccountsDataProvider accountsDataProvider = new AccountsDataProvider();
		DataView<AccountProfile> dataView = new DataView<AccountProfile>("rows", accountsDataProvider, 5) {
			@Override
			protected void populateItem(Item<AccountProfile> item) {
				AccountProfile accountProfile = item.getModelObject();

				item.add(new Label("id", accountProfile.getId()));
				item.add(new Label("firstName", accountProfile.getFirstName()));
				item.add(new Label("lastName", accountProfile.getLastName()));

				item.add(new Label("email", accountProfile.getAccount().getEmail()));

				item.add(new Label("password", accountProfile.getAccount().getPassword()));

				item.add(new Link<Void>("edit-link") {
					@Override
					public void onClick() {
						setResponsePage(new AccountEditPage(accountProfile));
					}
				});

				item.add(new Link<Void>("delete-link") {
					@Override
					public void onClick() {
						try {
							accountService.delete(accountProfile.getId());
						} catch (PersistenceException e) {
							System.out.println("caughth persistance exception");
						}

						setResponsePage(new AccountsPage());
					}
				});

			}
		};
		add(dataView);
		add(new PagingNavigator("paging", dataView));

		add(new OrderByBorder("sort-id", AccountProfile_.id, accountsDataProvider));
		add(new OrderByBorder("sort-firstName", AccountProfile_.firstName, accountsDataProvider));
		add(new OrderByBorder("sort-lastName", AccountProfile_.lastName, accountsDataProvider));
		add(new OrderByBorder("sort-email", Account_.email, accountsDataProvider));
		add(new OrderByBorder("sort-password", Account_.password, accountsDataProvider));

	}

	private class AccountsDataProvider extends SortableDataProvider<AccountProfile, Serializable> {

		private AccountFilter accountFilter;
		private AccountProfileFilter accountProfileFilter;

		public AccountsDataProvider() {
			super();
			accountProfileFilter = new AccountProfileFilter();
			accountProfileFilter.setFetchAccount(true);
			setSort((Serializable) AccountProfile_.id, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<AccountProfile> iterator(long first, long count) {
			Serializable property = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);

			accountProfileFilter.setSortProperty((SingularAttribute) property);
			accountProfileFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);

			accountProfileFilter.setLimit((int) count);
			accountProfileFilter.setOffset((int) first);
			// accountProfileFilter.setFetchAccount(true);
			return accountService.find(accountProfileFilter).iterator();
		}

		@Override
		public long size() {
			return accountService.count(accountProfileFilter);
		}

		@Override
		public IModel<AccountProfile> model(AccountProfile object) {
			return new Model(object);
		}

	}

}
