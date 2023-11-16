public class CertificateOfDeposit extends Account {

	public CertificateOfDeposit(double apr, int id, int initialDeposit, String accountType) {
		super(apr, id, accountType);
		super.setBalance(initialDeposit);
	}

}
