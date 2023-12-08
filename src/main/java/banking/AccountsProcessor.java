package banking;

import java.util.Iterator;
import java.util.Map;

public class AccountsProcessor {

	public void processAccounts(Bank bank, int month) {
		Map<Integer, Account> accounts = bank.getAccounts();
		Iterator<Map.Entry<Integer, Account>> iterator = accounts.entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry<Integer, Account> entry = iterator.next();
			int accountID = entry.getKey();
			String accountType = entry.getValue().getAccountType();
			double balance = entry.getValue().getBalance();
			double apr = entry.getValue().getApr();

			if (accountType == "SAVINGS" || accountType == "CHECKING") {
				double moneyFromApr = balance * (apr / 1200);
				bank.depositMoneyFromBank(accountID, moneyFromApr);

			}

			if (accountType == "CD") {

				int i = 0;
				while (i < 4) {
					double moneyFromApr = balance * (apr / 1200);
					bank.depositMoneyFromBank(accountID, moneyFromApr);
					balance = entry.getValue().getBalance();
					i += 1;
				}

			}

			if (balance == 0) {
				iterator.remove();
				// Additional actions if needed
			} else if (balance < 100) {
				bank.withdrawMoneyFromBank(accountID, 25);
			}
		}

	}
}
