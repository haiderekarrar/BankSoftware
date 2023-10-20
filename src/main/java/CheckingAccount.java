public class CheckingAccount extends Account {
	private double balance;

	public CheckingAccount(double apr) {
		super(apr);
		balance = 0;
	}

}
