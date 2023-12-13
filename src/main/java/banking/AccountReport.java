package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AccountReport {

	private final Bank bank;
	List<String> combinedList = new ArrayList<>();

	public AccountReport(Bank bank) {
		this.bank = bank;

	}

	public void generateReport(String command) {
		Map<Integer, Account> accounts = bank.getAccounts();
		String[] parsedCommand = command.split(" ");
		String commandType = parsedCommand[0].toUpperCase();

		for (Map.Entry<Integer, Account> entry : accounts.entrySet()) {
			int accountID = entry.getKey();
			Account account = entry.getValue();

			// Step 1: Store account details
			String accountInfo = String.format("%s %d %.2f %.2f", account.getAccountType(), accountID,
					account.getBalance(), account.getApr());
			account.setAccountInformation(accountInfo);
		}
		for (Map.Entry<Integer, Account> entry : accounts.entrySet()) {
			int accountID = entry.getKey();
			Account account = entry.getValue();

			// Step 2: Retrieve valid commands for the current account
			if (commandType.equals("TRANSFER")) {
				int commandAccountIDOne = Integer.parseInt(parsedCommand[1]);
				int commandAccountIDTwo = Integer.parseInt(parsedCommand[2]);
				if ((commandAccountIDOne == accountID) || (commandAccountIDTwo == accountID)) {
					account.setAccountTransactionHistory(command);
				}
			} else if ((commandType.equals("DEPOSIT")) || (commandType.equals("WITHDRAW"))) {
				int commandAccountID = Integer.parseInt(parsedCommand[1]);
				if (commandAccountID == accountID) {
					account.setAccountTransactionHistory(command);
				}
			}
		}

		// Iterate over all accounts to add account information and transaction history

	}

	public List<String> getCompleteBankReport() {
		Map<Integer, Account> accounts = bank.getAccounts();
		for (Map.Entry<Integer, Account> entry : accounts.entrySet()) {
			Account account = entry.getValue();

			// Add account information to the list

			combinedList.add(account.getAccountInformation());
			combinedList.addAll(account.getAccountTransactionHistory());

			// Store the combined list in the account
		}

		return new ArrayList<>(combinedList);
	}
}
