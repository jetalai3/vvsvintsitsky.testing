package vvsvintsitsky.testing.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import vvsvintsitsky.testing.dataaccess.ResultDao;
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
	public List<Result> getAll() {
		return resultDao.getAll();
	}

}
