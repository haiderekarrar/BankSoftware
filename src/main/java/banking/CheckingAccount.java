package banking;

public class CheckingAccount extends Account {
	private double balance;

	public CheckingAccount(double apr, int id, String accountType) {
		super(apr, id, accountType);
		balance = 0;
	}

}
