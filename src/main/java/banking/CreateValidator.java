package banking;

public class CreateValidator extends CommandValidator {
	public CreateValidator(Bank bank, DepositValidator depositValidator, CreateValidator createValidator) {
		super(bank, depositValidator, createValidator);
	}

	public boolean validateCommand(String[] parts) {

		if (parts.length != 4 && parts.length != 5) {
			return false;
		}
		String accountType = parts[1]; // banking.Account type is the second word
		String accountTypeUpperCase = accountType.toUpperCase();

		if ("CD".equals(accountTypeUpperCase) && parts.length == 5) {
			try {
				double cdAmount = Double.parseDouble(parts[4]);
				if (cdAmount < 1000 || cdAmount > 10000) {
					return false;
				}
			} catch (NumberFormatException e) {
				// Parsing failed, return false
				return false;
			}
		} else if (("SAVINGS".equals(accountTypeUpperCase) || "CHECKING".equals(accountTypeUpperCase))
				&& parts.length != 4) {
			return false;
		}

		String accountID = parts[2];
		try {
			double accountApr = Double.parseDouble(parts[3]);
			if (accountApr > 10.0 || accountApr < 0.0) {
				return false;
			}
		} catch (NumberFormatException e) {
			// Parsing failed, return false
			return false;
		}

		if (!isValidAccountId(accountID)) {
			return false;
		}

		if (doesAccountExist(accountID)) {
			return false;
		}

		if (!(accountTypeUpperCase.equals("CHECKING") || accountTypeUpperCase.equals("SAVINGS")
				|| accountTypeUpperCase.equals("CD"))) {
			return false;
		}

		return true;
	}

}
