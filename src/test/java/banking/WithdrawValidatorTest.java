package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawValidatorTest {
	private WithdrawValidator withdrawValidator;
	private CreateValidator createValidator;
	private CommandValidator commandValidator;
	private DepositValidator depositValidator;
	private Bank bank;
	private TransferValidator transferValidator;

	@BeforeEach
	void setUp() {
		bank = new Bank();

		depositValidator = new DepositValidator(bank, depositValidator, createValidator, withdrawValidator,
				transferValidator);
		createValidator = new CreateValidator(bank, depositValidator, createValidator, withdrawValidator,
				transferValidator);
		withdrawValidator = new WithdrawValidator(bank, depositValidator, createValidator, withdrawValidator,
				transferValidator);
		transferValidator = new TransferValidator(bank, depositValidator, createValidator, withdrawValidator,
				transferValidator);
		commandValidator = new CommandValidator(bank, depositValidator, createValidator, withdrawValidator,
				transferValidator);
	}

	@Test
	void valid_withdraw_command() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "withdraw 12345677 300";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void withdraw_command_is_case_insensitive() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "wIthDraW 12345677 300";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void typo_in_withdraw() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "withdrew 12345677 800";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void withdraw_is_absent() {
		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "12345677 800";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void numeric_command() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "withdra12545w 12345677 800";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void zero_can_be_withdrawn_from_savings() {
		bank.addAccount("Savings", 12345677, 0.6, 0);
		String command = "withdraw 12345677 0";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void zero_can_be_withdrawn_from_checking() {
		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "withdraw 12345677 0";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void attempt_to_withdraw_from_account_that_does_not_exist() {
		String command = "withdraw 12345677 300";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void invalid_account_id() {

		bank.addAccount("checking", 12345677, 0.6, 0);
		String command = "withdraw 12345677888 800";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void non_numeric_withdraw() {
		bank.addAccount("Savings", 12345677, 0.6, 0);
		String command = "withdraw 12345677 asdasf";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void withdraw_can_be_a_decimal() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		String command = "withdraw 12345677 800.2";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void amount_to_withdraw_cannot_be_negative_for_savings() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		String command = "withdraw 12345677 -100";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void amount_to_withdraw_cannot_be_negative_for_checking() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "withdraw 12345677 -100";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void amount_to_withdraw_cannot_be_greater_than_1000_in_savings() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		String command = "withdraw 12345677 3000";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void amount_to_withdraw_can_be_1000_in_savings() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		String command = "withdraw 12345677 1000";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void amount_to_withdraw_can_be_999_in_savings() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		String command = "withdraw 12345677 999";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void amount_to_withdraw_cannot_be_1001_in_savings() {
		String commandType = "deposit";
		bank.addAccount("Savings", 12345677, 0.6, 0);
		String command = "withdraw 12345677 1001";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void amount_to_withdraw_cannot_be_greater_than_400_in_checking() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "withdraw 12345677 1500";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void amount_to_withdraw_cannot_be_401_in_checking() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "withdraw 12345677 401";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void amount_to_withdraw_can_be_400_in_checking() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "withdraw 12345677 400";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void amount_to_withdraw_can_be_399_in_checking() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "withdraw 12345677 399";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void amount_to_withdraw_can_be_1_in_checking() {
		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "withdraw 12345677 1";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void amount_to_withdraw_can_be_1_in_savings() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		String command = "withdraw 12345677 1";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

}