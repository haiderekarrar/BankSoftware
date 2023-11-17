public class DepositValidator extends CommandValidator {
	public DepositValidator(Bank bank, DepositValidator depositValidator, CreateValidator createValidator) {
		super(bank, depositValidator, createValidator);
	}

	public boolean validateCommand(String[] parts) {

		if (parts.length != 3) {
			return false;
		}
		String accountID = parts[1]; // Account type is the second word

		if (!isValidAccountId(accountID)) {
			return false;
		}

//account needs to exist in the first place in order to deposit 
		if (!(doesAccountExist(accountID))) {
			return false;
		}

		try {
			double amountToDeposit = Double.parseDouble(parts[2]);
			if (amountToDeposit < 0) {
				return false;

			} else if (accountTypeByAccountId(accountID).toUpperCase().equals("SAVINGS")) {
				if (amountToDeposit > 2500) {
					return false;
				}
			} else if (accountTypeByAccountId(accountID).toUpperCase().equals("CHECKING")) {
				if (amountToDeposit > 1000) {
					return false;
				}
			} else if (accountTypeByAccountId(accountID).toUpperCase().equals("CERTIFICATE OF DEPOSIT")) {
				return false;
			}

		} catch (NumberFormatException e) {
			// Parsing failed, return false
			return false;
		}

		return true;
	}
}
