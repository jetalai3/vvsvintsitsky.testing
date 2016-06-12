package vvsvintsitsky.testing.webapp.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import vvsvintsitsky.testing.webapp.component.localization.LanguageSelectionComponent;
import vvsvintsitsky.testing.webapp.component.menu.MenuPanel;

public abstract class AbstractPage extends WebPage {

	private static final long serialVersionUID = -1790690697853654508L;

	public AbstractPage() {
		super();
	}

	public AbstractPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new LanguageSelectionComponent("language-select"));

		add(new MenuPanel("menu-panel"));

//		WebMarkupContainer footer = new WebMarkupContainer("footer");
//		add(footer);
//		footer.add(AttributeModifier.append("onclick", "alert('Im clicked')"));

	}

}