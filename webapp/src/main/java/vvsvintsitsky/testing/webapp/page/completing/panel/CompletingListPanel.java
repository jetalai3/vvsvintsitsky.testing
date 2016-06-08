package vvsvintsitsky.testing.webapp.page.completing.panel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.extensions.markup.html.form.palette.Palette;
import org.apache.wicket.extensions.markup.html.form.palette.theme.DefaultTheme;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.CollectionModel;

import vvsvintsitsky.testing.dataaccess.filters.QuestionFilter;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Result;
import vvsvintsitsky.testing.service.ExaminationService;
import vvsvintsitsky.testing.service.QuestionService;
import vvsvintsitsky.testing.service.ResultService;
import vvsvintsitsky.testing.webapp.app.AuthorizedSession;
import vvsvintsitsky.testing.webapp.common.AnswerChoiceRenderer;
import vvsvintsitsky.testing.webapp.common.QuestionChoiceRenderer;

public class CompletingListPanel extends Panel {

	@Inject
	private QuestionService questionService;

	@Inject
	private ResultService resultService;
	
	private Question question;

	private Examination examination;

	private List<Question> questions;

	private Result result;
	
	private List<Answer> mistakes;
	
	private QuestionFilter questionFilter;

	private Integer length;

	private Integer position;

	public CompletingListPanel(String id, Examination examination) {
		super(id);
		this.examination = examination;
		result = new Result();
		result.setAccountProfile(AuthorizedSession.get().getLoggedUser());
		result.setExamination(examination);
		mistakes = new ArrayList<Answer>();
		this.questions = examination.getQuestions();
		questionFilter = new QuestionFilter();
		questionFilter.setFetchAnswers(true);
		
		for(Question question : examination.getQuestions()){
//			question = questionService.getQuestionWithAnswers(question.getId());
			questionFilter.setId(question.getId());
			question = questionService.find(questionFilter).get(0);
		}

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		WebMarkupContainer rowsContainer = new WebMarkupContainer("rowsContainer");
		rowsContainer.setOutputMarkupId(true);
		add(rowsContainer);

		SListIterator sListiterator = new SListIterator(questions);

		if (sListiterator.hasNext()) {
			question = sListiterator.next();
		}

//		questionFilter = new QuestionFilter();
//		questionFilter.setFetchAnswers(true);
//		questionFilter.setId(question.getId());

		question = questionService.find(questionFilter).get(0);

		Model<String> questionModel = Model.of(question.getText());
		rowsContainer.add(new Label("question-text", questionModel));

		List<Answer> answers = new ArrayList<Answer>(question.getAnswers());

		DataView<Answer> dataView = new DataView<Answer>("rows",
				new ListDataProvider<Answer>(answers)) {
			@Override
			protected void populateItem(Item<Answer> item) {
				Answer answer = (Answer) item.getModelObject();
				Form<Answer> form = new Form<Answer>("form", new CompoundPropertyModel<>(answer));

				item.add(new Label("id", answer.getId()));
				item.add(new Label("answer-text", answer.getText()));
				form.add(new AjaxCheckBox("answered"){
					@Override
					protected void onUpdate(AjaxRequestTarget target) {
					}
				});
				item.add(form);
			}
		};
		rowsContainer.add(new AjaxLink("next-question") {

			private static final long serialVersionUID = 8930268252094829030L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if (sListiterator.hasNext()) {
					question = sListiterator.next();
					System.out.println("1");
//					questionFilter.setId(question.getId());
//					question = questionService.find(questionFilter).get(0);

					Iterator<Item<Answer>> it = dataView.getItems();
					System.out.println("2");

					Answer a;
					while(it.hasNext()){
						a = it.next().getModelObject();
						if(a.getCorrect() != a.getAnswered() && a.getAnswered() == true){
							mistakes.add(a);
						}
						//System.out.println(a.getText()+ " correct:" + a.getCorrect() + " answered:"+a.getAnswered());
					}
					System.out.println("3");

					answers.clear();
					System.out.println("4");

					answers.addAll(question.getAnswers());
					System.out.println("5");

					questionModel.setObject(question.getText());

				}
				target.add(rowsContainer);

			}
		});
		rowsContainer.add(new AjaxLink("previous-question") {

			private static final long serialVersionUID = 8930268252094829030L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				if (sListiterator.hasPrevious()) {
					question = sListiterator.previous();
//					questionFilter.setId(question.getId());
//					question = questionService.find(questionFilter).get(0);

					Iterator<Item<Answer>> it = dataView.getItems();
					Answer a;
					while(it.hasNext()){
						a = it.next().getModelObject();
							
							mistakes.remove(a);
					}
					
					answers.clear();
					answers.addAll(question.getAnswers());

					questionModel.setObject(question.getText());
				}
				target.add(rowsContainer);

			}
		});
		rowsContainer.add(new AjaxLink("finish-examination") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				
				target.add(rowsContainer);
				
				Iterator<Item<Answer>> it = dataView.getItems();
				Answer a;
				while(it.hasNext()){
					a = it.next().getModelObject();
					if(a.getCorrect() != a.getAnswered() && a.getAnswered() == true){
						mistakes.add(a);
					}
					//System.out.println(a.getText()+ " correct:" + a.getCorrect() + " answered:"+a.getAnswered());
				}
				
				result.setAnswers(mistakes);
				
				//resultService.insert(result);
			}
		});
		rowsContainer.add(dataView);

	}

	private class SListIterator implements Serializable {

		private static final long serialVersionUID = 1L;
		private List<Question> list;
		private int cursor;

		public SListIterator(List<Question> list) {
			this.list = list;
			cursor = -1;
		}

		public boolean hasPrevious() {
			return cursor > 0;
		}

		public boolean hasNext() {
			return cursor < list.size() - 1;
		}

		public Question previous() {
			if (!hasPrevious())
				throw new NoSuchElementException();
			return list.get(--cursor);
		}

		public Question next() {
			if (!hasNext())
				throw new NoSuchElementException();
			return list.get(++cursor);
		}
	}

	@AuthorizeAction(roles = { "ADMIN" }, action = Action.ENABLE)
	private class QuestionForm<T> extends Form<T> {

		public QuestionForm(String id, IModel<T> model) {
			super(id, model);
		}
	}
}
