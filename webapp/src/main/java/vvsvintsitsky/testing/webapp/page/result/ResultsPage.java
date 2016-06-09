package vvsvintsitsky.testing.webapp.page.result;

import javax.inject.Inject;

import org.apache.wicket.markup.html.link.Link;

import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Result;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.service.ResultService;
import vvsvintsitsky.testing.webapp.page.AbstractPage;
import vvsvintsitsky.testing.webapp.page.question.panel.QuestionsListPanel;
import vvsvintsitsky.testing.webapp.page.result.panel.ResultsListPanel;

public class ResultsPage extends AbstractPage {

	@Inject
	private ResultService resultService;

	public ResultsPage() {
		super();
		add(new ResultsListPanel("list-panel"));
	}
}
