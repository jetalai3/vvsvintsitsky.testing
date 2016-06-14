package vvsvintsitsky.testing.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import vvsvintsitsky.testing.dataaccess.AnswerDao;
import vvsvintsitsky.testing.dataaccess.LocalTextsDao;
import vvsvintsitsky.testing.dataaccess.VariousTextsDao;
import vvsvintsitsky.testing.dataaccess.filters.AnswerFilter;
import vvsvintsitsky.testing.datamodel.LocalTexts;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.service.AnswerService;

@Service
public class AnswerServiceImpl implements AnswerService {

	@Inject
	private AnswerDao answerDao;

	@Inject
	private LocalTextsDao localTextsDao;

	@Inject
	private VariousTextsDao variousTextsDao;
	
	@Override
	public void createAnswer(Answer answer, LocalTexts texts) {
		answer.setAnswerTexts(texts);
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
		Answer answer = answerDao.get(id);
		answerDao.delete(id);
		localTextsDao.delete(answer.getAnswerTexts().getId());
		variousTextsDao.delete(answer.getAnswerTexts().getRusText().getId());
		variousTextsDao.delete(answer.getAnswerTexts().getEngText().getId());
	}

	@Override
	public void deleteAll() {
		answerDao.deleteAll();
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
			variousTextsDao.update(answer.getAnswerTexts().getRusText());
			variousTextsDao.update(answer.getAnswerTexts().getEngText());
			localTextsDao.update(answer.getAnswerTexts());
			answerDao.update(answer);
		} else {
			variousTextsDao.insert(answer.getAnswerTexts().getRusText());
			variousTextsDao.insert(answer.getAnswerTexts().getEngText());
			localTextsDao.insert(answer.getAnswerTexts());
			answerDao.insert(answer);
		}
	}

	@Override
	public long count(AnswerFilter answerFilter) {
		return answerDao.count(answerFilter);
	}

	@Override
	public void deleteAnswerByQuestionId(Long id) {
		answerDao.deleteAnswerByQuestionId(id);
	}

	@Override
	public Answer getWithAllTexts(Long id) {
		return answerDao.getWithAllTexts(id);
	}
}
