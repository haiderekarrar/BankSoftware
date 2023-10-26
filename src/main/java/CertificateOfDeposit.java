public class CertificateOfDeposit extends Account {

	public CertificateOfDeposit(double apr, int id, double initialDeposit) {
		super(apr, id);
		super.setBalance(initialDeposit);
	}

}
