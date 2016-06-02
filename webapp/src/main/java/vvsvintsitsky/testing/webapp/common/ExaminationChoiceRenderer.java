package vvsvintsitsky.testing.webapp.common;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import vvsvintsitsky.testing.datamodel.Examination;

public class ExaminationChoiceRenderer extends ChoiceRenderer<Examination> {

    public static final ExaminationChoiceRenderer INSTANCE = new ExaminationChoiceRenderer();

    private ExaminationChoiceRenderer() {
        super();
    }

    @Override
    public Object getDisplayValue(Examination object) {
        return object.getName();
    }

    @Override
    public String getIdValue(Examination object, int index) {
        return String.valueOf(object.getId());
    }
}
