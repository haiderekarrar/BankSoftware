package banking;

public class WithdrawCommandProcessor extends CommandProcessor {

	public WithdrawCommandProcessor(Bank bank, DepositCommandProcessor depositCommandProcessor,
			CreateCommandProcessor createCommandProcessor, WithdrawCommandProcessor withdrawCommandProcessor,
			TransferCommandProcessor transferCommandProcessor) {
		super(bank, depositCommandProcessor, createCommandProcessor, withdrawCommandProcessor,
				transferCommandProcessor);
	}

	@Override
	public void commandProcessor(String[] parts) {
		int accountID = Integer.parseInt(parts[1]);
		double amountToWithdraw = Integer.parseInt(parts[2]);
		bank.withdrawMoneyFromBank(accountID, amountToWithdraw);

	}
}
