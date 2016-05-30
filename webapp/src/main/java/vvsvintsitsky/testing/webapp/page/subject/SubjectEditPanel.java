package vvsvintsitsky.testing.webapp.page.subject;

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
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.UserRole;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.service.SubjectService;
import vvsvintsitsky.testing.webapp.common.SubjectChoiceRenderer;
import vvsvintsitsky.testing.webapp.common.UserRoleChoiceRenderer;
import vvsvintsitsky.testing.webapp.page.AbstractPage;

public class SubjectEditPanel extends Panel {

    @Inject
    private SubjectService subjectService;

    
    
    private Subject subject;

    private ModalWindow modalWindow;
    
    public SubjectEditPanel(ModalWindow modalWindow, Subject subject) {
    		super(modalWindow.getContentId());
    		this.subject = subject;
    		this.modalWindow = modalWindow;
    	}

    	@Override
    	protected void onInitialize() {
    		super.onInitialize();
    		Form<Subject> form = new Form<Subject>("form", new CompoundPropertyModel<>(subject));
    		add(form);

    		form.add(new TextField<>("name"));

    		form.add(new AjaxSubmitLink("save") {

    			private static final long serialVersionUID = -5210362644590530669L;

    			@Override
    			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
    				super.onSubmit(target, form);
    				subjectService.saveOrUpdate(subject);
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
