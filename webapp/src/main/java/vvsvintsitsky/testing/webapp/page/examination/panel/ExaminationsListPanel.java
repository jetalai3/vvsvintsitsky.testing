package vvsvintsitsky.testing.webapp.page.examination.panel;

import java.io.Serializable;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
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
import vvsvintsitsky.testing.dataaccess.filters.ExaminationFilter;
import vvsvintsitsky.testing.dataaccess.filters.QuestionFilter;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.AccountProfile_;
import vvsvintsitsky.testing.datamodel.Account_;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Examination_;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Question_;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.service.ExaminationService;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.webapp.page.account.AccountEditPage;
import vvsvintsitsky.testing.webapp.page.account.AccountsPage;
import vvsvintsitsky.testing.webapp.page.examination.ExaminationEditPage;
import vvsvintsitsky.testing.webapp.page.examination.ExaminationsPage;
import vvsvintsitsky.testing.webapp.page.question.QuestionEditPage;

public class ExaminationsListPanel extends Panel {

	@Inject
	private ExaminationService examinationService;

	public ExaminationsListPanel(String id) {
		super(id);

		ExaminationsDataProvider examinationsDataProvider = new ExaminationsDataProvider();
		DataView<Examination> dataView = new DataView<Examination>("rows", examinationsDataProvider, 5) {
			@Override
			protected void populateItem(Item<Examination> item) {
				Examination examination = item.getModelObject();

				item.add(new Label("id", examination.getId()));
				item.add(new Label("name", examination.getName()));
				item.add(DateLabel.forDatePattern("begin-date", Model.of(examination.getBeginDate()), "dd-MM-yyyy"));
				item.add(DateLabel.forDatePattern("end-date", Model.of(examination.getEndDate()), "dd-MM-yyyy"));
				item.add(new Label("subject", examination.getSubject().getName()));
				item.add(new Label("account-id", examination.getAccountProfile().getId()));

				item.add(new Link<Void>("edit-link") {
					@Override
					public void onClick() {
						setResponsePage(new ExaminationEditPage(examination));
					}
				});

				item.add(new Link<Void>("delete-link") {
					@Override
					public void onClick() {
						try {
							examinationService.delete(examination.getId());
						} catch (PersistenceException e) {
							System.out.println("caughth persistance exception");
						}

						setResponsePage(new ExaminationsPage());
					}
				});

			}
		};
		add(dataView);
		add(new PagingNavigator("paging", dataView));

		add(new OrderByBorder("sort-id", Examination_.id, examinationsDataProvider));
		add(new OrderByBorder("sort-name", Examination_.name, examinationsDataProvider));
		add(new OrderByBorder("sort-begin-date", Examination_.beginDate, examinationsDataProvider));
		add(new OrderByBorder("sort-end-date", Examination_.endDate, examinationsDataProvider));
		add(new OrderByBorder("sort-subject", Examination_.subject.getName(), examinationsDataProvider));
		add(new OrderByBorder("sort-account-id", AccountProfile_.id, examinationsDataProvider));

	}

	private class ExaminationsDataProvider extends SortableDataProvider<Examination, Serializable> {

		private ExaminationFilter examinationFilter;
		

		public ExaminationsDataProvider() {
			super();
			examinationFilter = new ExaminationFilter();
			examinationFilter.setIsfetchSubject(true);
			examinationFilter.setIsFetchAccountProfile(true);
			examinationFilter.setIsFetchQuestions(true);
			setSort((Serializable) Examination_.id, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<Examination> iterator(long first, long count) {
			Serializable property = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);

			examinationFilter.setSortProperty((SingularAttribute) property);
			examinationFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);

			examinationFilter.setLimit((int) count);
			examinationFilter.setOffset((int) first);
			return examinationService.find(examinationFilter).iterator();
		}

		@Override
		public long size() {
			return examinationService.count(examinationFilter);
		}

		@Override
		public IModel<Examination> model(Examination object) {
			return new Model(object);
		}

	}

}
