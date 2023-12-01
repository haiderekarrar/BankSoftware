package banking;

public abstract class Account {
	private double balance;
	private double apr;
	private int id;
	private String accountType;

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
		}
		balance -= amountToWithdraw;
	}

}
