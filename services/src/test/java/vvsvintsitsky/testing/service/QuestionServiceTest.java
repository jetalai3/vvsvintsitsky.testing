package vvsvintsitsky.testing.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vvsvintsitsky.testing.dataaccess.filters.QuestionFilter;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Question_;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.service.QuestionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:service-context-test.xml")
public class QuestionServiceTest {

	@Inject
	private QuestionService questionService;

	@Inject
	private SubjectService subjectService;
	
	@Test
	public void createQuestion(){
		clearDataTables();
		
		Question question = new Question();
		
		question.setSubject(fillDatabaseWithSubjects(1).get(0));
		question.setText("question text");
		questionService.saveOrUpdate(question);
		
		clearDataTables();
	}
	
	@Test
	public void searchQuestions(){
		clearDataTables();
		
		List<Question> questions = fillDatabaseWithQuestions(20);
		
		QuestionFilter questionFilter = new QuestionFilter();
		Question question = questions.get(questions.size() - 1);
		
		questionFilter.setSubjectName(question.getSubject().getName());
		questionFilter.setText(question.getText());
		questionFilter.setSubjectName(question.getSubject().getName());;
		questionFilter.setFetchSubject(true);;
		questionFilter.setSortProperty(Question_.id);
		questionFilter.setSortOrder(false);
		questionFilter.setLimit(30);
		
		if (questionService.find(questionFilter).size() != 1) {
			throw new IllegalStateException("more than 1 question found");
		}
		
		clearDataTables();
	}
	
	@Test
	public void updateQuestion(){
		clearDataTables();
		
		List<Question> questions = fillDatabaseWithQuestions(20);

		Question question = questions.get(questions.size() - 1);
		Question updQuestion = questions.get(questions.size() - 2);
		
		updQuestion.setText(question.getText());
		
		try {
			questionService.update(updQuestion);
		} catch (PersistenceException e) {
			System.out.println("failed to update question, reason: " + e.getCause().getCause().getMessage());
		}
		
		clearDataTables();
	}
	
	@Test
	public void deleteQuestion(){
		clearDataTables();
		
		List<Question> questions = fillDatabaseWithQuestions(20);

		Question question = questions.get(0);
		
		try {
			questionService.delete(question.getId());
		} catch (PersistenceException e) {
			System.out.println("failed to delete question, reason: " + e.getCause().getCause().getMessage());
		}
		
		clearDataTables();		
	}
	
	@Test
	public void countQuestions(){
		clearDataTables();
		
		List<Question> questions = fillDatabaseWithQuestions(20);

		QuestionFilter questionFilter = new QuestionFilter();
		
		if (questions.size() != questionService.count(questionFilter)) {
			throw new IllegalStateException("not all questions found");
		}
		
		clearDataTables();
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
		questionService.deleteAll();
		subjectService.deleteAll();
	}
}
