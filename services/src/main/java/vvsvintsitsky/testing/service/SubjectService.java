package vvsvintsitsky.testing.service;

import java.util.List;
import javax.transaction.Transactional;

import vvsvintsitsky.testing.dataaccess.filters.SubjectFilter;
import vvsvintsitsky.testing.datamodel.Subject;

public interface SubjectService {
	@Transactional
	void register(Subject subject);

	Subject getSubject(Long id);

	@Transactional
	void update(Subject subject);

	@Transactional
	void delete(Long id);
	
	@Transactional
	void delete(Subject subject);

	List<Subject> getAll();
	
	List<Subject> find(SubjectFilter filter);
	
	@Transactional
	void saveOrUpdate(Subject subject);
	
	long count(SubjectFilter subjectFilter);

	@Transactional
	void deleteAll();
	
	Subject getWithAllTexts(Long id);
	
	List<Subject> getAllWithLanguageText(String language);
}
