package vvsvintsitsky.testing.dataaccess.impl;

import org.springframework.stereotype.Repository;
import vvsvintsitsky.testing.dataaccess.SubjectDao;
import vvsvintsitsky.testing.datamodel.Subject;

@Repository
public class SubjectDaoImpl extends AbstractDaoImpl<Subject, Long> implements SubjectDao {

	protected SubjectDaoImpl() {
		super(Subject.class);
	}

	

	

}
