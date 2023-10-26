public abstract class Account {
	private double balance;
	private double apr;
	private int id;

	public Account(double apr, int id) {
		this.apr = apr;
		this.id = id;
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

	public int getId() {
		return id;
	}

	public void setId(int idToSet) {
		id = idToSet;
	}

	public void depositMoney(double amountToDeposit) {
		balance += amountToDeposit;
	}

	public void withdrawMoney(double amountToWithdraw) {
		balance -= amountToWithdraw;
	}

}
