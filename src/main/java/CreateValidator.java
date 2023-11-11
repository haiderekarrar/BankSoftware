public class CreateValidator extends CommandValidator {
	public CreateValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validate(String command) {
		String[] parts = command.split(" "); // Split the command into words using space as a delimiter//
		// commandValidator;
		if (parts.length < 4) {
			return false; // not enough parameters for create command
		}

		String commandType = parts[0];
		String accountType = parts[1]; // Account type is the second word
		String accountID = parts[2];
		double accountApr = Double.parseDouble(parts[3]);
		String commandTypeUpperCase = commandType.toUpperCase();
		String accountTypeUpperCase = accountType.toUpperCase();

		if (!(commandTypeUpperCase.equals("CREATE"))) {
			return false;
		}

		if (!isValidAccountId(accountID)) {
			return false;
		}

		if (doesAccountExist(accountID)) {
			return false;
		}

		if (!(accountTypeUpperCase.equals("CHECKING") || accountTypeUpperCase.equals("SAVINGS")
				|| accountTypeUpperCase.equals("CERTIFICATEOFDEPOSIT"))) {
			return false;
		}

		if (accountApr > 10.0 && accountApr < 0.0) {
			return false;
		}

		return true;
	}

}
