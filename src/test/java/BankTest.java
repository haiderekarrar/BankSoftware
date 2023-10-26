import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
	public static final int ACCOUNT_ID = 14516562;
	public static final double ACCOUNT_APR = 2.1;

	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
	}

	@Test
	public void bank_has_no_accounts_initially() {
		assertTrue(bank.getAccounts().isEmpty());

	}

	@Test
	public void add_one_account_to_bank() {
		bank.addAccount(ACCOUNT_ID, ACCOUNT_APR);
		assertEquals(ACCOUNT_APR, bank.getAccounts().get(ACCOUNT_ID).getApr());
	}

	@Test
	void add_two_accounts_to_bank() {
		bank.addAccount(ACCOUNT_ID, ACCOUNT_APR);
		bank.addAccount(ACCOUNT_ID + 1, ACCOUNT_APR + 1);
		assertEquals(ACCOUNT_APR + 1, bank.getAccounts().get(ACCOUNT_ID + 1).getApr());
	}

	@Test
	void retrieve_one_account() {
		bank.addAccount(ACCOUNT_ID, ACCOUNT_APR);
		assertEquals(ACCOUNT_APR, bank.getAccounts().get(ACCOUNT_ID).getApr());
	}

	@Test
	void depositing_money_once_by_id() {
		bank.addAccount(ACCOUNT_ID, ACCOUNT_APR);
		bank.getAccounts().get(ACCOUNT_ID).depositMoney(250.9);
		assertEquals(250.9, bank.getAccounts().get(ACCOUNT_ID).getBalance());
	}

	@Test
	void depositing_money_twice_by_id() {
		bank.addAccount(ACCOUNT_ID, ACCOUNT_APR);
		bank.getAccounts().get(ACCOUNT_ID).depositMoney(250.9);
		bank.getAccounts().get(ACCOUNT_ID).depositMoney(1.1);
		assertEquals(252.0, bank.getAccounts().get(ACCOUNT_ID).getBalance());
	}

	@Test
	void withdrawing_money_once_by_id() {
		bank.addAccount(ACCOUNT_ID, ACCOUNT_APR);
		bank.getAccounts().get(ACCOUNT_ID).depositMoney(250.9);
		bank.getAccounts().get(ACCOUNT_ID).depositMoney(1.1);
		bank.getAccounts().get(ACCOUNT_ID).withdrawMoney(50);
		assertEquals(202.0, bank.getAccounts().get(ACCOUNT_ID).getBalance());
	}

	@Test
	void withdrawing_money_twice_by_id() {
		bank.addAccount(ACCOUNT_ID, ACCOUNT_APR);
		bank.getAccounts().get(ACCOUNT_ID).depositMoney(250.9);
		bank.getAccounts().get(ACCOUNT_ID).depositMoney(1.1);
		bank.getAccounts().get(ACCOUNT_ID).withdrawMoney(50);
		bank.getAccounts().get(ACCOUNT_ID).withdrawMoney(100.1);
		assertEquals(101.9, bank.getAccounts().get(ACCOUNT_ID).getBalance());
	}

	@Test
	void withdrawing_money_twice_by_id_balance_balance_does_not_go_below_zero() {
		bank.addAccount(ACCOUNT_ID, ACCOUNT_APR);
		bank.getAccounts().get(ACCOUNT_ID).depositMoney(250.9);
		bank.getAccounts().get(ACCOUNT_ID).depositMoney(1.1);
		bank.getAccounts().get(ACCOUNT_ID).withdrawMoney(50);
		bank.getAccounts().get(ACCOUNT_ID).withdrawMoney(300.1);
		assertEquals(0.0, bank.getAccounts().get(ACCOUNT_ID).getBalance());
	}

}
