package vvsvintsitsky.testing.webapp.common;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

import vvsvintsitsky.testing.datamodel.Examination;

public class ExaminationChoiceRenderer extends ChoiceRenderer<Examination> {

    public static final ExaminationChoiceRenderer INSTANCE = new ExaminationChoiceRenderer();

    public static String language = Session.get().getLocale().getLanguage();
    
    private ExaminationChoiceRenderer() {
        super();
    }

    @Override
    public Object getDisplayValue(Examination object) {
        return object.getExaminationNames().getText(language);
    }

    @Override
    public String getIdValue(Examination object, int index) {
        return String.valueOf(object.getId());
    }
}
