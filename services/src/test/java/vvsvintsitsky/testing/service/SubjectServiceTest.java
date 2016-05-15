package vvsvintsitsky.testing.service;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vvsvintsitsky.testing.dataaccess.filters.SubjectFilter;
import vvsvintsitsky.testing.datamodel.Subject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:service-context-test.xml")
public class SubjectServiceTest {
	@Inject
	private SubjectService subjectService;

	@Test
	public void testSubjectRegistration() {
		Subject subject = new Subject();
		subject.setName("geometry");
		subjectService.register(subject);
	}
	
	@Test
	public void testFilter(){
		SubjectFilter filter = new SubjectFilter();
		//filter.setId(4L);
		filter.setName("geometry");
		List<Subject> subjects = subjectService.find(filter);
		for(Subject subject : subjects){
			System.out.println(subject.getId() + " " + subject.getName());
		}
	}
}
