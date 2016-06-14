package vvsvintsitsky.testing.webapp.page.answer;

import javax.inject.Inject;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.VariousTexts;
import vvsvintsitsky.testing.service.AnswerService;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.LocalTexts;
import vvsvintsitsky.testing.webapp.app.AuthorizedSession;
import vvsvintsitsky.testing.webapp.common.events.AnswerAddEvent;

public class AnswerEditPanel extends Panel {

	@Inject
	private AnswerService answerService;

	private Answer answer;

	private LocalTexts texts;
	
	private VariousTexts rusText;
	
	private VariousTexts engText;
	
	private ModalWindow modalWindow;
	
	private Logger logger;

	public AnswerEditPanel(ModalWindow modalWindow, Answer answer) {
		super(modalWindow.getContentId());
		this.modalWindow = modalWindow;
		this.logger = LoggerFactory.getLogger(AnswerEditPanel.class);
//		if(answer.getId() != null) {
		if(answer.getCorrect() != null) {
			this.answer = answer;
			this.texts = answer.getAnswerTexts();
			this.rusText = this.texts.getRusText();
			this.engText = this.texts.getEngText();
		} else {
			this.answer = new Answer();
			this.texts = new LocalTexts();
			this.rusText = new VariousTexts();
			this.engText = new VariousTexts();
		}	
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		Form<Answer> form = new Form<Answer>("form", new CompoundPropertyModel<>(answer));
		add(form);

		CheckBox correctField = new CheckBox("correct");
		form.add(correctField);
		
		Form<VariousTexts> formRusText = new Form<VariousTexts>("formRusText", new CompoundPropertyModel<>(rusText));
		form.add(formRusText);
		formRusText.add(new TextField<>("txt").setRequired(true));
		
		Form<VariousTexts> formEngText = new Form<VariousTexts>("formEngText", new CompoundPropertyModel<>(engText));
		form.add(formEngText);
		formEngText.add(new TextField<>("txt").setRequired(true));
		
		form.add(new AjaxSubmitLink("save") {

			private static final long serialVersionUID = -5210362644590530669L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				logger.warn("User {} attepmpted to create/update answer", AuthorizedSession.get().getLoggedUser().getId());

				texts.setRusText(rusText);
				texts.setEngText(engText);
//				answer.setAnswerTexts(texts);
				if (answer.getAnswerTexts() == null) {
					answer.setAnswerTexts(texts);
					send(getPage(), Broadcast.BREADTH, new AnswerAddEvent(answer));
				} else {
					answer.setAnswerTexts(texts);
				}
				

				modalWindow.close(target);
			}
		});

		form.add(new AjaxLink<Object>("cancel") {

			private static final long serialVersionUID = 2020843267475126323L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modalWindow.close(target);
			}
		});
		add(new FeedbackPanel("feedback"));
	}
}
