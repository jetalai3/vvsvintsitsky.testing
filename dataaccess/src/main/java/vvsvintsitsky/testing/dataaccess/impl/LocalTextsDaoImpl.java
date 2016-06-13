package vvsvintsitsky.testing.dataaccess.impl;

import org.springframework.stereotype.Repository;

import vvsvintsitsky.testing.dataaccess.LocalTextsDao;
import vvsvintsitsky.testing.datamodel.LocalTexts;

@Repository
public class LocalTextsDaoImpl extends AbstractDaoImpl<LocalTexts, Long> implements LocalTextsDao {

	protected LocalTextsDaoImpl() {
		super(LocalTexts.class);
		
	}

}
