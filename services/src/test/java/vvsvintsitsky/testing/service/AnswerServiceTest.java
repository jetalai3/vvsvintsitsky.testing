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
import vvsvintsitsky.testing.datamodel.LocalTexts;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.VariousTexts;
import vvsvintsitsky.testing.service.AnswerService;
import vvsvintsitsky.testing.service.QuestionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:service-context-test.xml")
public class AnswerServiceTest {

	@Inject
	private ExaminationService examinationService;
	@Inject
	private AccountService accountService;
	@Inject
	private SubjectService subjectService;
	@Inject
	private QuestionService questionService;
	@Inject
	private AnswerService answerService;
	@Inject
	private LocalTextsService localTextsService;
	@Inject
	private VariousTextsService variousTextsService;
	@Inject
	private ResultService resultService;

	@Test
	public void createAnswer() {
		clearDataTables();

		
		Subject subject = fillDatabaseWithSubjects(1).get(0);
		Question question = fillDatabaseWithQuestions(1, subject).get(0);
		Answer answer = fillDatabaseWithAnswers(1, question).get(0);
		answer.setQuestion(question);
		
		answerService.saveOrUpdate(answer);

		clearDataTables();
	}

	

	@Test
	public void updateAnswer() {
		clearDataTables();

		Subject subject = fillDatabaseWithSubjects(1).get(0);
		Question question = fillDatabaseWithQuestions(1, subject).get(0);
		List<Answer> answers = fillDatabaseWithAnswers(2, question);

		Answer answer = answers.get(answers.size() - 1);
		Answer updAnswer = answers.get(answers.size() - 2);

		updAnswer.setAnswerTexts(answer.getAnswerTexts());
		updAnswer.setQuestion(question);
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

		Subject subject = fillDatabaseWithSubjects(1).get(0);
		Question question = fillDatabaseWithQuestions(1, subject).get(0);
		List<Answer> answers = fillDatabaseWithAnswers(2, question);

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

		Subject subject = fillDatabaseWithSubjects(1).get(0);
		Question question = fillDatabaseWithQuestions(1, subject).get(0);
		List<Answer> answers = fillDatabaseWithAnswers(2, question);
		
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

		Subject subject = fillDatabaseWithSubjects(1).get(0);
		Question question = fillDatabaseWithQuestions(1, subject).get(0);
		List<Answer> answers = fillDatabaseWithAnswers(2, question);

		
		AnswerFilter answerFilter = new AnswerFilter();

		if ((question.getAnswers().size() + answers.size()) != answerService.count(answerFilter)) {
			throw new IllegalStateException("not all answers found");
		}

		clearDataTables();
	}

	private List<Question> fillDatabaseWithQuestions(int quantity, Subject subject) {
		List<Question> questions = new ArrayList<Question>();
		Question question;
		LocalTexts questionTexts;

		for (int i = 0; i < quantity; i++) {
			question = new Question();
			question.setSubject(subject);
			questions.add(question);

			questionTexts = createLocalTexts("qu" + i);
			question.setQuestionTexts(questionTexts);

			questionService.saveOrUpdate(question);
			question.setAnswers(fillDatabaseWithAnswers(quantity, question));
		}
		return questions;
	}

	private LocalTexts createLocalTexts(String text) {
		LocalTexts localText;
		VariousTexts rusText;
		VariousTexts engText;
		
		rusText = new VariousTexts();
		rusText.setTxt("rus" +text);
		engText = new VariousTexts();
		engText.setTxt("eng" +text);

		localText = new LocalTexts();
		localText.setRusText(rusText);
		localText.setEngText(engText);
		
		return localText;
	}
	
	private List<Answer> fillDatabaseWithAnswers(int quantity, Question question) {
		List<Answer> answers = new ArrayList<Answer>();
		Answer answer;
		LocalTexts answerTexts;

		for (int i = 0; i < quantity; i++) {
			answer = new Answer();
			answer.setCorrect(true);
			answer.setQuestion(question);	
			answerTexts = createLocalTexts("ans" + i);
			answer.setAnswerTexts(answerTexts);
			answerService.saveOrUpdate(answer);
			answers.add(answer);
		}
		return answers;
	}

	private List<Subject> fillDatabaseWithSubjects(int quantity) {
		List<Subject> subjects = new ArrayList<Subject>();
		Subject subject;
		LocalTexts subjectNames;

		for (int i = 0; i < quantity; i++) {
			subject = new Subject();
			subjectNames = createLocalTexts("sub" + i);
			subject.setSubjectNames(subjectNames);
			subjects.add(subject);
			subjectService.saveOrUpdate(subject);
		}
		return subjects;
	}

	private void clearDataTables() {
		resultService.deleteAll();
		answerService.deleteAll();
		questionService.deleteAll();
		examinationService.deleteAll();
		subjectService.deleteAll();
		accountService.deleteAll();
		localTextsService.deleteAll();
		variousTextsService.deleteAll();
		
	}

}
