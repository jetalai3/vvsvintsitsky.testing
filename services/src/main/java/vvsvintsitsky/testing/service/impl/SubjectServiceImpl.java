package vvsvintsitsky.testing.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import vvsvintsitsky.testing.dataaccess.SubjectDao;
import vvsvintsitsky.testing.dataaccess.filters.SubjectFilter;
import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Inject
	private SubjectDao subjectDao;
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
		subjectDao.delete(id);
		
	}

	@Override
	public List<Subject> getAll() {
		return subjectDao.getAll();
	}

	@Override
	public List<Subject> find(SubjectFilter filter) {
		return subjectDao.find(filter);
	}

}