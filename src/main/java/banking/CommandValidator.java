package banking;

public class CommandValidator {

	private final Bank bank;
	private final DepositValidator depositValidator;
	private final CreateValidator createValidator;
	private final WithdrawValidator withdrawValidator;
	private final TransferValidator transferValidator;
	private final PassValidator passValidator;

	public CommandValidator(Bank bank, DepositValidator depositValidator, CreateValidator createValidator,
			WithdrawValidator withdrawValidator, TransferValidator transferValidator, PassValidator passValidator) {
		this.bank = bank;
		this.createValidator = createValidator;
		this.depositValidator = depositValidator;
		this.withdrawValidator = withdrawValidator;
		this.transferValidator = transferValidator;
		this.passValidator = passValidator;
	}

	public boolean validate(String command) {
		String[] parts = command.split(" ");
		if (parts.length > 5 || parts.length < 2) {
			return false;
		}
		String commandType = parts[0].toUpperCase();
		if (!(commandType.equals("CREATE") || commandType.equals("DEPOSIT") || commandType.equals("WITHDRAW")
				|| commandType.equals("TRANSFER") || commandType.equals("PASS"))) {
			return false;
		} else if (commandType.equals("CREATE")) {
			return createValidator.validateCommand(parts);
		} else if (commandType.equals("DEPOSIT")) {
			return depositValidator.validateCommand(parts);
		} else if (commandType.equals("PASS")) {
			return passValidator.validateCommand(parts);
		} else if (commandType.equals("WITHDRAW")) {
			return withdrawValidator.validateCommand(parts);
		} else {
			return transferValidator.validateCommand(parts);

		}
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

	// test cases not present for everything below this
	protected boolean deposit_rule_for_checking(double amountToDeposit) {
		return !(amountToDeposit > 1000);

	}

	protected boolean deposit_rule_for_savings(double amountToDeposit) {
		return !(amountToDeposit > 2500);
	}

	protected boolean withdrawal_rule_for_checking(double amountToWithdraw) {
		return !(amountToWithdraw > 400);

	}

	protected boolean withdrawal_rule_for_savings(int accountID, double amountToWithdraw) {

		if (bank.getSavingsWithdrawal(accountID) == 1) {
			return false;
		} else {
			return !(amountToWithdraw > 1000);
		}
	}

	protected boolean withdrawal_rule_for_cd(int accountID, double amountToWithdraw) {

		if (bank.getCdWithdrawal(accountID) == 1) {
			return false;
		} else {
			return !(amountToWithdraw < bank.getBalance(accountID));
		}
	}

}
