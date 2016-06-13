package vvsvintsitsky.testing.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class LocalTexts extends AbstractModel {

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	private VariousTexts rusText;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	private VariousTexts engText;
	
	public VariousTexts getRusText() {
		return rusText;
	}

	public void setRusText(VariousTexts rusText) {
		this.rusText = rusText;
	}

	public VariousTexts getEngText() {
		return engText;
	}

	public void setEngText(VariousTexts engText) {
		this.engText = engText;
	}

	@Transient
	public String getText(String language) {
		if(language.equals("ru")) {
			return rusText.getTxt();
		}
		if(language.equals("en")) {
			return engText.getTxt();
		}
		return null;
	}
}
