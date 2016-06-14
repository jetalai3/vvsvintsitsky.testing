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

import vvsvintsitsky.testing.dataaccess.filters.SubjectFilter;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.LocalTexts;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Result;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.Subject_;
import vvsvintsitsky.testing.datamodel.UserRole;
import vvsvintsitsky.testing.datamodel.VariousTexts;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:service-context-test.xml")
public class SubjectServiceTest {
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
	public void searchSubjects(){
		clearDataTables();
		
		List<Subject> subjects = fillDatabaseWithSubjects(5);
		
		SubjectFilter subjectFilter = new SubjectFilter();
		Subject subject = subjects.get(subjects.size() - 1);
		
		subjectFilter.setName(subject.getSubjectNames().getText("ru"));
		subjectFilter.setLanguage("ru");
		subjectFilter.setSortProperty(Subject_.id);
		subjectFilter.setSortOrder(false);
		subjectFilter.setLimit(30);
		
		if (subjectService.find(subjectFilter).size() != 1) {
			throw new IllegalStateException("more than 1 subject found");
		}
		
		clearDataTables();
	}
	
	@Test
	public void updateSubject(){
		clearDataTables();
		
		List<Subject> subjects = fillDatabaseWithSubjects(2);

		Subject subject = subjects.get(subjects.size() - 1);
		Subject updSubject = subjects.get(subjects.size() -2);
		
		updSubject.setSubjectNames(subject.getSubjectNames());
		
		try {
			subjectService.update(updSubject);
		} catch (PersistenceException e) {
			System.out.println("failed to update subject, reason: " + e.getCause().getCause().getMessage());
		}
		
		clearDataTables();
	}
	
	@Test
	public void deleteSubject(){
		clearDataTables();

		List<Subject> subjects = fillDatabaseWithSubjects(20);
		Subject subject = subjects.get(0);

		try {
			subjectService.delete(subject.getId());
		} catch (PersistenceException e) {
			System.out.println("failed to delete subject, reason: " + e.getCause().getCause().getMessage());
		}
		
		clearDataTables();
	}
	
	@Test
	public void countSubjects(){
		clearDataTables();
		
		List<Subject> subjects = fillDatabaseWithSubjects(20);

		SubjectFilter subjectFilter = new SubjectFilter();
		
		if (subjects.size() != subjectService.count(subjectFilter)) {
			throw new IllegalStateException("not all subjects found");
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

