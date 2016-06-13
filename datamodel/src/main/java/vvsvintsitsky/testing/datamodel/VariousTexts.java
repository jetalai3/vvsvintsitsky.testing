package vvsvintsitsky.testing.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class VariousTexts extends AbstractModel {

	@Column
	private String txt;

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}
	
}
