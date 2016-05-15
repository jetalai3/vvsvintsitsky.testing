package vvsvintsitsky.testing.dataaccess.filters;

import javax.persistence.metamodel.SingularAttribute;

public class AccountFilter extends AbstractFilter {

	private Long Id;
	private String password;
	private String email;
	private Integer role;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

}
