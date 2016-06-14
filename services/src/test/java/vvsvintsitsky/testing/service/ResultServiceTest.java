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
import vvsvintsitsky.testing.datamodel.LocalTexts;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Result;
import vvsvintsitsky.testing.datamodel.Result_;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.UserRole;
import vvsvintsitsky.testing.datamodel.VariousTexts;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.service.AnswerService;
import vvsvintsitsky.testing.service.ExaminationService;
import vvsvintsitsky.testing.service.ResultService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:service-context-test.xml")
public class ResultServiceTest {

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
	public void createResult(){
		clearDataTables();
		
		Result result = new Result();
		Subject subject = fillDatabaseWithSubjects(1).get(0);
		AccountProfile accountProfile = fillDatabaseWithAccountsAndAccountProfiles(1).get(0);
		result.setAccountProfile(accountProfile);
		
		result.setExamination(fillDatabaseWithExaminations(1, accountProfile, subject).get(0));
		result.setPoints(100);
		Question question = fillDatabaseWithQuestions(1, subject).get(0);
		result.setAnswers(fillDatabaseWithAnswers(10, question));
		resultService.saveOrUpdate(result);
		
		clearDataTables();
	}
	
	@Test
	public void searchResults(){
		clearDataTables();
		
		List<Result> results = fillDatabaseWithResults(1);
		ResultFilter resultFilter = new ResultFilter();
		Result result = results.get(results.size() - 1);
		resultFilter.setLanguage("ru");
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
		
		List<Result> results = fillDatabaseWithResults(5);

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
		
		List<Result> results = fillDatabaseWithResults(5);

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
		
		List<Result> results = fillDatabaseWithResults(5);

		ResultFilter resultFilter = new ResultFilter();
		
		if (results.size() != resultService.count(resultFilter)) {
			throw new IllegalStateException("not all results found");
		}
		
		clearDataTables();
	}
	
	private List<Examination> fillDatabaseWithExaminations(int quantity, AccountProfile accountProfile,
			Subject subject) {
		List<Examination> examinations = new ArrayList<Examination>();
		// List<Question> questions = fillDatabaseWithQuestions(quantity);
		LocalTexts examinationNames;
		Examination examination;

		for (int i = 0; i < quantity; i++) {
			examination = new Examination();
			examination.setAccountProfile(accountProfile);
			examination.setBeginDate(new Date());
			examination.setEndDate(new Date());
			examination.setSubject(subject);
			
			examinationNames = createLocalTexts("ex" + i);
			examination.setExaminationNames(examinationNames);
			
			List<Question> questions = fillDatabaseWithQuestions(quantity, subject);
			examination.setQuestions(questions);

			examinations.add(examination);
			examinationService.saveOrUpdate(examination);
		}
		return examinations;
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

	private List<LocalTexts> fillDatabaseWithLocalTexts(int quantity, String text) {
		List<LocalTexts> localTexts = new ArrayList<LocalTexts>();
		LocalTexts localText;
		VariousTexts rusText;
		VariousTexts engText;

		for (int i = 0; i < quantity; i++) {
			rusText = new VariousTexts();
			rusText.setTxt(text + "rus" + i);
			engText = new VariousTexts();
			engText.setTxt(text + "eng" + i);

			localText = new LocalTexts();
			localText.setRusText(rusText);
			localText.setEngText(engText);

			localTextsService.insert(localText);
			localTexts.add(localText);
		}

		return localTexts;
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
	
	private List<Result> fillDatabaseWithResults(int quantity){
		List<Result> results = new ArrayList<Result>();
		AccountProfile accountProfile = fillDatabaseWithAccountsAndAccountProfiles(1).get(0);
		Subject subject = fillDatabaseWithSubjects(1).get(0);
		Question question = fillDatabaseWithQuestions(1, subject).get(0);
		List<Examination> examinations = fillDatabaseWithExaminations(quantity, accountProfile, subject);
		List<AccountProfile> accountProfiles = fillDatabaseWithAccountsAndAccountProfiles(quantity);
		List<Answer> answers = fillDatabaseWithAnswers(2, question);
		
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
