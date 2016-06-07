package vvsvintsitsky.testing.webapp.component.menu;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import vvsvintsitsky.testing.datamodel.AccountProfile;
import vvsvintsitsky.testing.datamodel.UserRole;
import vvsvintsitsky.testing.webapp.app.AuthorizedSession;
import vvsvintsitsky.testing.webapp.page.account.AccountEditPage;
import vvsvintsitsky.testing.webapp.page.account.AccountsPage;
import vvsvintsitsky.testing.webapp.page.examination.ExaminationsPage;
import vvsvintsitsky.testing.webapp.page.home.HomePage;
import vvsvintsitsky.testing.webapp.page.login.LoginPage;
import vvsvintsitsky.testing.webapp.page.question.QuestionsPage;
import vvsvintsitsky.testing.webapp.page.subject.SubjectsPage;

public class MenuPanel extends Panel {

    public MenuPanel(String id) {
        super(id);
        
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        boolean isSignedIn = AuthorizedSession.get().isSignedIn();
		UserRole role = UserRole.GUEST;
		AccountProfile loggedUser = AuthorizedSession.get().getLoggedUser();
		
		if(loggedUser != null) {
			role = loggedUser.getAccount().getRole();
		}
		
        add(new Link("link-home") {
            @Override
            public void onClick() {
                setResponsePage(new HomePage());
            }
        });

        add(new Link("link-accounts") {
            @Override
            public void onClick() {
                setResponsePage(new AccountsPage());
            }
        }.setVisible(isSignedIn && role.equals(UserRole.ADMIN)));
        
        add(new Link("link-questions") {
            @Override
            public void onClick() {
                setResponsePage(new QuestionsPage());
            }
            
        }.setVisible(isSignedIn));
        
        add(new Link("link-subjects") {
            @Override
            public void onClick() {
                setResponsePage(new SubjectsPage());
            }
            
        }.setVisible(isSignedIn && role.equals(UserRole.ADMIN)));
        
        add(new Link("link-examinations") {
            @Override
            public void onClick() {
                setResponsePage(new ExaminationsPage());
            }
            
        }.setVisible(isSignedIn));
        
        add(new Link<Void>("link-personal") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new AccountEditPage(loggedUser));
			}
		}.setVisible(isSignedIn));
        
        add(new Link<Void>("link-login") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(LoginPage.class);
			}
		}.setVisible(!isSignedIn));
        
        add( new Link<Void>("link-logout") {
            @Override
            public void onClick() {
                getSession().invalidate();
                setResponsePage(LoginPage.class);
            }
        }.setVisible(isSignedIn));
        
        
    }
}
