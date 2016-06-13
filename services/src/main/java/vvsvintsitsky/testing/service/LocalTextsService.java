package vvsvintsitsky.testing.service;

import javax.transaction.Transactional;

import vvsvintsitsky.testing.datamodel.LocalTexts;

public interface LocalTextsService {

	@Transactional
	void insert(LocalTexts ansText);
	
	LocalTexts get(Long id);

	@Transactional
	void deleteAll();
}
