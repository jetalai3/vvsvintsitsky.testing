package vvsvintsitsky.testing.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import vvsvintsitsky.testing.dataaccess.ExaminationDao;
import vvsvintsitsky.testing.dataaccess.filters.AnswerFilter;
import vvsvintsitsky.testing.dataaccess.filters.ExaminationFilter;
import vvsvintsitsky.testing.datamodel.Answer;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.service.ExaminationService;

@Service
public class ExaminationServiceImpl implements ExaminationService {

	@Inject
	private ExaminationDao examinationDao;
	
	@Override
	public void createExamination(Examination examination) {
		examinationDao.insert(examination);
	}

	@Override
	public Examination getExamination(Long id) {
		return examinationDao.get(id);
	}

	@Override
	public void update(Examination examination) {
		examinationDao.update(examination);
	}

	@Override
	public void delete(Long id) {
		examinationDao.delete(id);
	}
	
	@Override
	public void deleteAll(){
		examinationDao.deleteAll();
	}

	@Override
	public List<Examination> getAll() {
		return examinationDao.getAll();
	}

	@Override
	public List<Examination> find(ExaminationFilter filter) {
		return examinationDao.find(filter);
	}

	@Override
	public void saveOrUpdate(Examination examination) {
		if (examination.getId() != null) {
			examinationDao.update(examination);
		} else {
			examinationDao.insert(examination);
		}
	}
	
	@Override
	public long count(ExaminationFilter examinationFilter) {
		return examinationDao.count(examinationFilter);
	}

}
