package vvsvintsitsky.testing.webapp.page.user.panel;

import java.io.Serializable;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;


import vvsvintsitsky.testing.dataaccess.filters.AccountFilter;
import vvsvintsitsky.testing.dataaccess.filters.AccountProfileFilter;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.AccountProfile_;
import vvsvintsitsky.testing.datamodel.Account_;
import vvsvintsitsky.testing.service.AccountService;

public class UsersListPanel extends Panel {

    @Inject
    private AccountService accountService;

    public UsersListPanel(String id) {
        super(id);

        UsersDataProvider userDataProvider = new UsersDataProvider();
        DataView<AccountProfile> dataView = new DataView<AccountProfile>("rows", userDataProvider, 5) {
            @Override
            protected void populateItem(Item<AccountProfile> item) {
                AccountProfile accountProfile = item.getModelObject();
                item.add(new Label("id", accountProfile.getId()));
                item.add(new Label("fName", accountProfile.getFirstName()));
                item.add(new Label("email", accountProfile.getAccount().getEmail()));
            }
        };
        add(dataView);
        add(new PagingNavigator("paging", dataView));

        add(new OrderByBorder("sort-id", AccountProfile_.id, userDataProvider));
        add(new OrderByBorder("sort-name", AccountProfile_.firstName, userDataProvider));
        add(new OrderByBorder("sort-email", Account_.email, userDataProvider));

    }

    private class UsersDataProvider extends SortableDataProvider<AccountProfile, Serializable> {

        private AccountProfileFilter accountProfileFilter;

        public UsersDataProvider() {
            super();
            accountProfileFilter = new AccountProfileFilter();
            accountProfileFilter.setFetchAccount(true);
            setSort((Serializable) AccountProfile_.firstName, SortOrder.ASCENDING);
        }

        @Override
        public Iterator<AccountProfile> iterator(long first, long count) {
            Serializable property = getSort().getProperty();
            SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);

            accountProfileFilter.setSortProperty((SingularAttribute) property);
            accountProfileFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);

            accountProfileFilter.setLimit((int) count);
            accountProfileFilter.setOffset((int) first);
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
