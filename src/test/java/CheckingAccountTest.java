import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckingAccountTest {
	public static final double ACCOUNT_APR = 2.1;
	SavingsAccount checkingAccount;

	@BeforeEach
	public void setUp() {
		checkingAccount = new SavingsAccount(ACCOUNT_APR);
	}

	@Test
	public void checking_account_created_with_no_balance_by_default() {
		double actual = checkingAccount.getBalance();

		assertEquals(0, actual);

	}

}
