package vvsvintsitsky.testing.webapp.page.answer.panel;

import java.io.Serializable;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebMarkupContainer;
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
import vvsvintsitsky.testing.dataaccess.filters.AnswerFilter;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.AccountProfile_;
import vvsvintsitsky.testing.datamodel.Account_;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Answer_;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Subject_;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.service.AnswerService;
import vvsvintsitsky.testing.webapp.page.account.AccountEditPage;
import vvsvintsitsky.testing.webapp.page.account.AccountsPage;
import vvsvintsitsky.testing.webapp.page.question.QuestionEditPage;
import vvsvintsitsky.testing.webapp.page.answer.AnswerEditPanel;
import vvsvintsitsky.testing.webapp.page.subject.SubjectsPage;

public class AnswersListPanel extends Panel {

	@Inject
	private AnswerService answerService;

	private Question question;
	public AnswersListPanel(String id, Question question) {
		super(id);
		this.question = question;
		WebMarkupContainer rowsContainer = new WebMarkupContainer("rowsContainer");
		rowsContainer.setOutputMarkupId(true);
		AnswersDataProvider answersDataProvider = new AnswersDataProvider();
		DataView<Answer> dataView = new DataView<Answer>("rows", answersDataProvider, 5) {
			@Override
			protected void populateItem(Item<Answer> item) {
				Answer answer = item.getModelObject();

				item.add(new Label("id", answer.getId()));
				item.add(new Label("text", answer.getText()));
				CheckBox checkbox = new CheckBox("correct", Model.of(answer.getCorrect()));
                item.add(checkbox);
				ModalWindow modalWindow = new ModalWindow("modal");
				item.add(modalWindow);
				item.add(new AjaxLink<Void>("edit-link") {

					private static final long serialVersionUID = -4197818843372247766L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						modalWindow.setContent(new AnswerEditPanel(modalWindow, answer));
						modalWindow.show(target);

					}
				});

				item.add(new AjaxLink("delete-link") {

					private static final long serialVersionUID = -343889365476492210L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						try {
							answerService.delete(answer.getId());
						} catch (PersistenceException e) {
							System.out.println("caught PersistenceException");
						}
						target.add(rowsContainer);
					}
				});

				modalWindow.setWindowClosedCallback(new WindowClosedCallback() {

					private static final long serialVersionUID = 8965470088247585358L;

					@Override
					public void onClose(AjaxRequestTarget target) {
						target.add(rowsContainer);
					}
				});
			}

		};
		
		rowsContainer.add(dataView);
		rowsContainer.add(new PagingNavigator("paging", dataView));
		rowsContainer.add(new OrderByBorder("sort-id", Answer_.id, answersDataProvider));
		rowsContainer.add(new OrderByBorder("sort-text", Answer_.text, answersDataProvider));
		rowsContainer.add(new OrderByBorder("sort-correct", Answer_.correct, answersDataProvider));
		
		add(rowsContainer);


	}

	private class AnswersDataProvider extends SortableDataProvider<Answer, Serializable> {

		private AnswerFilter answerFilter;

		public AnswersDataProvider() {
			super();
			answerFilter = new AnswerFilter();
			answerFilter.setFetchQuestion(true);
			answerFilter.setQuestionId(question.getId());
			setSort((Serializable) Answer_.id, SortOrder.ASCENDING);
		}

		@Override
		public Iterator<Answer> iterator(long first, long count) {
			Serializable property = getSort().getProperty();
			SortOrder propertySortOrder = getSortState().getPropertySortOrder(property);

			answerFilter.setSortProperty((SingularAttribute) property);
			answerFilter.setSortOrder(propertySortOrder.equals(SortOrder.ASCENDING) ? true : false);

			answerFilter.setLimit((int) count);
			answerFilter.setOffset((int) first);
			return answerService.find(answerFilter).iterator();
		}

		@Override
		public long size() {
			return answerService.count(answerFilter);
		}

		@Override
		public IModel<Answer> model(Answer object) {
			return new Model(object);
		}

	}

}
