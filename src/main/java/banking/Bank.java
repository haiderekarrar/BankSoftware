package banking;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Bank {

	private final Map<Integer, Account> accounts;

	public Bank() {
		accounts = new LinkedHashMap<>();
	}

	public Map<Integer, Account> getAccounts() {
		return accounts;
	}

	public void addAccount(String accountType, int accountId, double accountApr, double deposit) {
		if ("CHECKING".equals(accountType)) {
			accountType = "Checking";
			accounts.put(accountId, new CheckingAccount(accountApr, accountId, accountType));
		}
		if ("SAVINGS".equals(accountType)) {
			accountType = "Savings";
			accounts.put(accountId, new SavingsAccount(accountApr, accountId, accountType));
		}
		if ("CD".equals(accountType)) {
			accountType = "Cd";
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
		return accounts.get(accountID) != null;
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

	public int getMonthsPassed(int accountID) {
		return accounts.get(accountID).getMonthsPassed();
	}

	public void setMonthsPassed(int accountID, int monthsPassed) {
		accounts.get(accountID).setMonthsPassed(monthsPassed);
	}

	public int getSavingsWithdrawal(int accountID) {
		return accounts.get(accountID).getSavingsWithdrawal();
	}

	public void setSavingsWithdrawal(int accountID, int toSet) {
		accounts.get(accountID).setSavingsWithdrawal(toSet);
	}

	public int getCdWithdrawal(int accountID) {
		return accounts.get(accountID).getCdWithdrawal();
	}

	public void setCdWithdrawal(int accountID, int toSet) {
		accounts.get(accountID).setCdWithdrawal(toSet);
	}

	public String getAccountReportbyAccountID(int accountID) {
		return accounts.get(accountID).getAccountInformation();
	}

	public List<String> getAccountTransactionHistorybyAccountID(int accountID) {
		return accounts.get(accountID).getAccountTransactionHistory();
	}

}
