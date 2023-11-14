public abstract class CommandValidator {
	private Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public abstract boolean validate(String command);

	protected boolean isValidAccountId(String accountID) {
		return (accountID.length() == 8 && accountID.matches("\\d{8}"));
	}

	protected boolean doesAccountExist(String accountID) {
		return bank.accountExistsByAccountID(Integer.parseInt(accountID));
	}

}