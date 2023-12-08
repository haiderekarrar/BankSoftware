package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/* 
ask whether giving an ammount to addaccount is okay since I seperate checking/saving and cd in bank.java so is it fine if i take deposit for add account)

 */
public class BankTest {
	public static final int ACCOUNT_ID = 14516562;
	public static final double ACCOUNT_APR = 2.1;
	public static final String ACCOUNT_TYPE = "CHECKING";
	public static final int CD_DEPOSIT = 150;

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
	public void add_one_saving_or_checking_account_to_bank() {
		bank.addAccount(ACCOUNT_TYPE, ACCOUNT_ID, ACCOUNT_APR, CD_DEPOSIT);
		assertEquals(ACCOUNT_ID, bank.getAccounts().get(ACCOUNT_ID).getId());
	}

	@Test
	void add_two_accounts_to_bank() {
		bank.addAccount(ACCOUNT_TYPE, ACCOUNT_ID, ACCOUNT_APR, CD_DEPOSIT);
		bank.addAccount(ACCOUNT_TYPE, ACCOUNT_ID + 1, ACCOUNT_APR + 1, CD_DEPOSIT);
		assertEquals(ACCOUNT_ID + 1, bank.getAccounts().get(ACCOUNT_ID + 1).getId());
	}

	@Test
	void retrieve_one_account() {
		bank.addAccount(ACCOUNT_TYPE, ACCOUNT_ID, ACCOUNT_APR, CD_DEPOSIT);
		assertEquals(ACCOUNT_ID, bank.getAccounts().get(ACCOUNT_ID).getId());
	}

	@Test
	void CD_DEPOSITing_money_once_by_id() {
		bank.addAccount(ACCOUNT_TYPE, ACCOUNT_ID, ACCOUNT_APR, CD_DEPOSIT);
		bank.depositMoneyFromBank(ACCOUNT_ID, 100);
		assertEquals(100, bank.getAccounts().get(ACCOUNT_ID).getBalance());
	}

	@Test
	void CD_DEPOSITing_money_twice_by_id() {
		bank.addAccount(ACCOUNT_TYPE, ACCOUNT_ID, ACCOUNT_APR, CD_DEPOSIT);
		bank.depositMoneyFromBank(ACCOUNT_ID, 100);
		bank.depositMoneyFromBank(ACCOUNT_ID, 200);
		assertEquals(300, bank.getAccounts().get(ACCOUNT_ID).getBalance());
	}

	@Test
	void withdrawing_money_once_by_id() {
		bank.addAccount(ACCOUNT_TYPE, ACCOUNT_ID, ACCOUNT_APR, CD_DEPOSIT);
		bank.depositMoneyFromBank(ACCOUNT_ID, 200);
		bank.withdrawMoneyFromBank(ACCOUNT_ID, 50);
		assertEquals(150, bank.getAccounts().get(ACCOUNT_ID).getBalance());
	}

	@Test
	void withdrawing_money_twice_by_id() {
		bank.addAccount(ACCOUNT_TYPE, ACCOUNT_ID, ACCOUNT_APR, CD_DEPOSIT);
		bank.depositMoneyFromBank(ACCOUNT_ID, 200);
		bank.withdrawMoneyFromBank(ACCOUNT_ID, 50);
		bank.withdrawMoneyFromBank(ACCOUNT_ID, 10);
		assertEquals(140, bank.getAccounts().get(ACCOUNT_ID).getBalance());
	}

	@Test
	void withdrawing_money_twice_by_id_balance_balance_does_not_go_below_zero() {
		bank.addAccount(ACCOUNT_TYPE, ACCOUNT_ID, ACCOUNT_APR, CD_DEPOSIT);
		bank.depositMoneyFromBank(ACCOUNT_ID, 200);
		bank.withdrawMoneyFromBank(ACCOUNT_ID, 50);
		bank.withdrawMoneyFromBank(ACCOUNT_ID, 200);
		assertEquals(0, bank.getAccounts().get(ACCOUNT_ID).getBalance());
	}

}
