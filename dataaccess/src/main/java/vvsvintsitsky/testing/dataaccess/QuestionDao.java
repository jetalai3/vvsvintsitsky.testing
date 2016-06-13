package vvsvintsitsky.testing.dataaccess;

import java.util.List;

import vvsvintsitsky.testing.datamodel.LanguageVariant;
import vvsvintsitsky.testing.datamodel.Question;

public interface QuestionDao extends AbstractDao<Question, Long> {

	List<Question> getQuestionsWithAnswers(Long id, String language);

	Question getQuestionWithAnswers(Long id);

	
	
}
