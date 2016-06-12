package vvsvintsitsky.testing.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vvsvintsitsky.testing.dataaccess.filters.ResultFilter;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Result;
import vvsvintsitsky.testing.datamodel.Result_;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.UserRole;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.service.AnswerService;
import vvsvintsitsky.testing.service.ExaminationService;
import vvsvintsitsky.testing.service.ResultService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:service-context-test.xml")
public class ResultServiceTest {

	@Inject
	private ResultService resultService;
	
	@Inject
	private QuestionService questionService;
	
	@Inject
	private SubjectService subjectService;
	
	@Inject
	private ExaminationService examinationService;
	
	@Inject
	private AccountService accountService;
	
	@Inject
	private AnswerService answerService;
	
	@Test
	public void createResult(){
		clearDataTables();
		
		Result result = new Result();
		
		result.setAccountProfile(fillDatabaseWithAccountsAndAccountProfiles(1).get(0));
		result.setExamination(fillDatabaseWithExaminations(1).get(0));
		result.setPoints(100);
		result.setAnswers(fillDatabaseWithAnswers(10));
		resultService.saveOrUpdate(result);
		
		clearDataTables();
	}
	
	@Test
	public void searchResults(){
		clearDataTables();
		
		List<Result> results = fillDatabaseWithResults(20);
		ResultFilter resultFilter = new ResultFilter();
		Result result = results.get(results.size() - 1);
		
		resultFilter.setAccountProfileId(result.getAccountProfile().getId());
		resultFilter.setPoints(result.getPoints());
		resultFilter.setExaminationId(result.getExamination().getId());
		resultFilter.setIsFetchAccountProfile(true);
		resultFilter.setIsFetchExaminations(true);
		resultFilter.setSortProperty(Result_.id);
		resultFilter.setSortOrder(false);
		resultFilter.setLimit(30);
		
		if (resultService.find(resultFilter).size() != 1) {
			throw new IllegalStateException("more than 1 result found");
		}
		
		clearDataTables();
	}
	
	@Test
	public void updateResult(){
		clearDataTables();
		
		List<Result> results = fillDatabaseWithResults(20);

		Result result = results.get(results.size() - 1);
		Result updResult = results.get(results.size() - 2);
		
		updResult.setPoints(result.getPoints());
		
		try {
			resultService.update(updResult);
		} catch (PersistenceException e) {
			System.out.println("failed to update result, reason: " + e.getCause().getCause().getMessage());
		}
		
		clearDataTables();
	}
	
	@Test
	public void deleteResult(){
		clearDataTables();
		
		List<Result> results = fillDatabaseWithResults(20);

		Result result = results.get(0);
		
		try {
			resultService.delete(result.getId());
		} catch (PersistenceException e) {
			System.out.println("failed to delete result, reason: " + e.getCause().getCause().getMessage());
		}
		
		clearDataTables();
	}
	
	@Test
	public void countResults(){
		clearDataTables();
		
		List<Result> results = fillDatabaseWithResults(20);

		ResultFilter resultFilter = new ResultFilter();
		
		if (results.size() != resultService.count(resultFilter)) {
			throw new IllegalStateException("not all results found");
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
	
	private List<Result> fillDatabaseWithResults(int quantity){
		List<Result> results = new ArrayList<Result>();
		List<Examination> examinations = fillDatabaseWithExaminations(quantity);
		List<AccountProfile> accountProfiles = fillDatabaseWithAccountsAndAccountProfiles(quantity);
		List<Answer> answers = fillDatabaseWithAnswers(quantity);
		
		Result result;
		
		for(int i = 0; i < quantity; i++){
			result = new Result();
			result.setAccountProfile(accountProfiles.get(i));
			result.setExamination(examinations.get(i));
			result.setAnswers(answers);
			result.setPoints(i);
			resultService.saveOrUpdate(result);
			results.add(result);
		}
		return results;
	}
	
	private List<Examination> fillDatabaseWithExaminations(int quantity){
		List<Examination> examinations = new ArrayList<Examination>();
		List<AccountProfile> accountProfiles = fillDatabaseWithAccountsAndAccountProfiles(quantity);
		List<Question> questions = fillDatabaseWithQuestions(quantity);
		List<Subject> subjects = fillDatabaseWithSubjects(quantity);
		
		Examination examination;
		
		for(int i = 0; i < quantity; i++){
			examination = new Examination();
			examination.setName("examination " + i);
			examination.setAccountProfile(accountProfiles.get(i));
			examination.setBeginDate(new Date());
			examination.setEndDate(new Date());
			examination.setSubject(subjects.get(i));
			examination.setQuestions(questions);
			
			examinations.add(examination);
			examinationService.saveOrUpdate(examination);
		}
		return examinations;
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
	
	
	private List<AccountProfile> fillDatabaseWithAccountsAndAccountProfiles(int quantity) {
		List<AccountProfile> profiles = new ArrayList<AccountProfile>();
		Account account;
		AccountProfile profile;

		for (int i = 0; i < quantity; i++) {
			account = new Account();
			account.setEmail("email" + i);
			account.setPassword("password" + i);
			account.setRole(UserRole.ADMIN);

			profile = new AccountProfile();
			profile.setFirstName("firstName" + i);
			profile.setLastName("lastName" + i);

			accountService.register(account, profile);
			profiles.add(profile);
		}

		return profiles;
	}
	
	private void clearDataTables() {
		answerService.deleteAll();
		questionService.deleteAll();
		resultService.deleteAll();
		examinationService.deleteAll();
		subjectService.deleteAll();
		accountService.deleteAll();
	}
}
