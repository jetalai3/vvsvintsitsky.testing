package vvsvintsitsky.testing.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import vvsvintsitsky.testing.dataaccess.ExaminationDao;
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
	public List<Examination> getAll() {
		return examinationDao.getAll();
	}

}
