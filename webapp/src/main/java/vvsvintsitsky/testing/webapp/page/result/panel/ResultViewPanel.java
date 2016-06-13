package vvsvintsitsky.testing.webapp.page.result.panel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.event.IEvent;
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
import vvsvintsitsky.testing.webapp.common.events.LanguageChangedEvent;
import vvsvintsitsky.testing.webapp.common.iterator.CustomIterator;
import vvsvintsitsky.testing.webapp.page.home.HomePage;

@SuppressWarnings("serial")
public class ResultViewPanel extends Panel {

	@Inject
	private QuestionService questionService;

	@Inject
	private ResultService resultService;

	private Question question;

	private List<Question> questions;

	private Result result;
	
	CustomIterator<Question> sListiterator;
	
	List<Answer> answers;
	
	Model<String> questionModel;
	
	private String language = Session.get().getLocale().getLanguage();

	public ResultViewPanel(String id, Result result) {
		super(id);
//		this.examination = examination;
//		this.questions = new ArrayList<Question>();
//		this.result = resultService.getResultWithAnswersAndQuestions(result.getId());
//		for (Answer answer : this.result.getAnswers()) {
//			question = answer.getQuestion();
//			if (!questions.contains(question)) {
//				questions.add(question);
//			}
//		}
//
//		for (Question question : questions) {
//			List<Answer> answers = new ArrayList<Answer>();
//			for (Answer answer : this.result.getAnswers()) {
//				if (question.getId() == answer.getQuestion().getId()) {
//					answers.add(answer);
//				}
//			}
//			question.setAnswers(answers);
//		}
		String language = Session.get().getLocale().getLanguage();
		this.result = result;
		this.questions = resultService.getResultQuestionsWithAnswers(result.getId(), language);
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void onInitialize() {
		super.onInitialize();

		WebMarkupContainer rowsContainer = new WebMarkupContainer("rowsContainer");
		rowsContainer.setOutputMarkupId(true);
		add(rowsContainer);

		sListiterator = new CustomIterator<Question>(questions);

		boolean linkVisible;
		
		if (sListiterator.hasNext()) {
			question = sListiterator.next();
			questionModel = Model.of(question.getQuestionTexts().getText(language));
			linkVisible = true;
		} else {
			questionModel = Model.of(getString("resultViewPanel.noMistakes"));
			question = new Question();
			question.setAnswers(new ArrayList<Answer>());
			linkVisible = false;
		}

		rowsContainer.add(new Label("question-text", questionModel));

		answers = new ArrayList<Answer>(question.getAnswers());

		DataView<Answer> dataView = new DataView<Answer>("rows", new ListDataProvider<Answer>(answers)) {
			@Override
			protected void populateItem(Item<Answer> item) {
				Answer answer = (Answer) item.getModelObject();
				language = Session.get().getLocale().getLanguage();
				item.add(new Label("id", answer.getId()));
				item.add(new Label("answer-text", answer.getAnswerTexts().getText(language)));

			}
		};

		AjaxLink nextLink = new AjaxLink("next-question") {

			private static final long serialVersionUID = 8930268252094829030L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if (sListiterator.hasNext()) {
					question = sListiterator.next();

					answers.clear();

					answers.addAll(question.getAnswers());

					questionModel.setObject(question.getQuestionTexts().getText(language));

				}
				target.add(rowsContainer);

			}
		};
		nextLink.setVisible(linkVisible);
		rowsContainer.add(nextLink);

		rowsContainer.add(new AjaxLink("previous-question") {

			private static final long serialVersionUID = 8930268252094829030L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				nextLink.setVisible(true);
				if (sListiterator.hasPrevious()) {
					question = sListiterator.previous();

					answers.clear();
					answers.addAll(question.getAnswers());

					questionModel.setObject(question.getQuestionTexts().getText(language));
				}
				target.add(rowsContainer);

			}
		}.setVisible(linkVisible));

		rowsContainer.add(dataView);

	}

	
	@Override
	public void onEvent(IEvent<?> event) {
		if (event.getPayload() instanceof LanguageChangedEvent) {
			
			this.questions.clear();
			language = Session.get().getLocale().getLanguage();
			this.questions = resultService.getResultQuestionsWithAnswers(result.getId(), language);
			sListiterator = new CustomIterator<Question>(questions);
			if (sListiterator.hasNext()) {
				question = sListiterator.next();
			}
			questionModel.setObject(question.getQuestionTexts().getText(language));
			answers.clear();
			answers.addAll(question.getAnswers());
		}

	}
}
