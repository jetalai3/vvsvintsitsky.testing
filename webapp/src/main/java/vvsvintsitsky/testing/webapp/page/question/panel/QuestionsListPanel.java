package vvsvintsitsky.testing.webapp.page.question.panel;

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
import vvsvintsitsky.testing.dataaccess.filters.QuestionFilter;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.AccountProfile_;
import vvsvintsitsky.testing.datamodel.Account_;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Question_;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.webapp.page.account.AccountEditPage;
import vvsvintsitsky.testing.webapp.page.account.AccountsPage;
import vvsvintsitsky.testing.webapp.page.question.QuestionEditPage;

public class QuestionsListPanel extends Panel {

	@Inject
	private QuestionService questionService;

	public QuestionsListPanel(String id) {
		super(id);

		QuestionsDataProvider questionsDataProvider = new QuestionsDataProvider();
		DataView<Question> dataView = new DataView<Question>("rows", questionsDataProvider, 5) {
			@Override
			protected void populateItem(Item<Question> item) {
				Question question = item.getModelObject();

				item.add(new Label("id", question.getId()));
				item.add(new Label("text", question.getText()));
				item.add(new Label("subject", question.getSubject().getName()));

				item.add(new Link<Void>("edit-link") {
					@Override
					public void onClick() {
						setResponsePage(new QuestionEditPage(question));
					}
				});

				item.add(new Link<Void>("delete-link") {
					@Override
					public void onClick() {
						try {
							questionService.delete(question.getId());
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

		add(new OrderByBorder("sort-id", Question_.id, questionsDataProvider));
		add(new OrderByBorder("sort-text", Question_.text, questionsDataProvider));
		add(new OrderByBorder("sort-subject", Question_.subject.getName(), questionsDataProvider));
		

	}

	private class QuestionsDataProvider extends SortableDataProvider<Question, Serializable> {

		private QuestionFilter questionFilter;
		

		public QuestionsDataProvider() {
			super();
			questionFilter = new QuestionFilter();
			questionFilter.setFetchSubject(true);
			setSort((Serializable) Question_.id, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<Question> iterator(long first, long count) {
			Serializable property = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);

			questionFilter.setSortProperty((SingularAttribute) property);
			questionFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);

			questionFilter.setLimit((int) count);
			questionFilter.setOffset((int) first);
			return questionService.find(questionFilter).iterator();
		}

		@Override
		public long size() {
			return questionService.count(questionFilter);
		}

		@Override
		public IModel<Question> model(Question object) {
			return new Model(object);
		}

	}

}