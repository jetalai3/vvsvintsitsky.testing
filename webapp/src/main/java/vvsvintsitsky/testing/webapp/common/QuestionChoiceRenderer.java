package vvsvintsitsky.testing.webapp.common;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.ChoiceRenderer;

import vvsvintsitsky.testing.datamodel.Question;

public class QuestionChoiceRenderer extends ChoiceRenderer<Question> {

    public static final QuestionChoiceRenderer INSTANCE = new QuestionChoiceRenderer();

    public static String language = Session.get().getLocale().getLanguage();
    
    private QuestionChoiceRenderer() {
        super();
    }

    @Override
    public Object getDisplayValue(Question object) {
        return object.getQuestionTexts().getText(language);
    }

    @Override
    public String getIdValue(Question object, int index) {
        return String.valueOf(object.getId());
    }
}
