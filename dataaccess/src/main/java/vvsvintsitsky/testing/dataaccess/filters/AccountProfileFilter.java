package vvsvintsitsky.testing.dataaccess.filters;

public class AccountProfileFilter extends AbstractFilter {
	private Long accountProfileId;
	private String firstName;
	private String lastNname;
	private boolean isFetchAccount;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastNname() {
		return lastNname;
	}

	public void setLastNname(String lastNname) {
		this.lastNname = lastNname;
	}

	public Long getAccountProfileId() {
		return accountProfileId;
	}

	public void setAccountProfileId(Long accountProfileId) {
		this.accountProfileId = accountProfileId;
	}

	public boolean isFetchAccount() {
		return isFetchAccount;
	}

	public void setFetchAccounte(boolean isFetchAccount) {
		this.isFetchAccount = isFetchAccount;
	}

}
