package vvsvintsitsky.testing.webapp.page.completing;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

import vvsvintsitsky.testing.dataaccess.filters.ExaminationFilter;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.service.ExaminationService;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.webapp.page.AbstractPage;
import vvsvintsitsky.testing.webapp.page.completing.panel.CompletingListPanel;
import vvsvintsitsky.testing.webapp.page.examination.panel.ExaminationsListPanel;
import vvsvintsitsky.testing.webapp.page.question.panel.QuestionsListPanel;

public class CompletingPage extends AbstractPage {

	@Inject
	private ExaminationService examinationService;

	private Examination examination;

	public CompletingPage(Examination examination) {
		super();
		this.examination = examination;
		
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		add(new Label("examination-name", examination.getName()));

		

		CompletingListPanel completingListPanel = new CompletingListPanel("list-panel", examination);
		add(completingListPanel);
		

		
	}
}
