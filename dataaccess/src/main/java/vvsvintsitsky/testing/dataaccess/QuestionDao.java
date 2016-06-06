package vvsvintsitsky.testing.dataaccess;

import vvsvintsitsky.testing.datamodel.Question;

public interface QuestionDao extends AbstractDao<Question, Long> {
	Question getQuestionWithAnswers(Long id);
}
