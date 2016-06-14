package vvsvintsitsky.testing.webapp.page.completing.panel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Result;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.service.ResultService;
import vvsvintsitsky.testing.webapp.app.AuthorizedSession;
import vvsvintsitsky.testing.webapp.common.QuestionChoiceRenderer;
import vvsvintsitsky.testing.webapp.common.events.LanguageChangedEvent;
import vvsvintsitsky.testing.webapp.common.iterator.CustomIterator;
import vvsvintsitsky.testing.webapp.page.home.HomePage;

@SuppressWarnings("serial")
public class CompletingListPanel extends Panel {

	@Inject
	private QuestionService questionService;

	@Inject
	private ResultService resultService;
	
	private Question question;

	private List<Question> questions;

	private Examination examination;
	
	CustomIterator<Question> sListiterator;
	
	List<Answer> answers;
	
	Model<String> questionModel;
	
	private Logger logger;
	
	private String language = Session.get().getLocale().getLanguage();
	
	public CompletingListPanel(String id, Examination examination) {
		super(id);
		this.examination = examination;
		this.logger = LoggerFactory.getLogger(CompletingListPanel.class);
		Locale locale = Session.get().getLocale();
		
		questionService.getQuestionsWithAnswers(this.examination, locale.getLanguage());
		this.questions = examination.getQuestions();

	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void onInitialize() {
		super.onInitialize();

		WebMarkupContainer rowsContainer = new WebMarkupContainer("rowsContainer");
		rowsContainer.setOutputMarkupId(true);
		add(rowsContainer);

		sListiterator = new CustomIterator<Question>(questions);

		if (sListiterator.hasNext()) {
			question = sListiterator.next();
		}

		questionModel = Model.of(question.getQuestionTexts().getText(language));
		rowsContainer.add(new Label("question-text", questionModel));

		answers = new ArrayList<Answer>(question.getAnswers());

		DataView<Answer> dataView = new DataView<Answer>("rows", new ListDataProvider<Answer>(answers)) {
			@Override
			protected void populateItem(Item<Answer> item) {
				Answer answer = (Answer) item.getModelObject();
				Form<Answer> form = new Form<Answer>("form", new CompoundPropertyModel<>(answer));

				item.add(new Label("id", answer.getId()));
				item.add(new Label("answer-text", answer.getAnswerTexts().getText(language)));
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
				logger.warn("User {} attepmpted to submit new result", AuthorizedSession.get().getLoggedUser().getId());
				int total = 0;
				target.add(rowsContainer);
				List<Answer> mistakes = new ArrayList<Answer>();
				for(Question question : examination.getQuestions()){
					for(Answer answer : question.getAnswers()){
						total++;
						if(answer.getCorrect().compareTo(answer.getAnswered()) != 0){
							System.out.println("correct: " + answer.getCorrect() + " answered: " +answer.getAnswered());
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
				try{
				resultService.insert(result);
				} catch(PersistenceException e) {
					logger.error("User {} failed to submit new result", AuthorizedSession.get().getLoggedUser().getId());
				}
				setResponsePage(new HomePage());
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

					questionModel.setObject(question.getQuestionTexts().getText(language));

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

					questionModel.setObject(question.getQuestionTexts().getText(language));
				}
				target.add(rowsContainer);

			}
		});
		
		rowsContainer.add(dataView);

	}

	
	@Override
	public void onEvent(IEvent<?> event) {
		if (event.getPayload() instanceof LanguageChangedEvent) {
			
			this.questions.clear();
			language = Session.get().getLocale().getLanguage();
			questionService.getQuestionsWithAnswers(this.examination,  language);
			this.questions = examination.getQuestions();
			sListiterator = new CustomIterator<Question>(questions);
			if (sListiterator.hasNext()) {
				question = sListiterator.next();
			}
			questionModel.setObject(question.getQuestionTexts().getText(language));
			answers.clear();
			answers.addAll(question.getAnswers());

		}

	}
	

	@AuthorizeAction(roles = { "ADMIN" }, action = Action.ENABLE)
	private class QuestionForm<T> extends Form<T> {

		public QuestionForm(String id, IModel<T> model) {
			super(id, model);
		}
	}
}
