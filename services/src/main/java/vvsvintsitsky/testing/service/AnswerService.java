package vvsvintsitsky.testing.service;

import java.util.List;

import javax.transaction.Transactional;

import vvsvintsitsky.testing.dataaccess.filters.AnswerFilter;
import vvsvintsitsky.testing.datamodel.Answer;

public interface AnswerService {

	@Transactional
	void createAnswer(Answer answer);

	Answer getAnswer(Long id);

	@Transactional
	void update(Answer answer);

	@Transactional
	void delete(Long id);

	List<Answer> getAll();

	long count(AnswerFilter answerFilter);

	List<Answer> find(AnswerFilter filter);

	@Transactional
	void saveOrUpdate(Answer answer);

	@Transactional
	void deleteAnswerByQuestionId(Long id);
}
