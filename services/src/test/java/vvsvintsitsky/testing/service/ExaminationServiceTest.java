package vvsvintsitsky.testing.service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vvsvintsitsky.testing.dataaccess.SubjectDao;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.service.ExaminationService;
import vvsvintsitsky.testing.service.QuestionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:service-context-test.xml")
public class ExaminationServiceTest {

	@Inject
	private ExaminationService examinationService;
	@Inject
	private SubjectDao subjectDao;
	@Inject
	private AccountService accountService;
	
	@Inject
	private QuestionService questionService;
	
	@Test
	public void testExaminationRegistration() {

		Examination examination = new Examination();
		
		examination.setBeginDate(new Date());
		examination.setEndDate(new Date());
		examination.setName("math: geometry");
		examination.setAccountProfile(accountService.getAccountProfile(1L));
		examination.setSubject(subjectDao.get(2L));
		List<Question> questions = new LinkedList<Question>();
		Question question1 = questionService.getQuestion(1L);
		Question question2 = questionService.getQuestion(2L);
		
		questions.add(question1);
		questions.add(question2);
		
		examination.setQuestions(questions);
		
		examinationService.register(examination);		
	}
	
}
