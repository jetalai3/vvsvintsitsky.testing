package vvsvintsitsky.testing.webapp.page.examination;

import javax.inject.Inject;

import org.apache.wicket.markup.html.link.Link;

import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.service.ExaminationService;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.webapp.page.AbstractPage;
import vvsvintsitsky.testing.webapp.page.examination.panel.ExaminationsListPanel;
import vvsvintsitsky.testing.webapp.page.question.panel.QuestionsListPanel;

public class ExaminationsPage extends AbstractPage {

    @Inject
    private ExaminationService examinationService;

    public ExaminationsPage() {
        super();
        add(new ExaminationsListPanel("list-panel"));

        add(new Link("create") {
            @Override
            public void onClick() {
                setResponsePage(new ExaminationEditPage(new Examination()));
            }
        });
    }
}
