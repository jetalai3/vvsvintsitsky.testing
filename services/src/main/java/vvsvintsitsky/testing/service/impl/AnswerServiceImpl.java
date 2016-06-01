package vvsvintsitsky.testing.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import vvsvintsitsky.testing.dataaccess.AnswerDao;
import vvsvintsitsky.testing.dataaccess.filters.AnswerFilter;
import vvsvintsitsky.testing.dataaccess.filters.AnswerFilter;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.service.AnswerService;

@Service
public class AnswerServiceImpl implements AnswerService {

	@Inject
	private AnswerDao answerDao;
	
	@Override
	public void createAnswer(Answer answer) {
		answerDao.insert(answer);
	}

	@Override
	public Answer getAnswer(Long id) {
		return answerDao.get(id);
	}

	@Override
	public void update(Answer answer) {
		answerDao.update(answer);
	}

	@Override
	public void delete(Long id) {
		answerDao.delete(id);
	}

	@Override
	public List<Answer> getAll() {
		return answerDao.getAll();
	}

	@Override
	public List<Answer> find(AnswerFilter filter) {
		return answerDao.find(filter);
	}

	@Override
	public void saveOrUpdate(Answer answer) {
		if (answer.getId() != null) {
			answerDao.update(answer);
		} else {
			answerDao.insert(answer);
		}
	}
	@Override
	public long count(AnswerFilter answerFilter) {
		return answerDao.count(answerFilter);
	}

}
