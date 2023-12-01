package banking;

public class CommandValidator {

	private Bank bank;
	private DepositValidator depositValidator;
	private CreateValidator createValidator;
	private WithdrawValidator withdrawValidator;
	private TransferValidator transferValidator;

	public CommandValidator(Bank bank, DepositValidator depositValidator, CreateValidator createValidator,
			WithdrawValidator withdrawValidator, TransferValidator transferValidator) {
		this.bank = bank;
		this.createValidator = createValidator;
		this.depositValidator = depositValidator;
		this.withdrawValidator = withdrawValidator;
		this.transferValidator = transferValidator;
	}

	public boolean validate(String command) {
		String[] parts = command.split(" ");
		if (parts.length > 5 || parts.length < 3) {
			return false;
		}
		String commandType = parts[0];
		if (!(commandType.toUpperCase().equals("CREATE") || commandType.toUpperCase().equals("DEPOSIT")
				|| commandType.toUpperCase().equals("WITHDRAW") || commandType.toUpperCase().equals("TRANSFER"))) {
			return false;
		} else if (commandType.toUpperCase().equals("CREATE")) {
			return createValidator.validateCommand(parts);
		} else if (commandType.toUpperCase().equals("DEPOSIT")) {
			return depositValidator.validateCommand(parts);
		} else if (commandType.toUpperCase().equals("WITHDRAW")) {
			return withdrawValidator.validateCommand(parts);
		} else if (commandType.toUpperCase().equals("TRANSFER")) {
			return transferValidator.validateCommand(parts);
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

	// test cases niot present for everything below this
	protected boolean deposit_rule_for_checking(double amountToDeposit) {
		if (amountToDeposit > 1000) {
			return false;
		}
		return true;

	}

	protected boolean deposit_rule_for_savings(double amountToDeposit) {
		if (amountToDeposit > 2500) {
			return false;
		}
		return true;
	}

	protected boolean withdrawal_rule_for_checking(double amountToWithdraw) {
		if (amountToWithdraw > 400) {
			return false;
		}
		return true;

	}

	protected boolean withdrawal_rule_for_savings(double amountToWithdraw) {
		if (amountToWithdraw > 1000) {
			return false;
		}
		return true;
	}

}
