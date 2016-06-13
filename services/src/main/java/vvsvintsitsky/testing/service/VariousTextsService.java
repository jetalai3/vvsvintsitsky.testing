package vvsvintsitsky.testing.service;

import javax.transaction.Transactional;

public interface VariousTextsService {

	@Transactional
	void deleteAll();
}
