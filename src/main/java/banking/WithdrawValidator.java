package banking;

public class WithdrawValidator extends CommandValidator {

	public WithdrawValidator(Bank bank, DepositValidator depositValidator, CreateValidator createValidator,
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
			double amountToWithdraw = Double.parseDouble(parts[2]);
			if (amountToWithdraw < 0) {
				return false;

			} else if (accountType.equals("SAVINGS")) {
				return withdrawal_rule_for_savings(Integer.parseInt(accountID), amountToWithdraw);
			} else if (accountType.equals("CHECKING")) {
				return withdrawal_rule_for_checking(amountToWithdraw);
			} else if (accountType.equals("CD")) {
				return withdrawal_rule_for_cd(Integer.parseInt(accountID), amountToWithdraw);
			}

		} catch (NumberFormatException e) {
			// Parsing failed, return false
			return false;
		}

		return true;
	}
}
