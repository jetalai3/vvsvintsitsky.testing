package vvsvintsitsky.testing.webapp.page.completing.panel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Result;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.service.ResultService;
import vvsvintsitsky.testing.webapp.app.AuthorizedSession;
import vvsvintsitsky.testing.webapp.common.iterator.CustomIterator;

@SuppressWarnings("serial")
public class CompletingListPanel extends Panel {

	@Inject
	private QuestionService questionService;

	@Inject
	private ResultService resultService;
	
	private Question question;

	private List<Question> questions;

	private Examination examination;
	
	public CompletingListPanel(String id, Examination examination) {
		super(id);
		this.examination = examination;
		questionService.getQuestionsWithAnswers(this.examination);
		this.questions = examination.getQuestions();

	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void onInitialize() {
		super.onInitialize();

		WebMarkupContainer rowsContainer = new WebMarkupContainer("rowsContainer");
		rowsContainer.setOutputMarkupId(true);
		add(rowsContainer);

		CustomIterator<Question> sListiterator = new CustomIterator<Question>(questions);

		if (sListiterator.hasNext()) {
			question = sListiterator.next();
		}

		Model<String> questionModel = Model.of(question.getText());
		rowsContainer.add(new Label("question-text", questionModel));

		List<Answer> answers = new ArrayList<Answer>(question.getAnswers());

		DataView<Answer> dataView = new DataView<Answer>("rows", new ListDataProvider<Answer>(answers)) {
			@Override
			protected void populateItem(Item<Answer> item) {
				Answer answer = (Answer) item.getModelObject();
				Form<Answer> form = new Form<Answer>("form", new CompoundPropertyModel<>(answer));

				item.add(new Label("id", answer.getId()));
				item.add(new Label("answer-text", answer.getText()));
				form.add(new AjaxCheckBox("answered") {
					@Override
					protected void onUpdate(AjaxRequestTarget target) {
					}
				});
				item.add(form);
			}
		};

		AjaxLink finishLink = new AjaxLink("finish-examination") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				int total = 0;
				target.add(rowsContainer);
				List<Answer> mistakes = new ArrayList<Answer>();
				for(Question question : examination.getQuestions()){
					for(Answer answer : question.getAnswers()){
						total++;
						if(answer.getCorrect() != answer.getAnswered()){
							mistakes.add(answer);
						}
					}
				}
				Result result = new Result();
				result.setAnswers(mistakes);
				result.setAccountProfile(AuthorizedSession.get().getLoggedUser());
				result.setExamination(examination);
				total = (total - mistakes.size()) * 100 / total;
				result.setPoints(total);
				resultService.insert(result);
			}
		};
		finishLink.setVisible(false);
		rowsContainer.add(finishLink);
		
		AjaxLink nextLink = new AjaxLink("next-question") {

			private static final long serialVersionUID = 8930268252094829030L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if (sListiterator.hasNext()) {
					question = sListiterator.next();

					answers.clear();

					answers.addAll(question.getAnswers());

					questionModel.setObject(question.getText());

				} else {
					this.setVisible(false);
					finishLink.setVisible(true);
				}
				target.add(rowsContainer);

			}
		};
		rowsContainer.add(nextLink);
		
		rowsContainer.add(new AjaxLink("previous-question") {

			private static final long serialVersionUID = 8930268252094829030L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				finishLink.setVisible(false);
				nextLink.setVisible(true);
				if (sListiterator.hasPrevious()) {
					question = sListiterator.previous();

					answers.clear();
					answers.addAll(question.getAnswers());

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
