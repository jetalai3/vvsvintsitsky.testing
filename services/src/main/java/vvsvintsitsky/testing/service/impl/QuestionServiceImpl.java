package vvsvintsitsky.testing.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import vvsvintsitsky.testing.dataaccess.QuestionDao;
import vvsvintsitsky.testing.dataaccess.filters.AccountProfileFilter;
import vvsvintsitsky.testing.dataaccess.filters.QuestionFilter;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Inject
	private QuestionDao questionDao;

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
		questionDao.delete(id);
	}

	@Override
	public List<Question> getAll() {
		return questionDao.getAll();
	}

	@Override
	public void saveOrUpdate(Question question) {
		if (question.getId() != null) {
			questionDao.update(question);
		} else {
			questionDao.insert(question);
		}
	}

	@Override
	public List<Question> find(QuestionFilter filter) {
		return questionDao.find(filter);
	}

	@Override
	public long count(QuestionFilter questionFilter) {
		return questionDao.count(questionFilter);
	}

	@Override
	public void getQuestionsWithAnswers(Examination examination) {
		examination.setQuestions(questionDao.getQuestionsWithAnswers(examination.getId()));
	}
}
