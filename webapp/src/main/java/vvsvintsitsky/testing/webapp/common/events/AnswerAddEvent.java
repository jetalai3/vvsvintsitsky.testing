package vvsvintsitsky.testing.webapp.common.events;

import java.io.Serializable;

import vvsvintsitsky.testing.datamodel.Answer;

public class AnswerAddEvent implements Serializable {
	private Answer answer;
	
	public AnswerAddEvent(Answer answer){
		this.answer = answer;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
}
