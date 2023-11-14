public class CreateValidator extends CommandValidator {
	public CreateValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validateCommand(String commandType, String[] parts) {

		if (parts.length != 3 && parts.length != 4) {
			return false;
		}

		String commandTypeUpperCase = commandType.toUpperCase();
		String accountType = parts[0]; // Account type is the second word
		String accountTypeUpperCase = accountType.toUpperCase();

		if (!(commandTypeUpperCase.equals("CREATE"))) {
			return false;
		}

		if ("CD".equals(accountTypeUpperCase) && parts.length == 4) {
			int cdAmount = Integer.parseInt(parts[3]);
			if (cdAmount < 1000 || cdAmount > 10000) {
				return false;
			}

		} else if (("SAVINGS".equals(accountTypeUpperCase) || "CHECKING".equals(accountTypeUpperCase))
				&& parts.length != 3) {
			return false;
		}

		String accountID = parts[1];
		double accountApr = Double.parseDouble(parts[2]);

		if (accountApr > 10.0 || accountApr < 0.0) {
			return false;
		}

		if (!isValidAccountId(accountID)) {
			return false;
		}

		if (doesAccountExist(accountID)) {
			return false;
		}

		if (!(accountTypeUpperCase.equals("CHECKING") || accountTypeUpperCase.equals("SAVINGS"))) {
			return false;
		}

		return true;
	}

}
