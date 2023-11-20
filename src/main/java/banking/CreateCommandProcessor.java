package banking;

public class CreateCommandProcessor extends CommandProcessor {
	public CreateCommandProcessor(Bank bank, DepositCommandProcessor depositCommandProcessor,
			CreateCommandProcessor createCommandProcessor) {
		super(bank, depositCommandProcessor, createCommandProcessor);
	}

	@Override
	public void commandProcessor(String[] parts) {
		String accountType = parts[1];
		int accountID = Integer.parseInt(parts[2]);
		double accountApr = Double.parseDouble(parts[3]);
		double cdDeposit = (parts.length >= 5) ? Double.parseDouble(parts[4]) : 0;

		bank.addAccount(accountType, accountID, accountApr, cdDeposit);

	}
}
