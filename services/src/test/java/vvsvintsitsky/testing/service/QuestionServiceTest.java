package vvsvintsitsky.testing.service;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vvsvintsitsky.testing.dataaccess.SubjectDao;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.service.QuestionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:service-context-test.xml")
public class QuestionServiceTest {

	@Inject
	private QuestionService questionService;
	@Inject
	private SubjectDao subjectDao;
	@Test
	public void testQuestionRegistration() {
		Question question = new Question();
		question.setText("test task");
		question.setSubject(subjectDao.get(1L));;
		
		questionService.register(question);
	}
}
