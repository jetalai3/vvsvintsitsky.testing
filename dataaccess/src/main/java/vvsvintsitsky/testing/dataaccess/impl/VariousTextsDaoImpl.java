package vvsvintsitsky.testing.dataaccess.impl;

import org.springframework.stereotype.Repository;

import vvsvintsitsky.testing.dataaccess.VariousTextsDao;
import vvsvintsitsky.testing.datamodel.VariousTexts;

@Repository
public class VariousTextsDaoImpl extends AbstractDaoImpl<VariousTexts, Long> implements VariousTextsDao {

	protected VariousTextsDaoImpl() {
		super(VariousTexts.class);
	}

}
