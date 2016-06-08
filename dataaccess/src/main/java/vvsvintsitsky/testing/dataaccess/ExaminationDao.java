package vvsvintsitsky.testing.dataaccess;

import javax.transaction.Transactional;

import vvsvintsitsky.testing.datamodel.Examination;

public interface ExaminationDao extends AbstractDao<Examination, Long> {

	@Transactional
	Examination getExaminationWithQuestionsAndAnswers(Long id);
}
