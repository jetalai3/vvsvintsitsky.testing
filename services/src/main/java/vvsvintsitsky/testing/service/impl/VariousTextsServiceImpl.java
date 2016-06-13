package vvsvintsitsky.testing.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import vvsvintsitsky.testing.dataaccess.VariousTextsDao;
import vvsvintsitsky.testing.service.VariousTextsService;

@Service
public class VariousTextsServiceImpl implements VariousTextsService {

	@Inject
	private VariousTextsDao variousTextsDao;
	
	@Override
	public void deleteAll() {
		variousTextsDao.deleteAll();
	}

}
