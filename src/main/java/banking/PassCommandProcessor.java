package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PassCommandProcessor extends CommandProcessor {
	public PassCommandProcessor(Bank bank, DepositCommandProcessor depositCommandProcessor,
			CreateCommandProcessor createCommandProcessor, WithdrawCommandProcessor withdrawCommandProcessor,
			TransferCommandProcessor transferCommandProcessor, PassCommandProcessor passCommandProcessor) {
		super(bank, depositCommandProcessor, createCommandProcessor, withdrawCommandProcessor, transferCommandProcessor,
				passCommandProcessor);

	}

	@Override
	public void commandProcessor(String[] parts) {
		int month = Integer.parseInt(parts[1]);
		int count = 0;

		Map<Integer, Account> accounts = bank.getAccounts();
		List<Integer> accountsToRemove = new ArrayList<>();

		for (Map.Entry<Integer, Account> entry : accounts.entrySet()) {

			while (count != month) {
				int accountID = entry.getKey();
				String accountType = entry.getValue().getAccountType();

				double balance = entry.getValue().getBalance();
				double apr = entry.getValue().getApr();

				if (accountType.equals("Savings") || accountType.equals("Checking")) {
					double moneyFromApr = balance * (apr / 1200);
					bank.depositMoneyFromBank(accountID, moneyFromApr);
				}

				if (accountType.equals("Cd")) {
					int i = 0;
					while (i < 4) {
						double moneyFromApr = balance * (apr / 1200);
						bank.depositMoneyFromBank(accountID, moneyFromApr);
						balance = entry.getValue().getBalance();
						i += 1;
					}
				}

				if (balance == 0) {
					accountsToRemove.add(accountID);
					// Additional actions if needed
				} else if (balance < 100) {
					bank.withdrawMoneyFromBank(accountID, 25);
				}
				count += 1;
				if (accountType.equals("Savings")) {
					bank.setSavingsWithdrawal(accountID, 0);
				}

				bank.setMonthsPassed(accountID, 1);
				if ((bank.getMonthsPassed(accountID) % 12 == 0) && accountType.equals("Cd")) {
					bank.setCdWithdrawal(accountID, 0);
				}

			}
			count = 0;
		}

		// Remove accounts outside the loop
		for (int accountIdToRemove : accountsToRemove) {
			accounts.remove(accountIdToRemove);
		}

		// Additional processing for remaining accounts

	}

}