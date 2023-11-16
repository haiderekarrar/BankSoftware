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

	public void addAccount(String accountType, int accountId, double accountApr, int deposit) {
		if (accountType == "Checking") {
			accounts.put(accountId, new CheckingAccount(accountApr, accountId, accountType));
		}
		if (accountType == "Savings") {
			accounts.put(accountId, new SavingsAccount(accountApr, accountId, accountType));
		}
		if (accountType == "Certificate of Deposit") {
			accounts.put(accountId, new CertificateOfDeposit(accountApr, accountId, deposit, accountType));
		}

	}

	public void depositMoneyFromBank(int accountId, double amountToDeposit) {
		accounts.get(accountId).depositMoney(amountToDeposit);
	}

	public void withdrawMoneyFromBank(int accountId, double amountToWithdraw) {
		accounts.get(accountId).withdrawMoney(amountToWithdraw);
	}

	public boolean accountExistsByAccountID(int accountID) {
		if (accounts.get(accountID) != null) {
			return true;
		} else {
			return false;
		}
	}

	public double getAccountApr(int accountID) {
		return accounts.get(accountID).getApr();
	}

	public int getBalance(int accountID) {
		return accounts.get(accountID).getBalance();
	}

	public String getAccountTypeByAccountID(int accountID) {
		return accounts.get(accountID).getAccountType();
	}
}
