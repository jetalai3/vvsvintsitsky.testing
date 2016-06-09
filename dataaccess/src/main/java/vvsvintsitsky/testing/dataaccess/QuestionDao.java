package vvsvintsitsky.testing.dataaccess;

import java.util.List;

import vvsvintsitsky.testing.datamodel.Question;

public interface QuestionDao extends AbstractDao<Question, Long> {

	List<Question> getQuestionsWithAnswers(Long id);

	
	
}
