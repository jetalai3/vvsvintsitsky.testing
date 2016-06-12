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
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Examination_;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.UserRole;
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

	@Test
	public void createExamination() {
		clearDataTables();
		
		Examination examination = new Examination();
		
		List<Subject> subjects = fillDatabaseWithSubjects(1);
		List<Question> questions = fillDatabaseWithQuestions(5);
		List<AccountProfile> accountProfile = fillDatabaseWithAccountsAndAccountProfiles(1);
		
		examination.setName("examination name");
		examination.setQuestions(questions);
		examination.setSubject(subjects.get(0));
		examination.setAccountProfile(accountProfile.get(0));
		examination.setBeginDate(new Date());
		examination.setEndDate(new Date());
		
		examinationService.saveOrUpdate(examination);
		
		clearDataTables();
	}
	
	@Test
	public void searchExamination(){
		clearDataTables();

		List<Examination> examinations = fillDatabaseWithExaminations(20);
		
		ExaminationFilter examinationFilter = new ExaminationFilter();
		Examination examination = examinations.get(examinations.size() - 1);
		
		examinationFilter.setBeginDate(examination.getBeginDate());
		examinationFilter.setEndDate(examination.getEndDate());
		examinationFilter.setName(examination.getName());
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

		List<Examination> examinations = fillDatabaseWithExaminations(20);

		Examination examination = examinations.get(examinations.size() - 1);
		Examination updExamination = examinations.get(examinations.size() - 2);
		
		updExamination.setName(examination.getName());
		
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
		
		List<Examination> examinations = fillDatabaseWithExaminations(20);
		
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
		
		List<Examination> examinations = fillDatabaseWithExaminations(20);

		ExaminationFilter examinationFilter = new ExaminationFilter();
		
		if (examinations.size() != examinationService.count(examinationFilter)) {
			throw new IllegalStateException("not all examinations found");
		}
		
		clearDataTables();
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
		examinationService.deleteAll();
		accountService.deleteAll();
		subjectService.deleteAll();
	}
}
