package vvsvintsitsky.testing.service;

import java.util.List;

import javax.transaction.Transactional;

import vvsvintsitsky.testing.datamodel.Result;

public interface ResultService {

	@Transactional
    void register(Result result);

	Result getResult(Long id);

    @Transactional
    void update(Result result);

    @Transactional
    void delete(Long id);

    List<Result> getAll();
}
