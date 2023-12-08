package banking;

public class CertificateOfDeposit extends Account {
	private int month;

	public CertificateOfDeposit(double apr, int id, double initialDeposit, String accountType) {
		super(apr, id, accountType);
		super.setBalance(initialDeposit);
		month = 0;

	}

}
