package vvsvintsitsky.testing.service;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Result;
import vvsvintsitsky.testing.service.AccountService;
import vvsvintsitsky.testing.service.AnswerService;
import vvsvintsitsky.testing.service.ExaminationService;
import vvsvintsitsky.testing.service.ResultService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:service-context-test.xml")
public class ResultServiceTest {

	@Inject
	private ResultService resultService;
	
	@Inject
	private ExaminationService examinationService;
	
	@Inject
	private AccountService accountService;
	
	@Inject
	private AnswerService answerService;
	
	@Test
	public void testResultRegistration() {
		Result result = new Result();
		result.setExamination(examinationService.getExamination(1L));
		result.setAccountProfile(accountService.getAccountProfile(1L));
		result.setPoints(90);
		
		List<Answer> answers = new LinkedList<Answer>();
		answers.add(answerService.getAnswer(1L));
		
		result.setAnswers(answers);
		
		resultService.insert(result);
	}
}
