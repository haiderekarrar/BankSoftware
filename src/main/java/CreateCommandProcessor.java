public class CreateCommandProcessor extends CommandProcessor {
	public CreateCommandProcessor(Bank bank) {
		super(bank);
	}

	@Override
	void commandProcessor(String[] parts) {
		String accountType = parts[1];
		int accountID = Integer.parseInt(parts[2]);
		double accountApr = Double.parseDouble(parts[3]);
		int cdDeposit = (parts.length >= 5) ? Integer.parseInt(parts[4]) : 0;

		bank.addAccount(accountType, accountID, accountApr, cdDeposit);

	}
}
