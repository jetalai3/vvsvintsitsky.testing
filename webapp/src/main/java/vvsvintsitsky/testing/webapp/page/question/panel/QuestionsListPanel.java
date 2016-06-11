package vvsvintsitsky.testing.webapp.page.question.panel;

import java.io.Serializable;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.sort.AjaxFallbackOrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.RangeValidator;

import vvsvintsitsky.testing.dataaccess.filters.AccountFilter;
import vvsvintsitsky.testing.dataaccess.filters.AccountProfileFilter;
import vvsvintsitsky.testing.dataaccess.filters.QuestionFilter;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.AccountProfile_;
import vvsvintsitsky.testing.datamodel.Account_;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Question_;
import vvsvintsitsky.testing.datamodel.Subject_;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.service.AnswerService;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.webapp.page.account.AccountEditPage;
import vvsvintsitsky.testing.webapp.page.account.AccountsPage;
import vvsvintsitsky.testing.webapp.page.question.QuestionEditPage;
import vvsvintsitsky.testing.webapp.page.question.QuestionsPage;

public class QuestionsListPanel extends Panel {

	@Inject
	private QuestionService questionService;

	@Inject
	private AnswerService answerService;
	
	private QuestionFilter questionFilter;

	public QuestionsListPanel(String id) {
		super(id);

		WebMarkupContainer rowsContainer = new WebMarkupContainer("rowsContainer");
		rowsContainer.setOutputMarkupId(true);
		questionFilter = new QuestionFilter();
		QuestionsDataProvider questionsDataProvider = new QuestionsDataProvider();
		DataView<Question> dataView = new DataView<Question>("rows", questionsDataProvider, 5) {

			private static final long serialVersionUID = -5461684826840940846L;

			@Override
			protected void populateItem(Item<Question> item) {
				Question question = item.getModelObject();

				item.add(new Label("question-id", question.getId()));
				item.add(new Label("question-text", question.getText()));
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
							answerService.deleteAnswerByQuestionId(question.getId());
							questionService.delete(question.getId());
						} catch (PersistenceException e) {
							System.out.println("caughth persistance exception");
						}

						setResponsePage(new QuestionsPage());
					}
				});

			}
		};
		rowsContainer.add(dataView);
		rowsContainer.add(new AjaxPagingNavigator("paging", dataView) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onAjaxEvent(AjaxRequestTarget target) {
				target.add(rowsContainer);
			}
		});

		Form<QuestionFilter> formId = new Form<QuestionFilter>("formId", new CompoundPropertyModel<>(questionFilter));
		TextField<Long> idField = new TextField<>("id");
		idField.add(RangeValidator.<Long> range(0L, 100L));
		formId.add(idField);

		Form<QuestionFilter> formText = new Form<QuestionFilter>("formText",
				new CompoundPropertyModel<>(questionFilter));
		TextField<Integer> textField = new TextField<>("text");
		formText.add(textField);

		AjaxFallbackOrderByBorder ajaxFallbackOrderByBorderId = new AjaxFallbackOrderByBorder("sort-id", Question_.id,
				questionsDataProvider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}

			@Override
			protected void onAjaxClick(AjaxRequestTarget target) {
				target.add(rowsContainer);
			}
		};
		formId.add(ajaxFallbackOrderByBorderId);

		AjaxFallbackOrderByBorder ajaxFallbackOrderByBorderText = new AjaxFallbackOrderByBorder("sort-text",
				Question_.text, questionsDataProvider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}

			@Override
			protected void onAjaxClick(AjaxRequestTarget target) {
				target.add(rowsContainer);
			}
		};
		formText.add(ajaxFallbackOrderByBorderText);
		AjaxFallbackOrderByBorder ajaxFallbackOrderByBorderSubject = new AjaxFallbackOrderByBorder("sort-subject",
				Subject_.name, questionsDataProvider) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSortChanged() {
				dataView.setCurrentPage(0);
			}

			@Override
			protected void onAjaxClick(AjaxRequestTarget target) {
				target.add(rowsContainer);
			}
		};
		rowsContainer.add(ajaxFallbackOrderByBorderSubject);

		rowsContainer.add(formId);
		rowsContainer.add(formText);
		add(rowsContainer);
	}

	private class QuestionsDataProvider extends SortableDataProvider<Question, Serializable> {

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