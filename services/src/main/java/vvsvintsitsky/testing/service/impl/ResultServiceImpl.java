package vvsvintsitsky.testing.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import vvsvintsitsky.testing.dataaccess.ResultDao;
import vvsvintsitsky.testing.dataaccess.filters.QuestionFilter;
import vvsvintsitsky.testing.dataaccess.filters.ResultFilter;
import vvsvintsitsky.testing.datamodel.Question;
import vvsvintsitsky.testing.datamodel.Result;
import vvsvintsitsky.testing.service.ResultService;

@Service
public class ResultServiceImpl implements ResultService {

	@Inject
	private ResultDao resultDao;
	
	@Override
	public void insert(Result result) {
		resultDao.insert(result);
	}

	@Override
	public Result getResult(Long id) {
		return resultDao.get(id);
	}

	@Override
	public void update(Result result) {
		resultDao.update(result);
	}

	@Override
	public void delete(Long id) {
		resultDao.delete(id);
	}
	
	@Override
	public void deleteAll(){
		resultDao.deleteAll();
	}

	@Override
	public List<Result> getAll() {
		return resultDao.getAll();
	}
	
	@Override
	public void saveOrUpdate(Result result){
		if (result.getId() != null) {
			resultDao.update(result);
		} else {
			resultDao.insert(result);
		}
	}
	
	@Override
	public List<Result> find(ResultFilter filter) {
		return resultDao.find(filter);
	}
	
	@Override
	public long count(ResultFilter filter) {
		return resultDao.count(filter);
	}

	@Override
	public Result getResultWithAnswersAndQuestions(Long id){
		return resultDao.getResultWithAnswersAndQuestions(id);
	}

	@Override
	public List<Question> getResultQuestionsWithAnswers(Long id, String language) {
		return resultDao.getResultQuestionsWithAnswers(id, language);
	}
	
}
