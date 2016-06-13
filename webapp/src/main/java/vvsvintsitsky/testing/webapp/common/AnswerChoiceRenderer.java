package vvsvintsitsky.testing.webapp.common;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

import vvsvintsitsky.testing.datamodel.Answer;

public class AnswerChoiceRenderer extends ChoiceRenderer<Answer> {

    public static final AnswerChoiceRenderer INSTANCE = new AnswerChoiceRenderer();

    public static String language = Session.get().getLocale().getLanguage();
    
    private AnswerChoiceRenderer() {
        super();
    }

    @Override
    public Object getDisplayValue(Answer object) {
        return object.getAnswerTexts().getText(language);
    }

    @Override
    public String getIdValue(Answer object, int index) {
        return String.valueOf(object.getId());
    }
}
