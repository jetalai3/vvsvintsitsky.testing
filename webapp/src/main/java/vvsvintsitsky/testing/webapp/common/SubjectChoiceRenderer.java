package vvsvintsitsky.testing.webapp.common;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

import vvsvintsitsky.testing.datamodel.Subject;

public class SubjectChoiceRenderer extends ChoiceRenderer<Subject> {

    public static final SubjectChoiceRenderer INSTANCE = new SubjectChoiceRenderer();

    public static String language = Session.get().getLocale().getLanguage();
    
    private SubjectChoiceRenderer() {
        super();
    }

    @Override
    public Object getDisplayValue(Subject object) {
        return object.getSubjectNames().getText(language);
    }

    @Override
    public String getIdValue(Subject object, int index) {
        return String.valueOf(object.getId());
    }
}
