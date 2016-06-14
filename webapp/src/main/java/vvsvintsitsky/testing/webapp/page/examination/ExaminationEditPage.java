package vvsvintsitsky.testing.webapp.page.examination;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.extensions.markup.html.form.palette.theme.DefaultTheme;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.CollectionModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vvsvintsitsky.testing.dataaccess.filters.QuestionFilter;
import vvsvintsitsky.testing.dataaccess.filters.SubjectFilter;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.LocalTexts;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.VariousTexts;
import vvsvintsitsky.testing.service.ExaminationService;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.service.SubjectService;
import vvsvintsitsky.testing.webapp.app.AuthorizedSession;
import vvsvintsitsky.testing.webapp.common.QuestionChoiceRenderer;
import vvsvintsitsky.testing.webapp.common.SubjectChoiceRenderer;
import vvsvintsitsky.testing.webapp.common.events.AnswerAddEvent;
import vvsvintsitsky.testing.webapp.common.events.LanguageChangedEvent;
import vvsvintsitsky.testing.webapp.common.events.SubjectChangeEvent;
import vvsvintsitsky.testing.webapp.page.AbstractPage;

public class ExaminationEditPage extends AbstractPage {

	@Inject
	private ExaminationService examinationService;

	@Inject
	private QuestionService questionService;

	@Inject
	private SubjectService subjectService;

	private Examination examination;

	private List<Question> exQuestions;

	private List<Question> allQuestions;

	QuestionFilter questionFilter;

	private LocalTexts texts;

	private VariousTexts rusText;

	private VariousTexts engText;

	private String language;

	private Logger logger;

	public ExaminationEditPage(PageParameters parameters) {
		super(parameters);
	}

	public ExaminationEditPage(Examination examination) {
		super();
		language = Session.get().getLocale().getLanguage();
		this.logger = LoggerFactory.getLogger(ExaminationEditPage.class);
		if (examination.getId() == null) {
			this.examination = examination;
			rusText = new VariousTexts();
			engText = new VariousTexts();
			texts = new LocalTexts();
		} else {
			this.examination = examinationService.getWithAllTexts(examination.getId(), language);
			texts = this.examination.getExaminationNames();
			rusText = texts.getRusText();
			engText = texts.getEngText();
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		WebMarkupContainer rowsContainer = new WebMarkupContainer("rowsContainer");
		rowsContainer.setOutputMarkupId(true);
		add(rowsContainer);

		Form<Examination> form = new Form<Examination>("form", new CompoundPropertyModel<>(examination));
		rowsContainer.add(form);

		Form<VariousTexts> formRusText = new Form<VariousTexts>("formRusText", new CompoundPropertyModel<>(rusText));
		form.add(formRusText);
		formRusText.add(new TextField<>("txt").setRequired(true));

		Form<VariousTexts> formEngText = new Form<VariousTexts>("formEngText", new CompoundPropertyModel<>(engText));
		form.add(formEngText);
		formEngText.add(new TextField<>("txt").setRequired(true));

		FeedbackPanel feedBackPanel = new FeedbackPanel("feedback");
		feedBackPanel.setOutputMarkupId(true);
		form.add(feedBackPanel);

		DateTextField beginDateField = new DateTextField("beginDate");
		beginDateField.add(new DatePicker());
		beginDateField.setRequired(true);
		form.add(beginDateField);
		DateTextField endDateField = new DateTextField("endDate");
		endDateField.add(new DatePicker());
		endDateField.setRequired(true);
		form.add(endDateField);

		List<Subject> allSubjects = subjectService.getAllWithLanguageText(Session.get().getLocale().getLanguage());
		questionFilter = new QuestionFilter();
		questionFilter.setFetchTexts(true);
		questionFilter.setLanguage(Session.get().getLocale().getLanguage());
		allQuestions = questionService.find(questionFilter);

		DropDownChoice<Subject> dropDownChoice = new DropDownChoice<Subject>("subject", allSubjects,
				SubjectChoiceRenderer.INSTANCE) {
			@Override
			public void onEvent(IEvent<?> event) {
				if (event.getPayload() instanceof LanguageChangedEvent) {
					allSubjects.clear();
					SubjectChoiceRenderer.language = Session.get().getLocale().getLanguage();
					allSubjects.addAll(subjectService.getAllWithLanguageText(SubjectChoiceRenderer.language));
				}
			}
		};
		dropDownChoice.setRequired(true);
		form.add(dropDownChoice);
		dropDownChoice.add(new AjaxEventBehavior("change") {
			@Override
			protected void onEvent(AjaxRequestTarget target) {
				dropDownChoice.onSelectionChanged();
				Subject subject = dropDownChoice.getModelObject();

				language = Session.get().getLocale().getLanguage();

				questionFilter.setSubjectName(subject.getSubjectNames().getText(language));
				allQuestions.clear();
				allQuestions.addAll(questionService.find(questionFilter));
				target.add(rowsContainer);
			}
		});

		final Palette<Question> palette = new Palette<Question>("questions", Model.ofList(examination.getQuestions()),
				new CollectionModel<Question>(allQuestions), QuestionChoiceRenderer.INSTANCE, 15, false, true);
		palette.add(new DefaultTheme());
		form.add(palette);

		form.add(new AjaxSubmitLink("save") {
			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				logger.warn("User {} attepmpted to create/update examination",
						AuthorizedSession.get().getLoggedUser().getId());
				texts.setRusText(rusText);
				texts.setEngText(engText);
				examination.setExaminationNames(texts);
				examination.setAccountProfile(AuthorizedSession.get().getLoggedUser());
				Date currentDate = new Date();
				boolean conditionOne = beginDateField.getModelObject().getTime() > endDateField.getModelObject()
						.getTime();
				boolean conditionTwo = beginDateField.getModelObject().getTime() < currentDate.getTime();
				if (conditionOne || conditionTwo) {
					feedBackPanel.info(getString("wrongDates"));
					target.add(rowsContainer);
				} else {
					try {
						examinationService.saveOrUpdate(examination);
					} catch (PersistenceException e) {
						logger.error("User {} failed to submit examination",
								AuthorizedSession.get().getLoggedUser().getId());
					}
					setResponsePage(new ExaminationsPage());
				}
				
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedBackPanel);
			}
		});
	}

	@Override
	public void onEvent(IEvent<?> event) {
		if (event.getPayload() instanceof LanguageChangedEvent) {

			allQuestions.clear();
			QuestionChoiceRenderer.language = Session.get().getLocale().getLanguage();
			questionFilter.setLanguage(QuestionChoiceRenderer.language);
			allQuestions.addAll(questionService.find(questionFilter));
			// answers.clear();
			// answers.addAll(question.getAnswers());

		}

	}

	@AuthorizeAction(roles = { "ADMIN" }, action = Action.ENABLE)
	private class ExaminationForm<T> extends Form<T> {

		public ExaminationForm(String id, IModel<T> model) {
			super(id, model);
		}
	}
}
