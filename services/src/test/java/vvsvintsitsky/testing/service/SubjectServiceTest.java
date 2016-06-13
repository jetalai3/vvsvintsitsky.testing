//package vvsvintsitsky.testing.service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.inject.Inject;
//import javax.persistence.PersistenceException;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import vvsvintsitsky.testing.dataaccess.filters.SubjectFilter;
//import vvsvintsitsky.testing.datamodel.Subject;
//import vvsvintsitsky.testing.datamodel.Subject_;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:service-context-test.xml")
//public class SubjectServiceTest {
//	@Inject
//	private SubjectService subjectService;
//
//	@Test
//	public void createSubject(){
//		clearDataTables();
//		
//		Subject subject = new Subject();
//		subject.setName("subject name");
//		subjectService.saveOrUpdate(subject);
//		
//		clearDataTables();
//	}
//	
//	@Test
//	public void searchSubjects(){
//		clearDataTables();
//		
//		List<Subject> subjects = fillDatabaseWithSubjects(20);
//		
//		SubjectFilter subjectFilter = new SubjectFilter();
//		Subject subject = subjects.get(subjects.size() - 1);
//		
//		subjectFilter.setName(subject.getName());
//		subjectFilter.setSortProperty(Subject_.id);
//		subjectFilter.setSortOrder(false);
//		subjectFilter.setLimit(30);
//		
//		if (subjectService.find(subjectFilter).size() != 1) {
//			throw new IllegalStateException("more than 1 subject found");
//		}
//		
//		clearDataTables();
//	}
//	
//	@Test
//	public void updateSubject(){
//		clearDataTables();
//		
//		List<Subject> subjects = fillDatabaseWithSubjects(20);
//
//		Subject subject = subjects.get(subjects.size() - 1);
//		Subject updSubject = subjects.get(subjects.size() -2);
//		
//		updSubject.setName(subject.getName());
//		
//		try {
//			subjectService.update(updSubject);
//		} catch (PersistenceException e) {
//			System.out.println("failed to update subject, reason: " + e.getCause().getCause().getMessage());
//		}
//		
//		clearDataTables();
//	}
//	
//	@Test
//	public void deleteSubject(){
//		clearDataTables();
//
//		List<Subject> subjects = fillDatabaseWithSubjects(20);
//		Subject subject = subjects.get(0);
//
//		try {
//			subjectService.delete(subject.getId());
//		} catch (PersistenceException e) {
//			System.out.println("failed to delete subject, reason: " + e.getCause().getCause().getMessage());
//		}
//		
//		clearDataTables();
//	}
//	
//	@Test
//	public void countSubjects(){
//		clearDataTables();
//		
//		List<Subject> subjects = fillDatabaseWithSubjects(20);
//
//		SubjectFilter subjectFilter = new SubjectFilter();
//		
//		if (subjects.size() != subjectService.count(subjectFilter)) {
//			throw new IllegalStateException("not all subjects found");
//		}
//		
//		clearDataTables();
//	}
//	
//	private List<Subject> fillDatabaseWithSubjects(int quantity) {
//		List<Subject> subjects = new ArrayList<Subject>();
//		Subject subject;
//
//		for (int i = 0; i < quantity; i++) {
//			subject = new Subject();
//			subject.setName("subject " + i);
//			subjects.add(subject);
//			subjectService.saveOrUpdate(subject);
//		}
//
//		return subjects;
//
//	}
//	
//	private void clearDataTables() {
//		subjectService.deleteAll();
//	}
//}
