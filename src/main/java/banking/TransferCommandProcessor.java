package banking;

public class TransferCommandProcessor extends CommandProcessor {
	public TransferCommandProcessor(Bank bank, DepositCommandProcessor depositCommandProcessor,
			CreateCommandProcessor createCommandProcessor, WithdrawCommandProcessor withdrawCommandProcessor,
			TransferCommandProcessor transferCommandProcessor) {
		super(bank, depositCommandProcessor, createCommandProcessor, withdrawCommandProcessor,
				transferCommandProcessor);
	}

	@Override
	public void commandProcessor(String[] parts) {
		int accountIDToWithdrawFrom = Integer.parseInt(parts[1]);
		double accountBalanceOfAccountToWithdrawFrom = bank.getBalance(accountIDToWithdrawFrom);
		int accountIDToDepositTo = Integer.parseInt(parts[2]);
		double amountToTransfer = Integer.parseInt(parts[3]);
		if (accountBalanceOfAccountToWithdrawFrom < amountToTransfer) {
			amountToTransfer = accountBalanceOfAccountToWithdrawFrom;
		}
		bank.withdrawMoneyFromBank(accountIDToWithdrawFrom, amountToTransfer);
		bank.depositMoneyFromBank(accountIDToDepositTo, amountToTransfer);

	}
}
