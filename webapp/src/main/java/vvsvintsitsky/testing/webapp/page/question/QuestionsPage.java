package vvsvintsitsky.testing.webapp.page.question;

import javax.inject.Inject;

import org.apache.wicket.markup.html.link.Link;

import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.webapp.page.AbstractPage;
import vvsvintsitsky.testing.webapp.page.question.panel.QuestionsListPanel;

public class QuestionsPage extends AbstractPage {

    @Inject
    private QuestionService questionService;

    public QuestionsPage() {
        super();
        add(new QuestionsListPanel("list-panel"));

        add(new Link("create") {
            @Override
            public void onClick() {
                setResponsePage(new QuestionEditPage(new Question()));
            }
        });
    }
}
