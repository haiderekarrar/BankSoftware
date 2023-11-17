import java.util.HashMap;
import java.util.Map;

public class Bank {
	private double deposit;

	private Map<Integer, Account> accounts;

	Bank() {
		accounts = new HashMap<>();
	}

	public Map<Integer, Account> getAccounts() {
		return accounts;
	}

	public void addAccount(String accountType, int accountId, double accountApr, double deposit) {
		if ("Checking".equals(accountType)) {
			accounts.put(accountId, new CheckingAccount(accountApr, accountId, accountType));
		}
		if ("Savings".equals(accountType)) {
			accounts.put(accountId, new SavingsAccount(accountApr, accountId, accountType));
		}
		if ("cd".equals(accountType)) {
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

	public double getBalance(int accountID) {
		return accounts.get(accountID).getBalance();
	}

	public String getAccountTypeByAccountID(int accountID) {
		return accounts.get(accountID).getAccountType();
	}
}
