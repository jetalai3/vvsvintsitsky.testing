package vvsvintsitsky.testing.webapp.page.examination;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.extensions.markup.html.form.palette.theme.DefaultTheme;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
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
import vvsvintsitsky.testing.dataaccess.filters.ExaminationFilter;
import vvsvintsitsky.testing.dataaccess.filters.QuestionFilter;
import vvsvintsitsky.testing.dataaccess.filters.SubjectFilter;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.service.ExaminationService;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.service.SubjectService;
import vvsvintsitsky.testing.webapp.app.AuthorizedSession;
import vvsvintsitsky.testing.webapp.common.ExaminationChoiceRenderer;
import vvsvintsitsky.testing.webapp.common.QuestionChoiceRenderer;
import vvsvintsitsky.testing.webapp.common.SubjectChoiceRenderer;
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

	public ExaminationEditPage(PageParameters parameters) {
		super(parameters);
	}

	public ExaminationEditPage(Examination examination) {
		super();
		this.examination = examination;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		Form<Examination> form = new Form<Examination>("form", new CompoundPropertyModel<>(examination));
        add(form);

        
        form.add(new TextField<>("name"));
        DateTextField beginDateField = new DateTextField("beginDate");
        beginDateField.add(new DatePicker());
        beginDateField.setRequired(true);
        form.add(beginDateField);
        DateTextField endDateField = new DateTextField("endDate");
        endDateField.add(new DatePicker());
        endDateField.setRequired(true);
        form.add(endDateField);
        List<Subject> allSubjects = subjectService.find(new SubjectFilter());
        DropDownChoice<Subject> dropDownChoice = new DropDownChoice<>("subject", allSubjects, SubjectChoiceRenderer.INSTANCE);
        form.add(dropDownChoice);
        dropDownChoice.add(new AjaxEventBehavior("change") {
            @Override
            protected void onEvent(AjaxRequestTarget target) {
                send(getPage(), Broadcast.BREADTH, new SubjectChangeEvent());
            }
        });

        List<Question> allQuestions = questionService.find(new QuestionFilter());
        final Palette<Question> palette = new Palette<Question>("questions", Model.ofList(examination.getQuestions()), new CollectionModel<Question>(
                allQuestions), QuestionChoiceRenderer.INSTANCE, 15, false, true);
        palette.add(new DefaultTheme());
        form.add(palette);

        form.add(new SubmitLink("save") {
            @Override
            public void onSubmit() {
                super.onSubmit();
                examination.setAccountProfile(AuthorizedSession.get().getLoggedUser());
                examinationService.saveOrUpdate(examination);
                setResponsePage(new ExaminationsPage());
            }
        });
	}

	@AuthorizeAction(roles = { "ADMIN" }, action = Action.ENABLE)
	private class ExaminationForm<T> extends Form<T> {

		public ExaminationForm(String id, IModel<T> model) {
			super(id, model);
		}
	}
}
