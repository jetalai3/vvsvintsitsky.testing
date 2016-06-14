package vvsvintsitsky.testing.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import vvsvintsitsky.testing.dataaccess.LocalTextsDao;
import vvsvintsitsky.testing.dataaccess.SubjectDao;
import vvsvintsitsky.testing.dataaccess.VariousTextsDao;
import vvsvintsitsky.testing.dataaccess.filters.QuestionFilter;
import vvsvintsitsky.testing.dataaccess.filters.SubjectFilter;
import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.LocalTexts;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.VariousTexts;
import vvsvintsitsky.testing.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Inject
	private SubjectDao subjectDao;
	
	@Inject
	private LocalTextsDao localTextsDao;

	@Inject
	private VariousTextsDao variousTextsDao;
	
	@Override
	public void register(Subject subject) {
		subjectDao.insert(subject);
		
	}

	@Override
	public Subject getSubject(Long id) {
		return subjectDao.get(id);
	}

	@Override
	public void update(Subject subject) {
		subjectDao.update(subject);
		
	}

	@Override
	public void delete(Long id) {
		Subject subject = subjectDao.get(id);
		subjectDao.delete(id);
		localTextsDao.delete(subject.getSubjectNames().getId());
		variousTextsDao.delete(subject.getSubjectNames().getRusText().getId());
		variousTextsDao.delete(subject.getSubjectNames().getEngText().getId());
	}
	
	@Override
	public void deleteAll(){
		subjectDao.deleteAll();
	}

	@Override
	public List<Subject> getAll() {
		return subjectDao.getAll();
	}

	@Override
	public List<Subject> find(SubjectFilter filter) {
		return subjectDao.find(filter);
	}

	@Override
	public void saveOrUpdate(Subject subject) {
		if (subject.getId() != null) {
			variousTextsDao.update(subject.getSubjectNames().getRusText());
			variousTextsDao.update(subject.getSubjectNames().getEngText());
			localTextsDao.update(subject.getSubjectNames());
			subjectDao.update(subject);
		} else {
			variousTextsDao.insert(subject.getSubjectNames().getRusText());
			variousTextsDao.insert(subject.getSubjectNames().getEngText());
			localTextsDao.insert(subject.getSubjectNames());
			subjectDao.insert(subject);
		}
	}
	
	@Override
	public long count(SubjectFilter subjectFilter) {
		return subjectDao.count(subjectFilter);
	}

	@Override
	public Subject getWithAllTexts(Long id) {
		return subjectDao.getWithAllTexts(id);
	}

	@Override
	public List<Subject> getAllWithLanguageText(String language) {
		return subjectDao.getAllWithLanguageText(language);
	}

	@Override
	public void delete(Subject subject) {
		VariousTexts rusText = subject.getSubjectNames().getRusText();
		VariousTexts engText = subject.getSubjectNames().getEngText();
		LocalTexts localTexts = subject.getSubjectNames();
		subjectDao.delete(subject.getId());
		localTextsDao.delete(localTexts.getId());
		variousTextsDao.delete(rusText.getId());
		variousTextsDao.delete(engText.getId());
		
	}
}
