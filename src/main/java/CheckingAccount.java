public class CheckingAccount extends Account {
	private double balance;

	public CheckingAccount(double apr, int id) {
		super(apr, id);
		balance = 0;
	}

}
