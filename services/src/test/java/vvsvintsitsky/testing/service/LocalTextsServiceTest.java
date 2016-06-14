package vvsvintsitsky.testing.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vvsvintsitsky.testing.dataaccess.filters.QuestionFilter;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.LocalTexts;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.LanguageVariant;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Question_;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.UserRole;
import vvsvintsitsky.testing.datamodel.VariousTexts;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:service-context-test.xml")
public class LocalTextsServiceTest {

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
	public void tessst() {
		wipeDB();
		Subject subject = fillDatabaseWithSubjects(1).get(0);
//		AccountProfile accountProfile = fillDatabaseWithAccountsAndAccountProfiles(1).get(0);
//
//		List<Examination> examinations = fillDatabaseWithExaminations(2, accountProfile, subject);
//		Examination examination = examinations.get(0);
//		examination = examinationService.getExamination(examination.getId());
//		examinationService.delete(examination.getId());
		subject = subjectService.getSubject(subject.getId());
//		subject.getSubjectNames().getRusText();
		subjectService.delete(subject.getId());
	}
	
	@Test
	public void testSMTH() {
		wipeDB();
		Subject subject = fillDatabaseWithSubjects(1).get(0);
		AccountProfile accountProfile = fillDatabaseWithAccountsAndAccountProfiles(1).get(0);

		List<Examination> examinations = fillDatabaseWithExaminations(2, accountProfile, subject);
		Examination examination = examinations.get(0);

		// questionService.getQuestionsWithAnswers(examination, "ru");
		// for(Question qq : examination.getQuestions()) {
		// System.out.print(qq.getTexts().getRusText().getTxt() + " ");
		// for(Answer aa : qq.getAnswers()) {
		// System.out.print(aa.getTexts().getRusText().getTxt());
		// }
		// System.out.println(" ");
		// }
		String ru = "ru";
		String en = "en";
		
		Question qq = examination.getQuestions().get(0);
		qq = questionService.getQuestionWithAnswers(qq.getId());

//		System.out.print(qq.getQuestionTexts().getText(ru) + " ");
//		System.out.println("");
//		for (Answer aa : qq.getAnswers()) {
//			System.out.print(aa.getAnswerTexts().getText(ru)+ " ");
//		}
//		System.out.println(" ");
		
		System.out.print(qq.getQuestionTexts().getText(ru) + " " + qq.getQuestionTexts().getText(en) + " ");
		System.out.println("");
		for (Answer aa : qq.getAnswers()) {
			System.out.print(aa.getAnswerTexts().getText(ru)+ " " + aa.getAnswerTexts().getText(en));
		}
		System.out.println(" ");
		
//		QuestionFilter filter = new QuestionFilter();
//		filter.setFetchTexts(true);
//		filter.setLanguage(ru);
////		filter.setSubjectName(qq.getSubject().getName());
//		filter.setFetchSubject(true);
//		filter.setSortProperty(Question_.id);
//		filter.setSortOrder(true);
//		filter.setLimit(5);
//		filter.setOffset(1);
		
//		qq = questionService.find(filter).get(0);
//		System.out.print(qq.getQuestionTexts().getText(ru) + " ");
//		System.out.println("");
//		for (Answer aa : qq.getAnswers()) {
//			System.out.print(aa.getTexts().getText(ru)+ " " + aa.getTexts().getText(en));
//		}
		System.out.println(" ");
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

	private void wipeDB() {
		answerService.deleteAll();
		questionService.deleteAll();
		examinationService.deleteAll();
		subjectService.deleteAll();
		accountService.deleteAll();
		localTextsService.deleteAll();
		variousTextsService.deleteAll();
	}

}
