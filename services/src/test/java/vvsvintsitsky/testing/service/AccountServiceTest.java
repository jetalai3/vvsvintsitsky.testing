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

import vvsvintsitsky.testing.dataaccess.filters.AccountFilter;
import vvsvintsitsky.testing.dataaccess.filters.AccountProfileFilter;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.AccountProfile_;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.LocalTexts;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Result;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.UserRole;
import vvsvintsitsky.testing.datamodel.VariousTexts;
import vvsvintsitsky.testing.service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:service-context-test.xml")
public class AccountServiceTest {

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
	
	@Test
	public void testRegistration() {
		clearDataTables();

		AccountProfile adminProfile = new AccountProfile();
		Account adminAccount = new Account();

		adminProfile.setFirstName("admin");
		adminProfile.setLastName("admin");

		adminAccount.setEmail("admin");
		adminAccount.setPassword("admin");
		adminAccount.setRole(UserRole.ADMIN);
		accountService.register(adminAccount, adminProfile);

		AccountProfile userProfile = new AccountProfile();
		Account userAccount = new Account();

		userProfile.setFirstName("user");
		userProfile.setLastName("user");

		userAccount.setEmail("user");
		userAccount.setPassword("user");
		userAccount.setRole(UserRole.USER);
		accountService.register(userAccount, userProfile);

		clearDataTables();

	}

	@Test
	public void searchProfiles() {
		clearDataTables();

		List<AccountProfile> profiles = fillDatabaseWithAccountsAndAccountProfiles(20);

		AccountProfileFilter filter = new AccountProfileFilter();
		AccountProfile profile = profiles.get(profiles.size() - 1);

		filter.setFirstName(profile.getFirstName());
		filter.setLastName(profile.getLastName());
		filter.setEmail(profile.getAccount().getEmail());
		filter.setRole(profile.getAccount().getRole());
		filter.setFetchAccount(true);
		filter.setSortProperty(AccountProfile_.id);
		filter.setSortOrder(false);
		filter.setLimit(30);

		if (accountService.find(filter).size() != 1) {
			throw new IllegalStateException("more than 1 user found");
		}

		clearDataTables();
	}

	@Test
	public void searchAccount() {
		clearDataTables();

		List<AccountProfile> profiles = fillDatabaseWithAccountsAndAccountProfiles(20);

		AccountFilter filter = new AccountFilter();
		Account account = profiles.get(profiles.size() - 1).getAccount();

		filter.setEmail(account.getEmail());
		filter.setPassword(account.getPassword());
		filter.setRole(account.getRole());

		List<Account> result = accountService.find(filter);

		if (result.size() != 1) {
			throw new IllegalStateException("more than 1 user found");
		}

		clearDataTables();
	}

	@Test
	public void updateProfile() {
		clearDataTables();

		List<AccountProfile> profiles = fillDatabaseWithAccountsAndAccountProfiles(20);

		AccountProfile profile = profiles.get(profiles.size() - 1);
		AccountProfile updProfile = profiles.get(profiles.size() - 2);

		updProfile.setFirstName(profile.getFirstName());
		updProfile.setLastName(profile.getLastName());

		try {
			accountService.update(updProfile);
		} catch (PersistenceException e) {
			System.out.println("failed to update profile, reason: " + e.getCause().getCause().getMessage());
		}

		updProfile.setAccount(profile.getAccount());

		try {
			accountService.update(updProfile);
		} catch (PersistenceException e) {
			System.out.println("failed to update profile, reason: " + e.getCause().getCause().getMessage());
		}

		clearDataTables();
	}

	@Test
	public void updateAccount() {
		clearDataTables();

		List<AccountProfile> profiles = fillDatabaseWithAccountsAndAccountProfiles(20);

		Account account = profiles.get(profiles.size() - 1).getAccount();
		Account updAccount = profiles.get(profiles.size() - 2).getAccount();

		updAccount.setPassword(account.getPassword());
		updAccount.setRole(account.getRole());

		try {
			accountService.update(updAccount);
		} catch (PersistenceException e) {
			System.out.println("failed to update account, reason: " + e.getCause().getCause().getMessage());
		}

		updAccount.setEmail(account.getEmail());

		try {
			accountService.update(updAccount);
		} catch (PersistenceException e) {
			System.out.println("failed to update profile, reason: " + e.getCause().getCause().getMessage());
		}

		clearDataTables();
	}

	@Test
	public void delete() {
		clearDataTables();

		List<AccountProfile> profiles = fillDatabaseWithAccountsAndAccountProfiles(20);

		AccountProfile profile = profiles.get(0);

		try {
			accountService.delete(profile.getId());
		} catch (PersistenceException e) {
			System.out.println("failed to delete profile, reason: " + e.getCause().getCause().getMessage());
		}

		profile = profiles.get(1);
		Subject subject = fillDatabaseWithSubjects(1).get(0);
		Examination examination = fillDatabaseWithExaminations(1, profile, subject).get(0);
//		createResult(examination, profile);
		
		
		try {
			accountService.delete(profile.getId());
		} catch (PersistenceException e) {
			System.out.println("failed to delete profile, reason: " + e.getCause().getCause().getMessage());
		}

		clearDataTables();
	}

	@Test
	public void findByEmailAndPassword() {
		clearDataTables();

		List<AccountProfile> profiles = fillDatabaseWithAccountsAndAccountProfiles(20);

		Account account = profiles.get(0).getAccount();
		AccountProfile profile = accountService.getByEmailAndPassword(account.getEmail(), account.getPassword());
		if (profile == null) {
			throw new IllegalStateException("no profile found");
		}

		clearDataTables();
	}

	@Test
	public void countProfiles() {
		clearDataTables();

		List<AccountProfile> profiles = fillDatabaseWithAccountsAndAccountProfiles(20);

		AccountProfileFilter accountProfileFilter = new AccountProfileFilter();

		if (profiles.size() != accountService.count(accountProfileFilter)) {
			throw new IllegalStateException("not all users found");
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

	private void clearDataTables() {
		answerService.deleteAll();
		questionService.deleteAll();
		examinationService.deleteAll();
		subjectService.deleteAll();
		accountService.deleteAll();
		localTextsService.deleteAll();
		variousTextsService.deleteAll();
		
	}
}
