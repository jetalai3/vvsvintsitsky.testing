package vvsvintsitsky.testing.webapp.page.subject;

import java.util.Arrays;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.RangeValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.LocalTexts;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.UserRole;
import vvsvintsitsky.testing.datamodel.VariousTexts;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.service.SubjectService;
import vvsvintsitsky.testing.webapp.app.AuthorizedSession;
import vvsvintsitsky.testing.webapp.common.SubjectChoiceRenderer;
import vvsvintsitsky.testing.webapp.common.UserRoleChoiceRenderer;
import vvsvintsitsky.testing.webapp.page.AbstractPage;

public class SubjectEditPanel extends Panel {

	@Inject
	private SubjectService subjectService;

	private Subject subject;

	private LocalTexts texts;

	private VariousTexts rusText;

	private VariousTexts engText;

	private ModalWindow modalWindow;

	private Logger logger;
	
	public SubjectEditPanel(ModalWindow modalWindow, Subject subject) {
		super(modalWindow.getContentId());
		this.logger = LoggerFactory.getLogger(SubjectEditPanel.class);
		if (subject.getId() != null) {
			this.subject = subjectService.getWithAllTexts(subject.getId());
			this.texts = this.subject.getSubjectNames();
			this.rusText = this.texts.getRusText();
			this.engText = this.texts.getEngText();

		} else {
			this.subject = new Subject();
			this.texts = new LocalTexts();
			this.rusText = new VariousTexts();
			this.engText = new VariousTexts();
		}
		this.modalWindow = modalWindow;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		Form<Subject> form = new Form<Subject>("form", new CompoundPropertyModel<>(subject));
		add(form);

		Form<VariousTexts> formRusText = new Form<VariousTexts>("formRusText", new CompoundPropertyModel<>(rusText));
		form.add(formRusText);
		formRusText.add(new TextField<>("txt").setRequired(true));

		Form<VariousTexts> formEngText = new Form<VariousTexts>("formEngText", new CompoundPropertyModel<>(engText));
		form.add(formEngText);
		formEngText.add(new TextField<>("txt").setRequired(true));

		form.add(new AjaxSubmitLink("save") {

			private static final long serialVersionUID = -5210362644590530669L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				logger.warn("User {} attepmpted to create/update subject", AuthorizedSession.get().getLoggedUser().getId());
				texts.setRusText(rusText);
				texts.setEngText(engText);
				subject.setSubjectNames(texts);
				try{
				subjectService.saveOrUpdate(subject);
				} catch(PersistenceException e){
					logger.error("User {} failed to submit account", AuthorizedSession.get().getLoggedUser().getId());
				}
				modalWindow.close(target);
			}
		});

		form.add(new AjaxLink<Object>("cancel") {

			private static final long serialVersionUID = 2020843267475126323L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modalWindow.close(target);
			}
		});
		 form.add(new FeedbackPanel("feedbackpanel"));
	}
}
