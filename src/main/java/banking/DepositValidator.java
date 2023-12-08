package banking;

public class DepositValidator extends CommandValidator {
	public DepositValidator(Bank bank, DepositValidator depositValidator, CreateValidator createValidator,
			WithdrawValidator withdrawValidator, TransferValidator transferValidator, PassValidator passValidator) {
		super(bank, depositValidator, createValidator, withdrawValidator, transferValidator, passValidator);
	}

	public boolean validateCommand(String[] parts) {

		if (parts.length != 3) {
			return false;
		}
		String accountID = parts[1]; // banking.Account type is the second word

		if (!isValidAccountId(accountID)) {
			return false;
		}

		if (!(doesAccountExist(accountID))) {
			return false;
		}

		String accountType = accountTypeByAccountId(accountID).toUpperCase();

//account needs to exist in the first place in order to deposit 

		try {
			double amountToDeposit = Double.parseDouble(parts[2]);
			if (amountToDeposit < 0) {
				return false;

			} else if (accountType.equals("SAVINGS")) {
				return deposit_rule_for_savings(amountToDeposit);

			} else if (accountType.equals("CHECKING")) {
				return deposit_rule_for_checking(amountToDeposit);

			} else if (accountType.equals("CD")) {
				return false;
			}

		} catch (NumberFormatException e) {
			// Parsing failed, return false
			return false;
		}

		return true;
	}

}
