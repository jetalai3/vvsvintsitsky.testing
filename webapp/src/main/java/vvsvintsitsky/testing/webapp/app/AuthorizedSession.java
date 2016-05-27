package vvsvintsitsky.testing.webapp.app;

import javax.inject.Inject;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;

import vvsvintsitsky.testing.dataaccess.filters.AccountFilter;
import vvsvintsitsky.testing.datamodel.Account;
import vvsvintsitsky.testing.service.AccountService;

public class AuthorizedSession extends AuthenticatedWebSession {
    @Inject
    private AccountService accountService;

    private Account loggedUser;

    private Roles roles;

    public AuthorizedSession(Request request) {
        super(request);
        Injector.get().inject(this);

    }

    public static AuthorizedSession get() {
        return (AuthorizedSession) Session.get();
    }

    @Override
    public boolean authenticate(final String email, final String password) {
        AccountFilter filter = new AccountFilter();
        filter.setEmail(email);
        filter.setPassword(password);
        if(accountService.find(filter).isEmpty() ){
        	return false;
        }
    	loggedUser = accountService.find(filter).get(0);
        return loggedUser != null;
    }

    @Override
    public Roles getRoles() {
        if (isSignedIn() && (roles == null)) {
            roles = new Roles();
            roles.addAll(accountService.resolveRoles(loggedUser.getId()));
        }
        return roles;
    }

    @Override
    public void signOut() {
        super.signOut();
        loggedUser = null;
        roles = null;
    }

    public Account getLoggedUser() {
        return loggedUser;
    }

}
