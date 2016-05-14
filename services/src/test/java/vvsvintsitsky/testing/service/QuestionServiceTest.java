package vvsvintsitsky.testing.service;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.service.QuestionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:service-context-test.xml")
public class QuestionServiceTest {

	@Inject
	private QuestionService questionService;
	
	@Test
	public void testQuestionRegistration() {
		Question question = new Question();
		question.setText("test task");
		question.setSubject("geometry");
		
		questionService.register(question);
	}
}
