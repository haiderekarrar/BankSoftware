import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CertificateOfDepositTest {
	public static final double ACCOUNT_APR = 2.1;
	public static final int ACCOUNT_ID = 12345678;
	public static final double DEPOSIT = 100.1;

	CertificateOfDeposit certificateOfDeposit;

	@Test
	public void cd_account_created_with_a_balance_by_default() {
		certificateOfDeposit = new CertificateOfDeposit(ACCOUNT_APR, ACCOUNT_ID, DEPOSIT);
		double actual = certificateOfDeposit.getBalance();

		assertEquals(100.1, actual);

	}
}
