package vvsvintsitsky.testing.webapp.page.login;

import org.apache.wicket.Application;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.string.Strings;

import vvsvintsitsky.testing.webapp.page.AbstractPage;

/**
 * 
 * 
 * @since Mar 11, 2014
 */
public class LoginPage extends AbstractPage {

    public static final String ID_FORM = "form";

    private String email;
    private String password;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        // if already logged then should not see login page at all
        if (AuthenticatedWebSession.get().isSignedIn()) {
            setResponsePage(Application.get().getHomePage());
        }

        final Form<Void> form = new Form<Void>(ID_FORM);
        form.setDefaultModel(new CompoundPropertyModel<LoginPage>(this));
        form.add(new RequiredTextField<String>("email"));
        form.add(new PasswordTextField("password"));

        form.add(new SubmitLink("submit-btn") {
            @Override
            public void onSubmit() {
                super.onSubmit();
                if (Strings.isEmpty(email) || Strings.isEmpty(password)) {
                    return;
                }
                final boolean authResult = AuthenticatedWebSession.get().signIn(email, password);
                if (authResult) {
                    // continueToOriginalDestination();
                    setResponsePage(Application.get().getHomePage());
                } else {
                    error("authorization error");
                }
            }
        });

        add(form);

        add(new FeedbackPanel("feedbackpanel"));

    }

}
