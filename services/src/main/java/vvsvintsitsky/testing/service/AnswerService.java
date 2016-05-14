package vvsvintsitsky.testing.service;

import java.util.List;

import javax.transaction.Transactional;

import vvsvintsitsky.testing.datamodel.Answer;

public interface AnswerService {

	@Transactional
    void register(Answer answer);

	Answer getAnswer(Long id);

    @Transactional
    void update(Answer answer);

    @Transactional
    void delete(Long id);

    List<Answer> getAll();
}
