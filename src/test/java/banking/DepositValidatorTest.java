package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositValidatorTest {
	private DepositValidator depositValidator;
	private CreateValidator createValidator;
	private CommandValidator commandValidator;
	private Bank bank; // Replace with your actual banking.Bank implementation

	@BeforeEach
	void setUp() {
		bank = new Bank();

		createValidator = new CreateValidator(bank, depositValidator, createValidator);
		depositValidator = new DepositValidator(bank, depositValidator, createValidator);
		commandValidator = new CommandValidator(bank, depositValidator, createValidator);
	}

	@Test
	void valid_deposit_command() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "deposit 12345677 800";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void deposit_command_is_case_insensitive() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "dEposIt 12345677 800";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void typo_in_deposit() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "dEeposIt 12345677 800";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void deposit_is_absent() {
		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "12345677 800";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void numeric_command() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "dEeposI12545t 12345677 800";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void zero_can_be_deposited_in_savings() {
		bank.addAccount("Savings", 12345677, 0.6, 0);
		String command = "deposit 12345677 0";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void cd_cannot_be_deposited_into() {
		bank.addAccount("Certificate of Deposit", 12345677, 0.6, 1200);
		String command = "deposit 12345677 800";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void zero_cannot_be_deposited_into_cd() {
		bank.addAccount("Certificate of Deposit", 12345677, 0.6, 0);
		String command = "deposit 12345677 0";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void attempt_to_deposit_into_account_that_does_not_exist() {
		String command = "deposit 12345677 800";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void invalid_account_id() {

		bank.addAccount("checking", 12345677, 0.6, 0);
		String command = "deposit 12345677888 800";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void non_numeric_deposit() {
		bank.addAccount("Savings", 12345677, 0.6, 0);
		String command = "deposit 12345677 asdasf";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void deposit_can_be_a_decimal() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		String command = "deposit 12345677 800.2";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void amount_to_deposit_cannot_be_negative_for_savings() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		String command = "deposit 12345677 -100";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void zero_can_be_deposited_in_checking() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "deposit 12345677 0";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void amount_to_deposit_cannot_be_negative_for_checking() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "deposit 12345677 -100";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void amount_to_deposit_cannot_be_greater_than_2500_in_savings() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		String command = "deposit 12345677 3000";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void amount_to_deposit_can_be_2500_in_savings() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		String command = "deposit 12345677 2500";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void amount_to_deposit_can_be_2499_in_savings() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		String command = "deposit 12345677 2499";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void amount_to_deposit_cannot_be_2501_in_savings() {
		String commandType = "deposit";
		bank.addAccount("Savings", 12345677, 0.6, 0);
		String command = "deposit 12345677 2501";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void amount_to_deposit_cannot_be_greater_than_1000_in_checking() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "deposit 12345677 1500";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void amount_to_deposit_cannot_be_1001_in_checking() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "deposit 12345677 1001";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void amount_to_deposit_can_be_1000_in_checking() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "deposit 12345677 1000";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void amount_to_deposit_can_be_999_in_checking() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "deposit 12345677 999";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void amount_to_deposit_can_be_1_in_checking() {
		bank.addAccount("Checking", 12345677, 0.6, 0);
		String command = "deposit 12345677 1";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void amount_to_deposit_can_be_1_in_savings() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		String command = "deposit 12345677 1";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

}
