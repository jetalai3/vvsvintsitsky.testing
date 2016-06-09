package vvsvintsitsky.testing.service;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vvsvintsitsky.testing.dataaccess.SubjectDao;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.service.QuestionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:service-context-test.xml")
public class QuestionServiceTest {

	@Inject
	private QuestionService questionService;

	@Inject
	private ExaminationService examinationService;
	
	@Inject
	private SubjectDao subjectDao;

	@Test
	public void testQuestionRegistration() {
		Question question = new Question();
		question.setText("test task");
		question.setSubject(subjectDao.get(1L));
		;

		questionService.createQuestion(question);
	}

	@Test
	public void testQuestionsWithAnswers(){
		Examination examination = examinationService.getExamination(12L);
		questionService.getQuestionsWithAnswers(examination);
		for(Question question : examination.getQuestions()){
			System.out.println(question.getText());
			for(Answer answer : question.getAnswers()){
				System.out.println(answer.getText());

			}
		}
	}
}
