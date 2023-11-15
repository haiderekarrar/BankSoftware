import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositValidatorTest {
	private DepositValidator depositValidator;
	private Bank bank; // Replace with your actual Bank implementation

	@BeforeEach
	void setUp() {
		bank = new Bank(); // Replace with actual implementation
		depositValidator = new DepositValidator(bank);
	}

	@Test
	void valid_deposit_command() {
		String commandType = "deposit";
		bank.addAccount("Checking", 12345677, 0.6, 0);
		String[] parts = { "12345677", "800" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertTrue(actual);

	}

	@Test
	void deposit_command_is_case_insensitive() {
		String commandType = "dEposIt";
		bank.addAccount("Checking", 12345677, 0.6, 0);
		String[] parts = { "12345677", "800" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertTrue(actual);

	}

	@Test
	void typo_in_deposit() {
		String commandType = "dEeposIt";
		bank.addAccount("Checking", 12345677, 0.6, 0);
		String[] parts = { "12345677", "800" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertFalse(actual);

	}

	@Test
	void deposit_is_absent() {
		String commandType = "";
		bank.addAccount("Checking", 12345677, 0.6, 0);
		String[] parts = { "12345677", "800" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertFalse(actual);

	}

	@Test
	void numeric_command() {
		String commandType = "dEeposI12545t";
		bank.addAccount("Checking", 12345677, 0.6, 0);
		String[] parts = { "12345677", "800" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertFalse(actual);

	}

	@Test
	void zero_can_be_deposited_in_savings() {
		String commandType = "deposit";
		bank.addAccount("Savings", 12345677, 0.6, 0);
		String[] parts = { "12345677", "0" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertTrue(actual);

	}

	@Test
	void cd_cannot_be_deposited_into() {
		String commandType = "deposit";
		bank.addAccount("Cd", 12345677, 0.6, 0);
		String[] parts = { "12345677", "50" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertFalse(actual);

	}

	@Test
	void zero_cannot_be_deposited_into_cd() {
		String commandType = "deposit";
		bank.addAccount("Cd", 12345677, 0.6, 0);
		String[] parts = { "12345677", "0" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertFalse(actual);

	}

	@Test
	void attempt_to_deposit_into_account_that_does_not_exist() {
		String commandType = "deposit";
		String[] parts = { "12345677", "50" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertFalse(actual);

	}

	@Test
	void invalid_account_id() {
		String commandType = "deposit";
		bank.addAccount("checking", 12345677, 0.6, 0);
		String[] parts = { "1234567788", "50" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertFalse(actual);

	}

	@Test
	void non_numeric_deposit() {
		String commandType = "deposit";
		bank.addAccount("Savings", 12345677, 0.6, 0);
		String[] parts = { "12345677", "fvdv" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertFalse(actual);

	}

	@Test
	void deposit_cannot_be_a_decimal() {
		String commandType = "deposit";
		bank.addAccount("Savings", 12345677, 0.6, 0);
		String[] parts = { "12345677", "800.5" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertFalse(actual);

	}

	@Test
	void amount_to_deposit_cannot_be_negative_for_savings() {
		String commandType = "deposit";
		bank.addAccount("Savings", 12345677, 0.6, 0);
		String[] parts = { "12345677", "-100" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertFalse(actual);

	}

	@Test
	void zero_can_be_deposited_in_checking() {
		String commandType = "deposit";
		bank.addAccount("Checking", 12345677, 0.6, 0);
		String[] parts = { "12345677", "0" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertTrue(actual);

	}

	@Test
	void amount_to_deposit_cannot_be_negative_for_checking() {
		String commandType = "deposit";
		bank.addAccount("Checking", 12345677, 0.6, 0);
		String[] parts = { "12345677", "-100" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertFalse(actual);

	}

	@Test
	void amount_to_deposit_cannot_be_greater_than_2500_in_savings() {
		String commandType = "deposit";
		bank.addAccount("Savings", 12345677, 0.6, 0);
		String[] parts = { "12345677", "2900" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertFalse(actual);

	}

	@Test
	void amount_to_deposit_can_be_2500_in_savings() {
		String commandType = "deposit";
		bank.addAccount("Savings", 12345677, 0.6, 0);
		String[] parts = { "12345677", "2500" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertTrue(actual);

	}

	@Test
	void amount_to_deposit_can_be_2499_in_savings() {
		String commandType = "deposit";
		bank.addAccount("Savings", 12345677, 0.6, 0);
		String[] parts = { "12345677", "2499" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertTrue(actual);

	}

	@Test
	void amount_to_deposit_cannot_be_2501_in_savings() {
		String commandType = "deposit";
		bank.addAccount("Savings", 12345677, 0.6, 0);
		String[] parts = { "12345677", "2501" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertFalse(actual);

	}

	@Test
	void amount_to_deposit_cannot_be_greater_than_1000_in_checking() {
		String commandType = "deposit";
		bank.addAccount("Checking", 12345677, 0.6, 0);
		String[] parts = { "12345677", "2000" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertFalse(actual);

	}

	@Test
	void amount_to_deposit_cannot_be_1001_in_checking() {
		String commandType = "deposit";
		bank.addAccount("Checking", 12345677, 0.6, 0);
		String[] parts = { "12345677", "1001" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertFalse(actual);

	}

	@Test
	void amount_to_deposit_can_be_1000_in_checking() {
		String commandType = "deposit";
		bank.addAccount("Checking", 12345677, 0.6, 0);
		String[] parts = { "12345677", "1000" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertTrue(actual);

	}

	@Test
	void amount_to_deposit_can_be_999_in_checking() {
		String commandType = "deposit";
		bank.addAccount("Checking", 12345677, 0.6, 0);
		String[] parts = { "12345677", "999" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertTrue(actual);

	}

	@Test
	void amount_to_deposit_can_be_1_in_checking() {
		String commandType = "deposit";
		bank.addAccount("Checking", 12345677, 0.6, 0);
		String[] parts = { "12345677", "1" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertTrue(actual);

	}

	@Test
	void amount_to_deposit_can_be_1_in_savings() {
		String commandType = "deposit";
		bank.addAccount("Savings", 12345677, 0.6, 0);
		String[] parts = { "12345677", "1" };
		boolean actual = depositValidator.validateCommand(commandType, parts);
		assertTrue(actual);

	}

}
