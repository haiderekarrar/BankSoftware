public class CommandValidator {
	private Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String command) {

		String[] parts = command.split(" "); // Split the command into words using space as a delimiter//
		// commandValidator;
		String commandType = parts[0];
		String accountType = parts.length > 1 ? parts[1] : ""; // Account type is the second word if it exists
		String accountID = parts.length > 2 ? parts[2] : "";
		double accountApr = parts.length > 3 ? Double.parseDouble(parts[3]) : -1;
		String commandTypeUpperCase = commandType.toUpperCase();

		if (!(commandTypeUpperCase.equals("CREATE"))) {
			return false;
		}
		if (accountID.length() != 8) {
			return false; // Account ID must be 8 characters long.
		}

		// Check if accountIDStr contains only numeric characters
		if (!accountID.matches("\\d{8}")) {
			return false; // Account ID contains non-numeric characters.
		}
		if (bank.accountExistsByAccountID(Integer.parseInt(accountID))) {
			return false;
		}

		if (!(accountApr <= 10.0 && accountApr >= 0.0)) {
			return false;
		}
		return true;

	}
}
