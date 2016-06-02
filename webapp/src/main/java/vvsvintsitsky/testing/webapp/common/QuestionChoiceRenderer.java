package vvsvintsitsky.testing.webapp.common;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import vvsvintsitsky.testing.datamodel.Question;

public class QuestionChoiceRenderer extends ChoiceRenderer<Question> {

    public static final QuestionChoiceRenderer INSTANCE = new QuestionChoiceRenderer();

    private QuestionChoiceRenderer() {
        super();
    }

    @Override
    public Object getDisplayValue(Question object) {
        return object.getText();
    }

    @Override
    public String getIdValue(Question object, int index) {
        return String.valueOf(object.getId());
    }
}
