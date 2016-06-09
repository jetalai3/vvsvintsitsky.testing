package vvsvintsitsky.testing.dataaccess;

import java.util.List;

import vvsvintsitsky.testing.datamodel.Result;

public interface ResultDao extends AbstractDao<Result, Long> {

	Result getResultWithAnswersAndQuestions(Long id);

}
