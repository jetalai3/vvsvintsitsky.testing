package vvsvintsitsky.testing.service;

import java.util.List;

import javax.transaction.Transactional;

import vvsvintsitsky.testing.dataaccess.filters.ExaminationFilter;
import vvsvintsitsky.testing.datamodel.Examination;

public interface ExaminationService {

	@Transactional
	void createExamination(Examination examination);

	Examination getExamination(Long id);

	@Transactional
	void update(Examination examination);

	@Transactional
	void delete(Long id);

	List<Examination> getAll();

	List<Examination> find(ExaminationFilter examinationFilter);

	@Transactional
	void saveOrUpdate(Examination examination);

	long count(ExaminationFilter examinationFilter);

	@Transactional
	void deleteAll();

}
