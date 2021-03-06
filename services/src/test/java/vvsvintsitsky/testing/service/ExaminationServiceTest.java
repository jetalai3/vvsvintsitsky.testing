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

import vvsvintsitsky.testing.dataaccess.filters.ExaminationFilter;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Examination_;
import vvsvintsitsky.testing.datamodel.LocalTexts;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Result;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.UserRole;
import vvsvintsitsky.testing.datamodel.VariousTexts;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.service.ExaminationService;
import vvsvintsitsky.testing.service.QuestionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:service-context-test.xml")
public class ExaminationServiceTest {

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
	public void searchExamination(){
		clearDataTables();
		
		AccountProfile accountProfile = fillDatabaseWithAccountsAndAccountProfiles(1).get(0);
		Subject subject = fillDatabaseWithSubjects(1).get(0);
		List<Examination> examinations = fillDatabaseWithExaminations(1, accountProfile, subject);
		
		ExaminationFilter examinationFilter = new ExaminationFilter();
		Examination examination = examinations.get(examinations.size() - 1);
		
		examinationFilter.setBeginDate(examination.getBeginDate());
		examinationFilter.setEndDate(examination.getEndDate());
//		examinationFilter.setName(examination.getName());
		examinationFilter.setLanguage("ru");
		examinationFilter.setName(examination.getExaminationNames().getText("ru"));
		examinationFilter.setAccountProfileId(examination.getAccountProfile().getId());
		examinationFilter.setSubjectId(examination.getSubject().getId());
		examinationFilter.setIsFetchAccountProfile(true);
		examinationFilter.setSortProperty(Examination_.id);
		examinationFilter.setSortOrder(false);
		examinationFilter.setLimit(30);
		
		System.out.println(examinationService.find(examinationFilter).size());
		if (examinationService.find(examinationFilter).size() != 1) {
			throw new IllegalStateException("more than 1 examination found");
		}
		
		clearDataTables();
	}
	
	@Test
	public void updateExamination(){
		clearDataTables();

		AccountProfile accountProfile = fillDatabaseWithAccountsAndAccountProfiles(1).get(0);
		Subject subject = fillDatabaseWithSubjects(1).get(0);
		List<Examination> examinations = fillDatabaseWithExaminations(2, accountProfile, subject);

		Examination examination = examinations.get(examinations.size() - 1);
		Examination updExamination = examinations.get(examinations.size() - 2);
		
		updExamination.setBeginDate(examination.getBeginDate());;
		
		try {
			examinationService.update(updExamination);
		} catch (PersistenceException e) {
			System.out.println("failed to update examination, reason: " + e.getCause().getCause().getMessage());
		}
		
		clearDataTables();
	}
	
	@Test
	public void deleteExamination(){
		clearDataTables();
		
		AccountProfile accountProfile = fillDatabaseWithAccountsAndAccountProfiles(1).get(0);
		Subject subject = fillDatabaseWithSubjects(1).get(0);
		List<Examination> examinations = fillDatabaseWithExaminations(1, accountProfile, subject);
		
		Examination examination = examinations.get(0);

		try {
			examinationService.delete(examination.getId());
		} catch (PersistenceException e) {
			System.out.println("failed to delete examination, reason: " + e.getCause().getCause().getMessage());
		}
		
		clearDataTables();
	}
	
	@Test
	public void countExaminations(){
		clearDataTables();
		
		AccountProfile accountProfile = fillDatabaseWithAccountsAndAccountProfiles(1).get(0);
		Subject subject = fillDatabaseWithSubjects(1).get(0);
		List<Examination> examinations = fillDatabaseWithExaminations(1, accountProfile, subject);

		ExaminationFilter examinationFilter = new ExaminationFilter();
		
		if (examinations.size() != examinationService.count(examinationFilter)) {
			throw new IllegalStateException("not all examinations found");
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
