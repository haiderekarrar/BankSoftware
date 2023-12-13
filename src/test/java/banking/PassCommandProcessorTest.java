package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassCommandProcessorTest {

	private DepositCommandProcessor depositCommandProcessor;
	private Bank bank;
	private CreateCommandProcessor createCommandProcessor;
	private WithdrawCommandProcessor withdrawCommandProcessor;
	private TransferCommandProcessor transferCommandProcessor;
	private PassCommandProcessor passCommandProcessor;
	private CommandProcessor commandProcessor;

	private DepositValidator depositValidator;
	private CreateValidator createValidator;
	private CommandValidator commandValidator;
	private WithdrawValidator withdrawValidator;
	private TransferValidator transferValidator;
	private PassValidator passValidator;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		depositCommandProcessor = new DepositCommandProcessor(bank, depositCommandProcessor, createCommandProcessor,
				withdrawCommandProcessor, transferCommandProcessor, passCommandProcessor);
		createCommandProcessor = new CreateCommandProcessor(bank, depositCommandProcessor, createCommandProcessor,
				withdrawCommandProcessor, transferCommandProcessor, passCommandProcessor);
		withdrawCommandProcessor = new WithdrawCommandProcessor(bank, depositCommandProcessor, createCommandProcessor,
				withdrawCommandProcessor, transferCommandProcessor, passCommandProcessor);
		transferCommandProcessor = new TransferCommandProcessor(bank, depositCommandProcessor, createCommandProcessor,
				withdrawCommandProcessor, transferCommandProcessor, passCommandProcessor);
		passCommandProcessor = new PassCommandProcessor(bank, depositCommandProcessor, createCommandProcessor,
				withdrawCommandProcessor, transferCommandProcessor, passCommandProcessor);
		commandProcessor = new CommandProcessor(bank, depositCommandProcessor, createCommandProcessor,
				withdrawCommandProcessor, transferCommandProcessor, passCommandProcessor);

		depositValidator = new DepositValidator(bank, depositValidator, createValidator, withdrawValidator,
				transferValidator, passValidator);
		createValidator = new CreateValidator(bank, depositValidator, createValidator, withdrawValidator,
				transferValidator, passValidator);
		withdrawValidator = new WithdrawValidator(bank, depositValidator, createValidator, withdrawValidator,
				transferValidator, passValidator);
		transferValidator = new TransferValidator(bank, depositValidator, createValidator, withdrawValidator,
				transferValidator, passValidator);
		passValidator = new PassValidator(bank, depositValidator, createValidator, withdrawValidator, transferValidator,
				passValidator);
		commandValidator = new CommandValidator(bank, depositValidator, createValidator, withdrawValidator,
				transferValidator, passValidator);
	}

	@Test
	void delete_an_account_with_0_balance_after_one_month() {
		bank.addAccount("CHECKING", 12345678, 9.5, 0);
		commandProcessor.commandParser("pass 1");
		assertFalse(bank.accountExistsByAccountID(12345678));
	}

	@Test
	void delete_an_account_with_25_balance_after_two_months() {
		bank.addAccount("CHECKING", 12345678, 9.5, 0);
		commandProcessor.commandParser("depost 12345678 25");
		commandProcessor.commandParser("pass 2");
		assertFalse(bank.accountExistsByAccountID(12345678));
	}

	@Test
	void account_with_50_balance_after_one_month_should_have_25_balance() {
		bank.addAccount("CHECKING", 12345678, 9.5, 0);
		commandProcessor.commandParser("deposit 12345678 50");
		commandProcessor.commandParser("pass 1");
		assertEquals(25.395833333333336, bank.getBalance(12345678));
	}

	@Test
	void account_with_50_balance_after_two_months_should_have_a_50_deduction_balance() {
		bank.addAccount("CHECKING", 12345678, 1.2, 0);
		commandProcessor.commandParser("deposit 12345678 50");
		commandProcessor.commandParser("pass 2");
		assertEquals(0.0750499999999974, bank.getBalance(12345678));
	}

	@Test
	void account_with_50_balance_after_three_months_should_be_deleted() {
		bank.addAccount("CHECKING", 12345678, 1.1, 0);
		commandProcessor.commandParser("deposit 12345678 50");
		commandProcessor.commandParser("pass 4");
		assertFalse(bank.accountExistsByAccountID(12345678));
	}

	@Test
	void checking_account_with_1000_balance_and_3_apr_after_one_month_should_have_1002_and_a_half() {
		bank.addAccount("CHECKING", 12345678, 3, 0);
		commandProcessor.commandParser("deposit 12345678 1000");
		commandProcessor.commandParser("pass 1");
		assertEquals(1002.5, bank.getBalance(12345678));
	}

	@Test
	void savings_account_with_1000_balance_and_3_apr_after_one_month_should_have_1002_and_a_half() {
		bank.addAccount("SAVINGS", 12345678, 3, 0);
		commandProcessor.commandParser("deposit 12345678 1000");
		commandProcessor.commandParser("pass 1");
		assertEquals(1002.5, bank.getBalance(12345678));
	}

	@Test
	void cd_account_with_2000_balance_and_2_point_1_apr_after_one_month_should_have_given_value() {
		bank.addAccount("CD", 12345678, 2.1, 2000);
		commandProcessor.commandParser("pass 1");
		assertEquals(2014.0367928937578, bank.getBalance(12345678));
	}

	@Test
	void if_no_month_passed_months_passed_for_savings_equal_to_0() {
		bank.addAccount("CHECKING", 12345678, 2.1, 2000);

		assertEquals(0, bank.getMonthsPassed(12345678));
	}

	@Test
	void if_no_month_passed_months_passed_for_cd_equal_to_0() {
		bank.addAccount("CD", 12345678, 2.1, 2000);

		assertEquals(0, bank.getMonthsPassed(12345678));
	}

	@Test
	void if_1_month_passed_months_passed_for_cd_equal_to_1() {
		bank.addAccount("CD", 12345678, 2.1, 2000);
		commandProcessor.commandParser("pass 1");
		assertEquals(1, bank.getMonthsPassed(12345678));
	}

	@Test
	void if_1_month_passed_months_passed_for_savings_equal_to_1() {
		bank.addAccount("SAVINGS", 12345678, 2.1, 0);
		commandProcessor.commandParser("deposit 12345678 1000");
		commandProcessor.commandParser("pass 1");
		assertEquals(1, bank.getMonthsPassed(12345678));
	}

	@Test
	void if_3_month_passed_months_and_then_4_months_passed_for_savings_equal_to_7() {
		bank.addAccount("SAVINGS", 12345678, 2.1, 0);
		commandProcessor.commandParser("deposit 12345678 1000");
		commandProcessor.commandParser("pass 3");
		commandProcessor.commandParser("pass 4");
		assertEquals(7, bank.getMonthsPassed(12345678));
	}

	@Test
	void account_id_for_a_deleted_account_can_be_used_again_to_create_checking() {
		bank.addAccount("CHECKING", 12345678, 9.5, 0);
		commandProcessor.commandParser("deposit 12345678 50");
		commandProcessor.commandParser("pass 3");
		commandProcessor.commandParser("create checking 12345678 2.2");
		assertEquals("Checking", bank.getAccountTypeByAccountID(12345678));
	}

	@Test
	void pass_command_for_multiple_accounts_with_different_balances_after_three_months() {
		bank.addAccount("CHECKING", 12345676, 9.5, 0);
		bank.addAccount("SAVINGS", 12345677, 9.5, 0);
		bank.addAccount("CHECKING", 12345678, 9.5, 0);
		bank.addAccount("CD", 12345679, 9.5, 500);
		commandProcessor.commandParser("deposit 12345676 1000");
		commandProcessor.commandParser("deposit 12345677 95");
		commandProcessor.commandParser("deposit 12345678 50");
		commandProcessor.commandParser("pass 15");
		assertFalse(bank.accountExistsByAccountID(12345677));
		assertFalse(bank.accountExistsByAccountID(12345678));
		assertEquals(1125.5619410591048, bank.getBalance(12345676));
		assertEquals(802.5047346495595, bank.getBalance(12345679));
	}

	@Test
	void cannot_attempt_to_wothdraw_in_the_same_month_twice_command_invalid() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.depositMoneyFromBank(12345677, 300);
		commandProcessor.commandParser("withdraw 12345677 100");

		boolean actual = commandValidator.validate("withdraw 12345677 100");
		assertFalse(actual);

	}

	@Test
	void can_attempt_to_wothdraw_in_the_same_month_twice_command_valid() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.depositMoneyFromBank(12345677, 300);
		commandProcessor.commandParser("withdraw 12345677 100");
		commandProcessor.commandParser("pass 1");

		boolean actual = commandValidator.validate("withdraw 12345677 100");
		assertTrue(actual);

	}

	@Test
	void can_withdraw_from_savings_after_one_time_once_month_is_passed() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.depositMoneyFromBank(12345677, 300);
		commandProcessor.commandParser("withdraw 12345677 100");
		commandProcessor.commandParser("pass 1");
		commandProcessor.commandParser("withdraw 12345677 100");
		assertEquals(100.1, bank.getBalance(12345677));

	}

	///

	@Test
	void when_withdraw_from_cd_with_balance_greater_than_actual_amount_you_get_all_balance_out() {

		bank.addAccount("CD", 12345677, 0.6, 1000);
		bank.depositMoneyFromBank(12345677, 300);
		commandProcessor.commandParser("pass 11");
		commandProcessor.commandParser("withdraw 12345677 1500");

		assertEquals(0, bank.getBalance(12345677));

	}

	@Test
	void check_account_is_deleted_if_0_balance() {
		bank.addAccount("CHECKING", 12345678, 1.2, 0);
		commandProcessor.commandParser("pass 1");

		assertFalse(bank.accountExistsByAccountID(12345678));
	}

	@Test
	void withdraw_from_savings_twice_in_a_month_is_invalid() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		String command = "withdraw 12345677 300";
		commandValidator.validate(command);
		commandProcessor.commandParser(command);
		boolean actual = commandValidator.validate(command);

		assertFalse(actual);

	}

	@Test
	void check_account_is_deleted_if_0_balance_for_multiple_accounts() {
		bank.addAccount("CHECKING", 12345678, 1.2, 0);
		bank.addAccount("SAVINGS", 12345677, 1.2, 0);

		commandProcessor.commandParser("pass 1");
		assertFalse(bank.accountExistsByAccountID(12345678));
		assertFalse(bank.accountExistsByAccountID(12345677));
	}

	@Test
	void check_account_is_deleted_if_0_balance_for_multiple_accounts_and_one_account_is_valid() {
		bank.addAccount("CHECKING", 12345678, 1.2, 0);
		bank.addAccount("CD", 12345679, 1.2, 500);
		bank.addAccount("SAVINGS", 12345677, 1.2, 0);

		commandProcessor.commandParser("pass 1");
		assertFalse(bank.accountExistsByAccountID(12345678));
		assertFalse(bank.accountExistsByAccountID(12345677));
		assertTrue(bank.accountExistsByAccountID(12345679));
	}

	@Test
	void check_25_is_deducted_if_balance_less_than_100() {
		bank.addAccount("CHECKING", 12345678, 1.2, 0);
		bank.depositMoneyFromBank(12345678, 99);
		commandProcessor.commandParser("pass 1");

		assertEquals(74.099, bank.getBalance(12345678));
	}

	@Test
	void check_25_is_deducted_if_balance_less_than_100_over_2_months() {
		bank.addAccount("CHECKING", 12345678, 1.2, 0);
		bank.depositMoneyFromBank(12345678, 99);
		commandProcessor.commandParser("pass 2");

		assertEquals(49.17309900000001, bank.getBalance(12345678));
	}

	@Test
	void check_25_is_deducted_if_balance_less_than_100_over_2_months_for_multiple_accounts() {
		bank.addAccount("CHECKING", 12345678, 1.2, 0);
		bank.addAccount("CHECKING", 12345679, 1.2, 0);
		bank.addAccount("SAVINGS", 12345677, 1.2, 0);
		bank.depositMoneyFromBank(12345678, 99);
		bank.depositMoneyFromBank(12345679, 80);
		bank.depositMoneyFromBank(12345677, 101);
		commandProcessor.commandParser("pass 2");

		assertEquals(49.17309900000001, bank.getBalance(12345678));
		assertEquals(101.202101, bank.getBalance(12345677));
		assertEquals(30.135079999999995, bank.getBalance(12345679));
	}

	@Test
	void check_if_an_account_with_$25_gets_deleted_after_3_months() {
		bank.addAccount("SAVINGS", 12345678, 1.2, 0);
		bank.depositMoneyFromBank(12345678, 25);
		commandProcessor.commandParser("pass 3");
		assertFalse(bank.accountExistsByAccountID(12345678));

	}

}
