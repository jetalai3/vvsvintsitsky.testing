package vvsvintsitsky.testing.dataaccess;

import java.util.List;

import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Result;

public interface ResultDao extends AbstractDao<Result, Long> {

	Result getResultWithAnswersAndQuestions(Long id);

	List<Question> getResultQuestionsWithAnswers(Long id, String language);
}
