package banking;

public class WithdrawCommandProcessor extends CommandProcessor {

	public WithdrawCommandProcessor(Bank bank, DepositCommandProcessor depositCommandProcessor,
			CreateCommandProcessor createCommandProcessor, WithdrawCommandProcessor withdrawCommandProcessor,
			TransferCommandProcessor transferCommandProcessor, PassCommandProcessor passCommandProcessor) {
		super(bank, depositCommandProcessor, createCommandProcessor, withdrawCommandProcessor, transferCommandProcessor,
				passCommandProcessor);
	}

	@Override
	public void commandProcessor(String[] parts) {
		int accountID = Integer.parseInt(parts[1]);
		double amountToWithdraw = Integer.parseInt(parts[2]);
		String accountType = bank.getAccountTypeByAccountID(accountID).toUpperCase();

		if (accountType.equals("CD")) {
			amountToWithdraw = bank.getBalance(accountID);
			bank.setCdWithdrawal(accountID, 1);
		}

		if (accountType.equals("SAVINGS")) {
			bank.setSavingsWithdrawal(accountID, 1);
		}
		bank.withdrawMoneyFromBank(accountID, amountToWithdraw);
	}
}
