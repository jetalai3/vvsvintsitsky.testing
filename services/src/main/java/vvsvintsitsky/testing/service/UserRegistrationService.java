package vvsvintsitsky.testing.service;

import javax.transaction.Transactional;

public interface UserRegistrationService {
	@Transactional
	boolean userRegistaration(String firstName, String lastName, String email, String password, String role);
}
