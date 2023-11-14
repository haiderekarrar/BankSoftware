public class CommandValidator {

	private Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {
		String[] parts = command.split(" ");
		if (parts.length > 5 || parts.length < 3) {
			return false;
		}
		String commandType = parts[0];
		if (!(commandType.toUpperCase().equals("CREATE") || commandType.toUpperCase().equals("DEPOSIT"))) {
			return false;
		}
		return validateCommand(commandType, parts);
	}

	protected boolean validateCommand(String commandType, String[] parts) {
		// will be overridden by child classes
		return true;
	}

	protected boolean isValidAccountId(String accountID) {
		return (accountID.length() == 8 && accountID.matches("\\d{8}"));
	}

	protected boolean doesAccountExist(String accountID) {
		return bank.accountExistsByAccountID(Integer.parseInt(accountID));
	}

}
