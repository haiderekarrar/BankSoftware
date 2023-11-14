public class CreateValidator extends CommandValidator {
	public CreateValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String command) {

		String[] parts = command.split(" ");
		if (parts.length != 4 || parts.length != 5) {
			return false;
		}

		String commandType = parts[0];
		String commandTypeUpperCase = commandType.toUpperCase();
		String accountType = parts[1]; // Account type is the second word
		String accountTypeUpperCase = accountType.toUpperCase();

		if (!(commandTypeUpperCase.equals("CREATE"))) {
			return false;
		}

		if ("CD".equals(accountTypeUpperCase) && parts.length == 5) {
			int cdAmount = Integer.parseInt(parts[4]);
			if (cdAmount < 1000 || cdAmount > 10000) {
				return false;
			}

		} else if (("SAVINGS".equals(accountTypeUpperCase) || "CHECKING".equals(accountTypeUpperCase))
				&& parts.length != 4) {
			return false;
		}

		String accountID = parts[2];
		double accountApr = Double.parseDouble(parts[3]);

		if (!isValidAccountId(accountID)) {
			return false;
		}

		if (doesAccountExist(accountID)) {
			return false;
		}

		if (!(accountTypeUpperCase.equals("CHECKING") || accountTypeUpperCase.equals("SAVINGS"))) {
			return false;
		}

		if (accountApr > 10.0 && accountApr < 0.0) {
			return false;
		}

		return true;
	}

}
