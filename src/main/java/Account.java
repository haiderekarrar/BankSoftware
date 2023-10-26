public class Account {
	private double balance;
	private double apr;

	public Account(double apr) {
		this.apr = apr;
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

	public void depositMoney(double amountToDeposit) {
		balance += amountToDeposit;
	}

	public void withdrawMoney(double amountToWithdraw) {
		balance -= amountToWithdraw;
	}

}
