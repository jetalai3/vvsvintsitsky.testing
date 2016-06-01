package vvsvintsitsky.testing.webapp.page.answer;

import java.util.Arrays;

import javax.inject.Inject;

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

import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.UserRole;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.service.AnswerService;
import vvsvintsitsky.testing.webapp.common.SubjectChoiceRenderer;
import vvsvintsitsky.testing.webapp.common.UserRoleChoiceRenderer;
import vvsvintsitsky.testing.webapp.page.AbstractPage;

public class AnswerEditPanel extends Panel {

    @Inject
    private AnswerService answerService;

    private Question question;
    
    private Answer answer;

    private ModalWindow modalWindow;
    
    public AnswerEditPanel(ModalWindow modalWindow, Answer answer) {
    		super(modalWindow.getContentId());
    		this.answer = answer;
    		this.modalWindow = modalWindow;
    	}
    public AnswerEditPanel(ModalWindow modalWindow,  Question question) {
		super(modalWindow.getContentId());
		this.question = question;
		this.answer = new Answer();
		this.modalWindow = modalWindow;
	}

    	@Override
    	protected void onInitialize() {
    		super.onInitialize();
    		Form<Answer> form = new Form<Answer>("form", new CompoundPropertyModel<>(answer));
    		add(form);

    		form.add(new TextField<>("text"));
    		CheckBox activeField = new CheckBox("correct");
            form.add(activeField);
    		form.add(new AjaxSubmitLink("save") {

    			private static final long serialVersionUID = -5210362644590530669L;

    			@Override
    			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
    				super.onSubmit(target, form);
    				if(answer.getId() == null){
    					answer.setQuestion(question);
    				}
    				answerService.saveOrUpdate(answer);
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
    	}
}
