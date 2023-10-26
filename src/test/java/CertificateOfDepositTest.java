import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CertificateOfDepositTest {
	public static final double ACCOUNT_APR = 2.1;
	CertificateOfDeposit certificateOfDeposit;

	@BeforeEach
	public void setUp() {
		certificateOfDeposit = new CertificateOfDeposit(ACCOUNT_APR);
	}

	@Test
	public void cd_account_created_with_a_balance_by_default() {
		certificateOfDeposit.setBalance(100);
		double actual = certificateOfDeposit.getBalance();

		assertEquals(100, actual);

	}
}
