package vvsvintsitsky.testing.dataaccess;

import java.util.List;

import vvsvintsitsky.testing.dataaccess.filters.SubjectFilter;
import vvsvintsitsky.testing.datamodel.Subject;

public interface SubjectDao extends AbstractDao<Subject, Long> {
	List<Subject> find(SubjectFilter filter);
}
