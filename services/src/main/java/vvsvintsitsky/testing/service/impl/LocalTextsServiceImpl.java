package vvsvintsitsky.testing.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import vvsvintsitsky.testing.dataaccess.LocalTextsDao;
import vvsvintsitsky.testing.dataaccess.VariousTextsDao;
import vvsvintsitsky.testing.datamodel.LocalTexts;
import vvsvintsitsky.testing.service.LocalTextsService;

@Service
public class LocalTextsServiceImpl implements LocalTextsService {

	@Inject
	private LocalTextsDao localTextsDao;
	
	@Inject
	private VariousTextsDao variousTextsDao;
	
	@Override
	public void insert(LocalTexts localTexts) {
		localTextsDao.insert(localTexts);
	}

	@Override
	public LocalTexts get(Long id) {
		return localTextsDao.get(id);
	}

	@Override
	public void deleteAll() {
		localTextsDao.deleteAll();
	}

}
