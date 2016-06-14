package vvsvintsitsky.testing.webapp.page.question;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.LocalTexts;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.VariousTexts;
import vvsvintsitsky.testing.service.AnswerService;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.service.SubjectService;
import vvsvintsitsky.testing.webapp.app.AuthorizedSession;
import vvsvintsitsky.testing.webapp.common.SubjectChoiceRenderer;
import vvsvintsitsky.testing.webapp.common.events.LanguageChangedEvent;
import vvsvintsitsky.testing.webapp.page.AbstractPage;
import vvsvintsitsky.testing.webapp.page.answer.AnswerEditPanel;
import vvsvintsitsky.testing.webapp.page.answer.panel.AnswersListPanel;

public class QuestionEditPage extends AbstractPage {

	@Inject
	private QuestionService questionService;

	@Inject
	private SubjectService subjectService;

	@Inject
	private AnswerService answerService;

	private Question question;

	private LocalTexts texts;

	private VariousTexts rusText;

	private VariousTexts engText;
	
	private Logger logger;

	public QuestionEditPage(PageParameters parameters) {
		super(parameters);
		this.logger = LoggerFactory.getLogger(QuestionEditPage.class);
	}

	public QuestionEditPage(Question question) {
		super();
		this.logger = LoggerFactory.getLogger(QuestionEditPage.class);

		if (question.getId() != null) {
			this.question = questionService.getQuestionWithAnswers(question.getId());
			this.texts = this.question.getQuestionTexts();
			this.question.setSubject(question.getSubject());
			this.rusText = this.texts.getRusText();
			this.engText = this.texts.getEngText();
		} else {
			this.question = question;
			this.texts = new LocalTexts();
			this.rusText = new VariousTexts();
			this.engText = new VariousTexts();
			this.question.setAnswers(new ArrayList<Answer>());
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		WebMarkupContainer rowsContainer = new WebMarkupContainer("rowsContainer");
		rowsContainer.setOutputMarkupId(true);
		Form<Question> form = new QuestionForm<Question>("form", new CompoundPropertyModel<Question>(question));

		add(form);

		List<Subject> subjects = subjectService.getAllWithLanguageText(Session.get().getLocale().getLanguage());
		DropDownChoice<Subject> subjectField = new DropDownChoice<Subject>("subject", subjects,
				SubjectChoiceRenderer.INSTANCE) {
			@Override
			public void onEvent(IEvent<?> event) {
				if (event.getPayload() instanceof LanguageChangedEvent) {
					subjects.clear();
					SubjectChoiceRenderer.language = Session.get().getLocale().getLanguage();
					subjects.addAll(subjectService.getAllWithLanguageText(SubjectChoiceRenderer.language));
				}
			}
		};
		subjectField.setRequired(true);
		form.add(subjectField);

		Form<VariousTexts> formRusText = new Form<VariousTexts>("formRusText", new CompoundPropertyModel<>(rusText));
		form.add(formRusText);
		formRusText.add(new TextField<>("txt").setRequired(true));
		
		Form<VariousTexts> formEngText = new Form<VariousTexts>("formEngText", new CompoundPropertyModel<>(engText));
		form.add(formEngText);
		formEngText.add(new TextField<>("txt").setRequired(true));

		form.add(new SubmitLink("save") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				logger.warn("User {} attepmpted to create/update question", AuthorizedSession.get().getLoggedUser().getId());
				List<Answer> answers = question.getAnswers();
				texts.setRusText(rusText);
				texts.setEngText(engText);
				question.setQuestionTexts(texts);
				try{
				questionService.saveOrUpdate(question);

				for (Answer answer : answers) {
					answer.setQuestion(question);
					answerService.saveOrUpdate(answer);
				}
				} catch(PersistenceException e) {
					logger.error("User {} failed to submit question", AuthorizedSession.get().getLoggedUser().getId());
				}
				setResponsePage(new QuestionsPage());
			}
		});

		ModalWindow modalWindow = new ModalWindow("modal");
		modalWindow.setAutoSize(true);
		AnswersListPanel answersListPanel = new AnswersListPanel("answers-panel", question);
		answersListPanel.setOutputMarkupId(true);
		rowsContainer.add(answersListPanel);
		rowsContainer.add(new AjaxLink<Void>("new-answer") {

			private static final long serialVersionUID = -4197818843372247766L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				modalWindow.setContent(new AnswerEditPanel(modalWindow, new Answer()));

				modalWindow.show(target);

			}
		});
		modalWindow.setWindowClosedCallback(new WindowClosedCallback() {

			private static final long serialVersionUID = 8965470088247585358L;

			@Override
			public void onClose(AjaxRequestTarget target) {
				target.add(rowsContainer);
			}
		});
		rowsContainer.add(modalWindow);
		add(rowsContainer);
		add(new FeedbackPanel("feedback"));

	}

	 @AuthorizeAction(roles = { "ADMIN" }, action = Action.ENABLE)
	private class QuestionForm<T> extends Form<T> {

		public QuestionForm(String id, IModel<T> model) {
			super(id, model);
		}
	}
}
