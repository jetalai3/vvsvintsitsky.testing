package vvsvintsitsky.testing.webapp.page.completing.panel;

import java.io.Serializable;
import java.util.Iterator;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
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
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import vvsvintsitsky.testing.dataaccess.filters.ExaminationFilter;
import vvsvintsitsky.testing.dataaccess.filters.QuestionFilter;
import vvsvintsitsky.testing.datamodel.AccountProfile_;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Examination_;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.service.ExaminationService;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.webapp.page.answer.AnswerEditPanel;
import vvsvintsitsky.testing.webapp.page.examination.ExaminationEditPage;
import vvsvintsitsky.testing.webapp.page.examination.ExaminationsPage;


public class CompletingListPanel extends Panel {

	@Inject
	private ExaminationService examinationService;

	@Inject
	private QuestionService questionService;
	
	private QuestionFilter questionFilter;
	
	private Question question;

	public CompletingListPanel(String id, Question question) {
		super(id);
		questionFilter = new QuestionFilter();
		

		questionFilter.setFetchAnswers(true);
		questionFilter.setId(question.getId());
		this.question = questionService.find(questionFilter).get(0);
		System.out.println(this.question.getText());
		
		Form<Question> formQuestion = new QuestionForm<Question>("form-question", new CompoundPropertyModel<Question>(this.question));

		add(formQuestion);

		
		formQuestion.add(new Label("question-text", question.getText()));
		
		
		
		DataView<Answer> dataView = new DataView<Answer>("rows", new ListDataProvider<Answer>(this.question.getAnswers())) {
			@Override
			protected void populateItem(Item<Answer> item) {
				Answer answer = item.getModelObject();

				item.add(new Label("id", answer.getId()));
				item.add(new Label("answer-text", answer.getText()));
				CheckBox checkbox = new CheckBox("select-answer");
                item.add(checkbox);
				
			}

		};
		
		add(dataView);

		
	}
	@AuthorizeAction(roles = { "ADMIN" }, action = Action.ENABLE)
	private class QuestionForm<T> extends Form<T> {

		public QuestionForm(String id, IModel<T> model) {
			super(id, model);
		}
	}
	}

	
		
		

	

