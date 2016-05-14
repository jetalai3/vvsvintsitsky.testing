package vvsvintsitsky.testing.service;

import java.util.List;

import javax.transaction.Transactional;

import vvsvintsitsky.testing.datamodel.Question;

public interface QuestionService {

	@Transactional
    void register(Question question);

	Question getQuestion(Long id);

    @Transactional
    void update(Question question);

    @Transactional
    void delete(Long id);

    List<Question> getAll();
}
