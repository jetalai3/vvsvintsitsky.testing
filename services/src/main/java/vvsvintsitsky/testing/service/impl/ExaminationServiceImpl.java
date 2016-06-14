package vvsvintsitsky.testing.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import vvsvintsitsky.testing.dataaccess.ExaminationDao;
import vvsvintsitsky.testing.dataaccess.LocalTextsDao;
import vvsvintsitsky.testing.dataaccess.VariousTextsDao;
import vvsvintsitsky.testing.dataaccess.filters.ExaminationFilter;
import vvsvintsitsky.testing.datamodel.Examination;
import vvsvintsitsky.testing.service.ExaminationService;

@Service
public class ExaminationServiceImpl implements ExaminationService {

	@Inject
	private ExaminationDao examinationDao;

	@Inject
	private LocalTextsDao localTextsDao;

	@Inject
	private VariousTextsDao variousTextsDao;

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
		Examination examination = examinationDao.get(id);
		localTextsDao.delete(examination.getExaminationNames().getId());
		variousTextsDao.delete(examination.getExaminationNames().getRusText().getId());
		variousTextsDao.delete(examination.getExaminationNames().getEngText().getId());
	}

	@Override
	public void deleteAll() {
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
			variousTextsDao.update(examination.getExaminationNames().getRusText());
			variousTextsDao.update(examination.getExaminationNames().getEngText());
			localTextsDao.update(examination.getExaminationNames());
			examinationDao.update(examination);
		} else {
			variousTextsDao.insert(examination.getExaminationNames().getRusText());
			variousTextsDao.insert(examination.getExaminationNames().getEngText());
			localTextsDao.insert(examination.getExaminationNames());
			examinationDao.insert(examination);
		}
	}

	@Override
	public long count(ExaminationFilter examinationFilter) {
		return examinationDao.count(examinationFilter);
	}

	@Override
	public Examination getWithAllTexts(Long id, String language) {
		return examinationDao.getWithAllTexts(id, language);
	}

}
