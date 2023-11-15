public class SavingsAccount extends Account {
	private double balance;

	public SavingsAccount(double apr, int id, String accountType) {

		super(apr, id, accountType);
		balance = 0;
	}

}
