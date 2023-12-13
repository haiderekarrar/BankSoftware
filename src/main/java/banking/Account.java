package banking;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
	private final int id;
	private final List<String> accountTransactionHistory = new ArrayList<>();
	private final String accountType;
	private double balance;
	private int months = 0;
	private double apr;
	private int savingsWithdrawal = 0;
	private String accountInformation;
	private int cdWithdrawal = 1;

	public Account(double apr, int id, String accountType) {
		this.apr = apr;
		this.id = id;
		this.accountType = accountType;
	}

	public double getBalance() {

		if (balance < 0) {
			balance = 0;
		}
		return balance;
	}

	public void setBalance(double cashToDeposit) {
		balance = cashToDeposit;
	}

	public double getApr() {
		return apr;
	}

	public void setApr(double aprToSet) {
		apr = aprToSet;
	}

	public String getAccountType() {
		return accountType;
	}

	public int getId() {
		return id;
	}

	public void depositMoney(double amountToDeposit) {
		balance += amountToDeposit;
	}

	public void withdrawMoney(double amountToWithdraw) {
		if (amountToWithdraw > getBalance()) {
			balance = 0;
		} else {
			balance -= amountToWithdraw;
		}
	}

	/////////////////
	public int getMonthsPassed() {
		return months;
	}

	public void setMonthsPassed(int monthsPassed) {
		months += monthsPassed;
	}

	public int getCdWithdrawal() {
		return cdWithdrawal;
	}

	public void setCdWithdrawal(int toSet) {
		cdWithdrawal = toSet;
	}

	public int getSavingsWithdrawal() {
		return savingsWithdrawal;
	}

	public void setSavingsWithdrawal(int toSet) {
		savingsWithdrawal = toSet;
	}

	public String getAccountInformation() {
		return accountInformation;
	}

	public void setAccountInformation(String accountInfo) {
		accountInformation = accountInfo;
	}

	public List<String> getAccountTransactionHistory() {
		return accountTransactionHistory;
	}

	public void setAccountTransactionHistory(String accountHistory) {
		accountTransactionHistory.add(accountHistory);
	}

}
