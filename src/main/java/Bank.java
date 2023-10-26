import java.util.HashMap;
import java.util.Map;

public class Bank {
	private int deposit;

	private Map<Integer, Account> accounts;

	Bank() {
		accounts = new HashMap<>();
	}

	public Map<Integer, Account> getAccounts() {
		return accounts;
	}

	public void addAccount(String accountType, int accountId, double accountApr, double deposit) {
		if (accountType == "Checking") {
			accounts.put(accountId, new CheckingAccount(accountApr, accountId));
		}
		if (accountType == "Savings") {
			accounts.put(accountId, new SavingsAccount(accountApr, accountId));
			if (accountType == "Certificate of Deposit") {
				accounts.put(accountId, new CertificateOfDeposit(accountApr, accountId, deposit));
			}

		}
	}

	public void depositMoneyFromBank(int accountId, double amountToDeposit) {
		accounts.get(accountId).depositMoney(amountToDeposit);
	}

	public void withdrawMoneyFromBank(int accountId, double amountToWithdraw) {
		accounts.get(accountId).withdrawMoney(amountToWithdraw);
	}

}
