package vvsvintsitsky.testing.webapp.page.completing;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;

import vvsvintsitsky.testing.dataaccess.filters.ExaminationFilter;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.service.ExaminationService;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.webapp.common.events.LanguageChangedEvent;
import vvsvintsitsky.testing.webapp.common.iterator.CustomIterator;
import vvsvintsitsky.testing.webapp.page.AbstractPage;
import vvsvintsitsky.testing.webapp.page.completing.panel.CompletingListPanel;
import vvsvintsitsky.testing.webapp.page.examination.panel.ExaminationsListPanel;
import vvsvintsitsky.testing.webapp.page.question.panel.QuestionsListPanel;

public class CompletingPage extends AbstractPage {

	@Inject
	private ExaminationService examinationService;

	private Examination examination;

	Model<String> model;
	
	private String language;
	
	public CompletingPage(Examination examination) {
		super();
		this.examination = examination;
		
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		language = Session.get().getLocale().getLanguage();
		Model<String> model = new Model<String>(examination.getExaminationNames().getText(language));
		add(new Label("examination-name", model));

		

		CompletingListPanel completingListPanel = new CompletingListPanel("list-panel", examination);
		add(completingListPanel);
		

		
	}
	
//	@Override
//	public void onEvent(IEvent<?> event) {
//		if (event.getPayload() instanceof LanguageChangedEvent) {
//			String language = Session.get().getLocale().getLanguage();
//			model.setObject(examination.getExaminationNames().getText(language));
//			
//		}
//
//	}
}
