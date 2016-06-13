package vvsvintsitsky.testing.dataaccess;

import java.util.List;

import vvsvintsitsky.testing.datamodel.Subject;

public interface SubjectDao extends AbstractDao<Subject, Long> {
	
	Subject getWithAllTexts(Long id);
	
	List<Subject> getAllWithLanguageText(String language);
}
