package vvsvintsitsky.testing.webapp.common;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import vvsvintsitsky.testing.datamodel.Answer;

public class AnswerChoiceRenderer extends ChoiceRenderer<Answer> {

    public static final AnswerChoiceRenderer INSTANCE = new AnswerChoiceRenderer();

    private AnswerChoiceRenderer() {
        super();
    }

    @Override
    public Object getDisplayValue(Answer object) {
        return object.getText();
    }

    @Override
    public String getIdValue(Answer object, int index) {
        return String.valueOf(object.getId());
    }
}
