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

	private Iterator<Question> iterator;
	
	public CompletingPage(Examination examination) {
        super();
        this.examination = examination;
        
        add(new Label("examination-name", examination.getName()));
        	
       
        iterator = examination.getQuestions().iterator();
        
        	
        WebMarkupContainer rowsContainer = new WebMarkupContainer("rowsContainer");
		rowsContainer.setOutputMarkupId(true);
        
        CompletingListPanel completingListPanel = new CompletingListPanel("list-panel", iterator.next());
		rowsContainer.add(completingListPanel);
		
		
        rowsContainer.add(new AjaxLink("next-question") {
            
        	private static final long serialVersionUID = 8930268252094829030L;
        	
        	@Override
			public void onClick(AjaxRequestTarget target) {
				System.out.println(iterator.next().getText());
        		target.add(rowsContainer);
				
			}
        });
        
    }
}
