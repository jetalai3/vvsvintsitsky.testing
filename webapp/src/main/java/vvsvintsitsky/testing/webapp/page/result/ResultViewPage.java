package vvsvintsitsky.testing.webapp.page.result;

import java.util.Arrays;

import javax.inject.Inject;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
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
import vvsvintsitsky.testing.datamodel.Result;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.UserRole;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.service.AnswerService;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.service.ResultService;
import vvsvintsitsky.testing.webapp.common.SubjectChoiceRenderer;
import vvsvintsitsky.testing.webapp.common.UserRoleChoiceRenderer;
import vvsvintsitsky.testing.webapp.page.AbstractPage;
import vvsvintsitsky.testing.webapp.page.answer.AnswerEditPanel;
import vvsvintsitsky.testing.webapp.page.result.panel.ResultViewPanel;

public class ResultViewPage extends AbstractPage {

	@Inject
	private ResultService resultService;

	@Inject
	private AccountService accountService;

	@Inject
	private AnswerService answerService;
	
	private Result result;

	private String language;
	
	public ResultViewPage(PageParameters parameters) {
		super(parameters);
	}

	public ResultViewPage(Result result) {
		super();
		this.result = result;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		language = Session.get().getLocale().getLanguage();
		add(new Label("examination-name", result.getExamination().getExaminationNames().getText(language)));
		add(new Label("accountProfile-firstName", result.getAccountProfile().getFirstName()));
		add(new Label("accountProfile-lastName", result.getAccountProfile().getLastName()));

		ResultViewPanel resultViewPanel = new ResultViewPanel("resultView-panel", result);
		resultViewPanel.setOutputMarkupId(true);
		add(resultViewPanel);
		add(new FeedbackPanel("feedback"));

	}

	
}
