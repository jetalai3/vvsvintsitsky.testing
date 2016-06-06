package vvsvintsitsky.testing.webapp.page.completing.panel;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import vvsvintsitsky.testing.dataaccess.filters.QuestionFilter;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.service.ExaminationService;
import vvsvintsitsky.testing.service.QuestionService;

public class CompletingListPanel extends Panel {

	@Inject
	private QuestionService questionService;

	private Question question;

	private Examination examination;
	
	private List<Question> questions;

	private Integer length;

	private Integer position;

	public CompletingListPanel(String id, Examination examination) {
		super(id);
		this.examination = examination;
		System.out.println(1);
		this.questions = examination.getQuestions();
		System.out.println(2);
		length = questions.size();
		position = 0;
		
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		WebMarkupContainer rowsContainer = new WebMarkupContainer("rowsContainer");
		rowsContainer.setOutputMarkupId(true);
		add(rowsContainer);

		if (length != 0) {
			
			question = questionService.getQuestionWithAnswers(questions.get(position).getId());
			System.out.println(question.getText());
			position++;
		} else {
			throw new IllegalArgumentException("No questions found");
		}

		Model<String> questionModel = Model.of(question.getText());
		rowsContainer.add(new Label("question-text", questionModel));

		DataView<Answer> dataView = new DataView<Answer>("rows",
				new ListDataProvider<Answer>(this.question.getAnswers())) {
			@Override
			protected void populateItem(Item<Answer> item) {
				Answer answer = item.getModelObject();

				item.add(new Label("id", answer.getId()));
				item.add(new Label("answer-text", answer.getText()));
				CheckBox checkbox = new CheckBox("select-answer");
				item.add(checkbox);
			}
		};
		rowsContainer.add(new AjaxLink("next-question") {

			private static final long serialVersionUID = 8930268252094829030L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if (position < length) {
					question = questionService.getQuestionWithAnswers(questions.get(position).getId());
					position++;
					questionModel.setObject(question.getText());
				}
				target.add(rowsContainer);

			}
		});
		rowsContainer.add(new AjaxLink("previous-question") {

			private static final long serialVersionUID = 8930268252094829030L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if (position > 0) {
					position--;
					question = questionService.getQuestionWithAnswers(questions.get(position).getId());
					questionModel.setObject(question.getText());
				}

				target.add(rowsContainer);

			}
		});
		rowsContainer.add(dataView);

	}

	@AuthorizeAction(roles = { "ADMIN" }, action = Action.ENABLE)
	private class QuestionForm<T> extends Form<T> {

		public QuestionForm(String id, IModel<T> model) {
			super(id, model);
		}
	}
}
