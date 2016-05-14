package vvsvintsitsky.testing.dataaccess.filters;

public class AccountProfileFilter extends AbstractFilter {
	private Integer accountId;
	private String email;
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
}
