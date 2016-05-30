package vvsvintsitsky.testing.webapp.common;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import vvsvintsitsky.testing.datamodel.Subject;
import vvsvintsitsky.testing.datamodel.UserRole;

public class SubjectChoiceRenderer extends ChoiceRenderer<Subject> {

    public static final SubjectChoiceRenderer INSTANCE = new SubjectChoiceRenderer();

    private SubjectChoiceRenderer() {
        super();
    }

    @Override
    public Object getDisplayValue(Subject object) {
        return object.getName();
    }

    @Override
    public String getIdValue(Subject object, int index) {
        return String.valueOf(object.getId());
    }
}
