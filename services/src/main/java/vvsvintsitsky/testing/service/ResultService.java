package vvsvintsitsky.testing.service;

import java.util.List;

import javax.transaction.Transactional;

import vvsvintsitsky.testing.dataaccess.filters.ResultFilter;
import vvsvintsitsky.testing.datamodel.Result;

public interface ResultService {

	@Transactional
    void insert(Result result);

	Result getResult(Long id);

    @Transactional
    void update(Result result);

    @Transactional
    void delete(Long id);

    List<Result> getAll();

	List<Result> find(ResultFilter filter);

	long count(ResultFilter filter);

	@Transactional
	void saveOrUpdate(Result result);

	Result getResultWithAnswersAndQuestions(Long id);
}
