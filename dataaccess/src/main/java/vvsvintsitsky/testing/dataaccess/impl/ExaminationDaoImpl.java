package vvsvintsitsky.testing.dataaccess.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import vvsvintsitsky.testing.dataaccess.ExaminationDao;
import vvsvintsitsky.testing.datamodel.Examination;

@Repository
public class ExaminationDaoImpl extends AbstractDaoImpl<Examination, Long> implements ExaminationDao {

	protected ExaminationDaoImpl() {
		super(Examination.class);
	}

	
}
