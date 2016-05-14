package vvsvintsitsky.testing.service;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.service.AnswerService;
import vvsvintsitsky.testing.service.QuestionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:service-context-test.xml")
public class AnswerServiceTest {

	@Inject
	private AnswerService answerService;
	
	@Inject
	private QuestionService questionService;
	
	@Test
	public void testAnswerRegistration() {
		Answer answer = new Answer();
		answer.setCorrect(false);
		answer.setText("ty sosesh!!1");
		answer.setQuestion(questionService.getQuestion(1L));
		
		answerService.register(answer);
	}
}
