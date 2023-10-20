import java.util.HashMap;
import java.util.Map;

public class Bank {

	private Map<Integer, Account> accounts;

	Bank() {
		accounts = new HashMap<>();
	}

	public Map<Integer, Account> getAccounts() {
		return accounts;
	}

	public void addAccount(int accountId, double accountApr) {
		accounts.put(accountId, new Account(accountApr));
	}

}
