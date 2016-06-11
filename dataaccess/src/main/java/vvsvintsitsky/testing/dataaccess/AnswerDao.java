package vvsvintsitsky.testing.dataaccess;

import vvsvintsitsky.testing.datamodel.Answer;

public interface AnswerDao extends AbstractDao<Answer, Long> {

	void deleteAnswerByQuestionId(Long id);

}
