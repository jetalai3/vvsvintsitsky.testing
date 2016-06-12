package vvsvintsitsky.testing.service;

import java.util.List;

import javax.transaction.Transactional;

import vvsvintsitsky.testing.dataaccess.filters.AccountProfileFilter;
import vvsvintsitsky.testing.dataaccess.filters.QuestionFilter;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.datamodel.Question;

public interface QuestionService {

	@Transactional
	void createQuestion(Question question);

	Question getQuestion(Long id);

	@Transactional
	void update(Question question);

	@Transactional
	void delete(Long id);

	@Transactional
	void saveOrUpdate(Question question);

	List<Question> getAll();
	
	List<Question> find(QuestionFilter filter);
	
	long count(QuestionFilter questionFilter);

	void getQuestionsWithAnswers(Examination examination);
	
	Question getQuestionWithAnswers(Long id);

	@Transactional
	void deleteAll();

}
