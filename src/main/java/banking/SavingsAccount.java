package banking;

public class SavingsAccount extends Account {
	private double balance;
	private int months;

	public SavingsAccount(double apr, int id, String accountType) {

		super(apr, id, accountType);
		balance = 0;
		months = 0;
	}

}
