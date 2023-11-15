public class DepositValidator extends CommandValidator {
	public DepositValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validateCommand(String commandType, String[] parts) {

		if (parts.length != 2) {
			return false;
		}

		String commandTypeUpperCase = commandType.toUpperCase();
		String accountID = parts[0]; // Account type is the second word

		if (!(commandTypeUpperCase.equals("DEPOSIT"))) {
			return false;
		}

		if (!isValidAccountId(accountID)) {
			return false;
		}

//account needs to exist in the first place in order to deposit 
		if (!(doesAccountExist(accountID))) {
			return false;
		}

		try {
			int amountToDeposit = Integer.parseInt(parts[1]);
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
			} else if (accountTypeByAccountId(accountID).toUpperCase().equals("CD")) {
				return false;
			}

		} catch (NumberFormatException e) {
			// Parsing failed, return false
			return false;
		}

		return true;
	}
}
