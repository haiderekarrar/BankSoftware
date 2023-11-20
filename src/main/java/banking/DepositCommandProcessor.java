package banking;

public class DepositCommandProcessor extends CommandProcessor {
	public DepositCommandProcessor(Bank bank, DepositCommandProcessor depositCommandProcessor,
			CreateCommandProcessor createCommandProcessor) {
		super(bank, depositCommandProcessor, createCommandProcessor);
	}

	@Override
	public void commandProcessor(String[] parts) {
		int accountID = Integer.parseInt(parts[1]);
		double amountToDeposit = Integer.parseInt(parts[2]);
		bank.depositMoneyFromBank(accountID, amountToDeposit);

	}

}
