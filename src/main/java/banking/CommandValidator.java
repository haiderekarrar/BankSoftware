package banking;

public class CommandValidator {

	private Bank bank;
	private DepositValidator depositValidator;
	private CreateValidator createValidator;

	public CommandValidator(Bank bank, DepositValidator depositValidator, CreateValidator createValidator) {
		this.bank = bank;
		this.createValidator = createValidator;
		this.depositValidator = depositValidator;
	}

	public boolean validate(String command) {
		String[] parts = command.split(" ");
		if (parts.length > 5 || parts.length < 3) {
			return false;
		}
		String commandType = parts[0];
		if (!(commandType.toUpperCase().equals("CREATE") || commandType.toUpperCase().equals("DEPOSIT"))) {
			return false;
		} else if (commandType.toUpperCase().equals("CREATE")) {
			return createValidator.validateCommand(parts);
		} else if (commandType.toUpperCase().equals("DEPOSIT")) {
			return depositValidator.validateCommand(parts);
		}
		return true;
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

	protected String accountTypeByAccountId(String accountID) {
		return bank.getAccountTypeByAccountID(Integer.parseInt(accountID));
	}

}
