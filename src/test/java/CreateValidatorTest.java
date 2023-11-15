import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateValidatorTest {
	private CreateValidator createValidator;
	private Bank bank; // Replace with your actual Bank implementation

	@BeforeEach
	void setUp() {
		bank = new Bank(); // Replace with actual implementation
		createValidator = new CreateValidator(bank);
	}

	@Test
	void accountID_is_8_digits() {
		String commandType = "Create";
		String[] parts = { "Savings", "12345677", "10" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertTrue(actual);
	}

	@Test
	void accountID_is_more_than_digits() {
		String commandType = "Create";
		String[] parts = { "Savings", "1234567788", "10" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertFalse(actual);
	}

	@Test
	void command_is_not_create() {
		String commandType = "deposit";
		String[] parts = { "Savings", "12345677", "10" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertFalse(actual);

	}

	@Test
	void spelling_error_in_create() {
		String commandType = "creete";
		String[] parts = { "Savings", "12345677", "10" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertFalse(actual);

	}

	@Test
	void case_insensitive_create() {
		String commandType = "crEate";
		String[] parts = { "Savings", "12345677", "10" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertTrue(actual);

	}

	@Test
	void create_absent() {
		String commandType = "";
		String[] parts = { "Savings", "12345677", "10" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertFalse(actual);

	}

	@Test
	void valid_checking_account_check() {
		String commandType = "create";
		String[] parts = { "Checking", "12345677", "10" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertTrue(actual);
	}

	@Test
	void valid_savings_account_check() {
		String commandType = "create";
		String[] parts = { "savings", "12345677", "10" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertTrue(actual);
	}

	@Test
	void more_than_required_arguments_for_savings() {
		String commandType = "create";
		String[] parts = { "Savings", "12345677", "10", "5" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertFalse(actual);
	}

	@Test
	void more_than_required_arguments_for_checking() {
		String commandType = "create";
		String[] parts = { "Checking", "12345677", "10", "5" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertFalse(actual);
	}

	@Test
	void typo_in_savings() {
		String commandType = "create";
		String[] parts = { "saving", "12345677", "10" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertFalse(actual);
	}

	@Test
	void typo_in_checking() {
		String commandType = "create";
		String[] parts = { "checkingss", "12345677", "10" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertFalse(actual);
	}

	@Test
	void checking_is_case_insenstiive() {
		String commandType = "create";
		String[] parts = { "chEcKing", "12345677", "10" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertTrue(actual);
	}

	@Test
	void savings_is_case_insenstiive() {
		String commandType = "create";
		String[] parts = { "sAvingS", "12345677", "10" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertTrue(actual);
	}

	@Test
	void account_type_absent() {
		String commandType = "create";
		String[] parts = { "12345677", "10" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertFalse(actual);
	}

	@Test
	void valid_cd_account_check() {
		String commandType = "create";
		String[] parts = { "cd", "12345677", "9.5", "1100" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertTrue(actual);
	}

	@Test
	void cd_is_case_insensitive_check() {
		String commandType = "create";
		String[] parts = { "cD", "12345677", "9.5", "1100" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertTrue(actual);
	}

	@Test
	void cd_upper_edge_case_check() {
		String commandType = "create";
		String[] parts = { "cD", "12345677", "9.5", "10001" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertFalse(actual);
	}

	@Test
	void cd_lower_edge_case_check() {
		String commandType = "create";
		String[] parts = { "cD", "12345677", "9.5", "999" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertFalse(actual);
	}

	@Test
	void cd_1000_deposit_check() {
		String commandType = "create";
		String[] parts = { "cD", "12345677", "9.5", "1000" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertTrue(actual);
	}

	@Test
	void cd_10000_deposit_check() {
		String commandType = "create";
		String[] parts = { "cD", "12345677", "9.5", "10000" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertTrue(actual);
	}

	@Test
	void cd_has_too_many_arguments() {
		String commandType = "create";
		String[] parts = { "cD", "12345677", "9.5", "1000", "balabla" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertFalse(actual);
	}

	@Test
	void cd_has_too_few_arguments() {
		String commandType = "create";
		String[] parts = { "cD", "12345677", "balabla" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertFalse(actual);
	}

	@Test
	void cd_amount_is_not_an_int() {
		String commandType = "create";
		String[] parts = { "cD", "12345677", "10", "100.1" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertFalse(actual);
	}

	@Test
	void cd_amount_is_non_numeric() {
		String commandType = "create";
		String[] parts = { "cD", "12345677", "10", "absbdjwd" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertFalse(actual);
	}

	@Test
	void account_apr_is_non_numeric() {
		String commandType = "create";
		String[] parts = { "cD", "12345677", "ansmn", "100" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertFalse(actual);
	}

	@Test
	void account_apr_upper_edge_case() {
		String commandType = "create";
		String[] parts = { "cD", "12345677", "10.1", "100" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertFalse(actual);
	}

	@Test
	void cd_amount_is_negative() {
		String commandType = "create";
		String[] parts = { "cD", "12345677", "10", "-1" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertFalse(actual);
	}

	@Test
	void command_is_numeric() {
		String commandType = "65351213";
		String[] parts = { "cD", "12345677", "10", "-1" };
		boolean actual = createValidator.validateCommand(commandType, parts);
		assertFalse(actual);
	}

}
