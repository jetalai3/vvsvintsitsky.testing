package vvsvintsitsky.testing.webapp.common.events;

import java.io.Serializable;

import vvsvintsitsky.testing.datamodel.Subject;

public class SubjectChangeEvent implements Serializable {
	private Subject subject;
	
	public SubjectChangeEvent(Subject subject){
		this.subject = subject;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
}
