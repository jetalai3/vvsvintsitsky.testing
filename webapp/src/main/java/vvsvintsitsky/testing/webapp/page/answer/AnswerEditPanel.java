package vvsvintsitsky.testing.webapp.page.answer;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.service.AnswerService;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.webapp.common.events.AnswerAddEvent;

public class AnswerEditPanel extends Panel {

	@Inject
	private AnswerService answerService;

	private Answer answer;

	private ModalWindow modalWindow;

	public AnswerEditPanel(ModalWindow modalWindow, Answer answer) {
		super(modalWindow.getContentId());
		this.answer = answer;
		this.modalWindow = modalWindow;
	}

	public AnswerEditPanel(ModalWindow modalWindow, Question question) {
		super(modalWindow.getContentId());
		this.answer = new Answer();
		this.modalWindow = modalWindow;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		Form<Answer> form = new Form<Answer>("form", new CompoundPropertyModel<>(answer));
		add(form);

		form.add(new TextField<>("text"));
		CheckBox correctField = new CheckBox("correct");
		form.add(correctField);
		form.add(new AjaxSubmitLink("save") {

			private static final long serialVersionUID = -5210362644590530669L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);

				if (answer.getId() == null) {
					send(getPage(), Broadcast.BREADTH, new AnswerAddEvent(answer));
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
	}
}
