package vvsvintsitsky.testing.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import vvsvintsitsky.testing.dataaccess.QuestionDao;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Inject
	private QuestionDao questionDao;
	
	@Override
	public void register(Question question) {
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

}
