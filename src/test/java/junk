import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {

	Bank bank;
	CommandValidator commandValidator;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void duplicate_account_id_is_invalid() {
		String command = "Create savings 12345678 0.6";
		bank.addAccount("Checking", 12345678, 0.6, 0);
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void apr_is_negative() {
		String command = "Create Checking 12345678 -1";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void apr_is_greater_than_10() {
		String command = "Create Checking 12345678 10.1";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void apr_is_ten() {
		String command = "Create Checking 12345678 10";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);
	}

	@Test
	void accountID_is_8_digits() {
		String command = "Create Checking 01234567 10";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);
	}

	@Test
	void accountID_is_more_than_8() {
		String command = "Create Checking 1234567788 10";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void accountID_is_less_than_8() {
		String command = "Create Checking 123488 10";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void create_is_case_insensitive() {
		String command = "CrEaTE checking 12345545 10";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);
	}

	@Test
	void create_is_absent() {
		String command = "checking 12345545 10";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void accountType_is_absent() {
		String command = "Create 12345545 10";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void accountID_is_absent() {
		String command = "Create savings 10";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void accountApr_is_absent() {
		String command = "Create 12345545 10";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void whole_command_is_absent() {
		String command = "";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void non_numeric_value_in_accountID() {
		String command = "Create checking 12345abc 10";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void typo_in_create() {
		String command = "Creete checking 12345678 10";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

}
