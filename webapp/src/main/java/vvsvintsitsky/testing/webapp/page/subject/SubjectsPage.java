package vvsvintsitsky.testing.webapp.page.subject;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.markup.html.link.Link;

import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.service.SubjectService;
import vvsvintsitsky.testing.webapp.page.AbstractPage;
import vvsvintsitsky.testing.webapp.page.subject.panel.SubjectsListPanel;

public class SubjectsPage extends AbstractPage {

    @Inject
    private SubjectService subjectService;

    public SubjectsPage() {
        super();      
        
    }
    @Override
	protected void onInitialize() {
		super.onInitialize();
		
		SubjectsListPanel subjectsListPanel = new SubjectsListPanel("list-panel");
		subjectsListPanel.setOutputMarkupId(true);
		add(subjectsListPanel);
		
		addModalWindow(subjectsListPanel);
	}
	
	private void addModalWindow(SubjectsListPanel subjectsListPanel) {
		ModalWindow modalWindow = new ModalWindow("modal");
		add(modalWindow);

		add(new AjaxLink<Void>("create") {

			private static final long serialVersionUID = 8930268252094829030L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modalWindow.setContent(new SubjectEditPanel(modalWindow, new Subject()));
				modalWindow.show(target);
			}
		});

		modalWindow.setWindowClosedCallback(new WindowClosedCallback(){

			private static final long serialVersionUID = -4886514118814449600L;

			@Override
			public void onClose(AjaxRequestTarget target) {
				target.add(subjectsListPanel);

			}
		});
	}
}
