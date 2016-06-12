package vvsvintsitsky.testing.webapp.page.answer.panel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;

import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.service.AnswerService;
import vvsvintsitsky.testing.webapp.common.events.AnswerAddEvent;
import vvsvintsitsky.testing.webapp.page.answer.AnswerEditPanel;

public class AnswersListPanel extends Panel {

	@Inject
	private AnswerService answerService;

	private List<Answer> answers;

	private Question question;

	public AnswersListPanel(String id, Question question) {
		super(id);

		this.question = question;

		WebMarkupContainer rowsContainer = new WebMarkupContainer("rowsContainer");
		rowsContainer.setOutputMarkupId(true);

		answers = new ArrayList<Answer>(this.question.getAnswers());

		ListDataProvider<Answer> listDataProvider = new ListDataProvider<Answer>(answers);
		DataView<Answer> dataView = new DataView<Answer>("rows", listDataProvider) {
			@Override
			protected void populateItem(Item<Answer> item) {
				Answer answer = item.getModelObject();

				item.add(new Label("id", answer.getId()));
				item.add(new Label("text", answer.getText()));
				CheckBox checkbox = new CheckBox("correct", Model.of(answer.getCorrect()));
				item.add(checkbox.setEnabled(false));
				ModalWindow modalWindow = new ModalWindow("modal");
				item.add(modalWindow);
				item.add(new AjaxLink<Void>("edit-link") {

					private static final long serialVersionUID = -4197818843372247766L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						modalWindow.setContent(new AnswerEditPanel(modalWindow, answer));
						modalWindow.show(target);

					}
				});

				item.add(new AjaxLink("delete-link") {

					private static final long serialVersionUID = -343889365476492210L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						try {
							answers.remove(answer);
							question.setAnswers(answers);
							if (answer.getId() != null) {
								answerService.delete(answer.getId());
							}

						} catch (PersistenceException e) {
							System.out.println("caught PersistenceException");
						}
						target.add(rowsContainer);

					}
				});

				modalWindow.setWindowClosedCallback(new WindowClosedCallback() {

					private static final long serialVersionUID = 8965470088247585358L;

					@Override
					public void onClose(AjaxRequestTarget target) {

						target.add(rowsContainer);
					}
				});
			}

		};

		rowsContainer.add(dataView);

		add(rowsContainer);

	}

	@Override
	public void onEvent(IEvent<?> event) {
		if (event.getPayload() instanceof AnswerAddEvent) {
			Answer answer = ((AnswerAddEvent) event.getPayload()).getAnswer();

			if (!(this.question.getAnswers().contains(answer))) {
				this.question.getAnswers().add(answer);
			}

			answers.clear();

			answers.addAll(question.getAnswers());

		}

	}

}
