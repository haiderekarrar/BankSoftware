public class SavingsAccount extends Account {
	private double balance;

	public SavingsAccount(double apr, int id) {

		super(apr, id);
		balance = 0;
	}

}
