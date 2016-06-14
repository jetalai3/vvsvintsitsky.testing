package vvsvintsitsky.testing.webapp.page.completing;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.webapp.page.AbstractPage;
import vvsvintsitsky.testing.webapp.page.completing.panel.CompletingListPanel;

public class CompletingPage extends AbstractPage {

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

}
