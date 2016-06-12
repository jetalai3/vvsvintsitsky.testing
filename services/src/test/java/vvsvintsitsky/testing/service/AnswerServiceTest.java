package vvsvintsitsky.testing.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vvsvintsitsky.testing.dataaccess.filters.AnswerFilter;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Answer_;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.service.AnswerService;
import vvsvintsitsky.testing.service.QuestionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:service-context-test.xml")
public class AnswerServiceTest {

	@Inject
	private AnswerService answerService;

	@Inject
	private QuestionService questionService;
	
	@Inject
	private SubjectService subjectService;

	@Test
	public void createAnswer() {
		clearDataTables();

		Answer answer = new Answer();
		answer.setCorrect(true);
		answer.setText("answer text");
		answer.setQuestion(fillDatabaseWithQuestions(1).get(0));
		
		answerService.saveOrUpdate(answer);

		clearDataTables();
	}

	@Test
	public void searchAnswers() {
		clearDataTables();

		List<Answer> answers = fillDatabaseWithAnswers(20);

		AnswerFilter answerFilter = new AnswerFilter();
		Answer answer = answers.get(answers.size() - 1);

		answerFilter.setCorrect(answer.getCorrect());
		answerFilter.setText(answer.getText());
		answerFilter.setQuestionId(answer.getQuestion().getId());
		answerFilter.setFetchQuestion(true);
		answerFilter.setSortProperty(Answer_.id);
		answerFilter.setSortOrder(false);
		answerFilter.setLimit(30);

		if (answerService.find(answerFilter).size() != 1) {
			throw new IllegalStateException("more than 1 answer found");
		}

		clearDataTables();
	}

	@Test
	public void updateAnswer() {
		clearDataTables();

		List<Answer> answers = fillDatabaseWithAnswers(20);

		Answer answer = answers.get(answers.size() - 1);
		Answer updAnswer = answers.get(answers.size() - 2);

		updAnswer.setText(answer.getText());
		updAnswer.setQuestion(fillDatabaseWithQuestions(1).get(0));
		updAnswer.setCorrect(true);

		try {
			answerService.update(updAnswer);
		} catch (PersistenceException e) {
			System.out.println("failed to update answer, reason: " + e.getCause().getCause().getMessage());
		}

		clearDataTables();
	}

	@Test
	public void deleteAnswer() {
		clearDataTables();

		List<Answer> answers = fillDatabaseWithAnswers(20);

		Answer answer = answers.get(0);

		try {
			answerService.delete(answer.getId());
		} catch (PersistenceException e) {
			System.out.println("failed to delete answer, reason: " + e.getCause().getCause().getMessage());
		}

		clearDataTables();
	}

	@Test
	public void deleteAnswerByQuestionId() {
		clearDataTables();

		List<Answer> answers = fillDatabaseWithAnswers(20);
		
		Answer answer = answers.get(0);
		
		try {
			answerService.deleteAnswerByQuestionId(answer.getQuestion().getId());
		} catch (PersistenceException e) {
			System.out.println("failed to delete answer, reason: " + e.getCause().getCause().getMessage());
		}
		
		clearDataTables();
	}

	@Test
	public void countAnswers() {
		clearDataTables();

		List<Answer> answers = fillDatabaseWithAnswers(20);

		AnswerFilter answerFilter = new AnswerFilter();

		if (answers.size() != answerService.count(answerFilter)) {
			throw new IllegalStateException("not all answers found");
		}

		clearDataTables();
	}

	private List<Answer> fillDatabaseWithAnswers(int quantity) {
		List<Answer> answers = new ArrayList<Answer>();
		Answer answer;

		List<Question> questions = fillDatabaseWithQuestions(quantity);

		for (int i = 0; i < quantity; i++) {
			answer = new Answer();
			answer.setCorrect(true);
			answer.setText("answer " + i);
			answer.setQuestion(questions.get(i));
			answerService.saveOrUpdate(answer);
			answers.add(answer);
		}

		return answers;
	}

	private List<Question> fillDatabaseWithQuestions(int quantity) {
		List<Question> questions = new ArrayList<Question>();
		Question question;

		List<Subject> subjects = fillDatabaseWithSubjects(quantity);
		
		for (int i = 0; i < quantity; i++) {
			question = new Question();
			question.setSubject(subjects.get(i));
			question.setText("question " + i);
			questions.add(question);
			questionService.saveOrUpdate(question);
		}
		return questions;
	}

	private List<Subject> fillDatabaseWithSubjects(int quantity) {
		List<Subject> subjects = new ArrayList<Subject>();
		Subject subject;

		for (int i = 0; i < quantity; i++) {
			subject = new Subject();
			subject.setName("subject " + i);
			subjects.add(subject);
			subjectService.saveOrUpdate(subject);
		}

		return subjects;

	}

	private void clearDataTables() {
		answerService.deleteAll();
		questionService.deleteAll();
		subjectService.deleteAll();
	}

}
