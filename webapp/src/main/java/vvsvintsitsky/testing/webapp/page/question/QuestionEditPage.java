package vvsvintsitsky.testing.webapp.page.question;

import java.util.Arrays;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.RangeValidator;

import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.UserRole;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.service.SubjectService;
import vvsvintsitsky.testing.webapp.common.SubjectChoiceRenderer;
import vvsvintsitsky.testing.webapp.common.UserRoleChoiceRenderer;
import vvsvintsitsky.testing.webapp.page.AbstractPage;
import vvsvintsitsky.testing.webapp.page.answer.AnswerEditPanel;
import vvsvintsitsky.testing.webapp.page.answer.panel.AnswersListPanel;

public class QuestionEditPage extends AbstractPage {

	@Inject
	private QuestionService questionService;

	@Inject
	private SubjectService subjectService;

	private Question question;

	public QuestionEditPage(PageParameters parameters) {
		super(parameters);
	}

	public QuestionEditPage(Question question) {
		super();
		this.question = question;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		WebMarkupContainer rowsContainer = new WebMarkupContainer("rowsContainer");
		rowsContainer.setOutputMarkupId(true);
		Form<Question> form = new QuestionForm<Question>("form", new CompoundPropertyModel<Question>(question));

		add(form);

		TextField<String> textField = new TextField<>("text");
		textField.setRequired(true);
		form.add(textField);

		DropDownChoice<Subject> subjectField = new DropDownChoice<>("subject", subjectService.getAll(),
				SubjectChoiceRenderer.INSTANCE);
		subjectField.setRequired(true);
		form.add(subjectField);

		form.add(new SubmitLink("save") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				// accountService.saveOrUpdate(account);
				questionService.saveOrUpdate(question);
				setResponsePage(new QuestionsPage());
			}
		});

		ModalWindow modalWindow = new ModalWindow("modal");
		AnswersListPanel answersListPanel = new AnswersListPanel("answers-panel", question);
		answersListPanel.setOutputMarkupId(true);
		rowsContainer.add(answersListPanel);
		rowsContainer.add(new AjaxLink<Void>("new-answer") {

			private static final long serialVersionUID = -4197818843372247766L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modalWindow.setContent(new AnswerEditPanel(modalWindow, question));
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
