package vvsvintsitsky.testing.webapp.page.question;

import java.util.Arrays;

import javax.inject.Inject;

import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
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

        Form<Question> form = new QuestionForm<Question>("form", new CompoundPropertyModel<Question>(question));
        add(form);

        TextField<String> textField = new TextField<>("text");
        textField.setRequired(true);
        form.add(textField);
        
        
        DropDownChoice<Subject> subjectField = new DropDownChoice<>("subject", subjectService.getAll(), SubjectChoiceRenderer.INSTANCE);
        subjectField.setRequired(true);
        form.add(subjectField);
       
        form.add(new SubmitLink("save") {
            @Override
            public void onSubmit() {
                super.onSubmit();
                //accountService.saveOrUpdate(account);
                questionService.saveOrUpdate(question);
                setResponsePage(new QuestionsPage());
            }
        });

        add(new FeedbackPanel("feedback"));
    }

    @AuthorizeAction(roles = { "ADMIN" }, action = Action.ENABLE)
    private class QuestionForm<T> extends Form<T> {

        public QuestionForm(String id, IModel<T> model) {
            super(id, model);
        }
    }
}
