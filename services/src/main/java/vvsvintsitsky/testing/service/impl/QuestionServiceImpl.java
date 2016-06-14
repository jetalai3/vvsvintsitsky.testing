package vvsvintsitsky.testing.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import vvsvintsitsky.testing.dataaccess.LocalTextsDao;
import vvsvintsitsky.testing.dataaccess.QuestionDao;
import vvsvintsitsky.testing.dataaccess.VariousTextsDao;
import vvsvintsitsky.testing.dataaccess.filters.QuestionFilter;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Inject
	private QuestionDao questionDao;

	@Inject
	private LocalTextsDao localTextsDao;
	
	@Inject
	private VariousTextsDao variousTextsDao;
	
	@Override
	public void createQuestion(Question question) {
		questionDao.insert(question);
	}

	@Override
	public Question getQuestion(Long id) {
		return questionDao.get(id);
	}

	@Override
	public void update(Question question) {
		questionDao.update(question);
	}

	@Override
	public void delete(Long id) {
		Question question = questionDao.get(id);
		questionDao.delete(id);
		localTextsDao.delete(question.getQuestionTexts().getId());
		variousTextsDao.delete(question.getQuestionTexts().getRusText().getId());
		variousTextsDao.delete(question.getQuestionTexts().getEngText().getId());
	}
	
	@Override
	public void deleteAll(){
		questionDao.deleteAll();
	}

	@Override
	public List<Question> getAll() {
		return questionDao.getAll();
	}

	@Override
	public void saveOrUpdate(Question question) {
		
		if (question.getId() != null) {
			variousTextsDao.update(question.getQuestionTexts().getRusText());
			variousTextsDao.update(question.getQuestionTexts().getEngText());
			localTextsDao.update(question.getQuestionTexts());
			questionDao.update(question);
		} else {
			variousTextsDao.insert(question.getQuestionTexts().getRusText());
			variousTextsDao.insert(question.getQuestionTexts().getEngText());
			localTextsDao.insert(question.getQuestionTexts());
			questionDao.insert(question);
		}
	}

	@Override
	public List<Question> find(QuestionFilter filter) {
		List<Question> questions = questionDao.find(filter);
		return questions;
	}

	@Override
	public long count(QuestionFilter questionFilter) {
		return questionDao.count(questionFilter);
	}

	@Override
	public void getQuestionsWithAnswers(Examination examination, String language) {
		List<Question> questions = questionDao.getQuestionsWithAnswers(examination.getId(), language);
		examination.setQuestions(questions);
	}

	@Override
	public Question getQuestionWithAnswers(Long id) {
		return questionDao.getQuestionWithAnswers(id);
	}
}
