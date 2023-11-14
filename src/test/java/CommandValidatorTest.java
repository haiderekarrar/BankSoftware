import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {

	private CommandValidator commandValidator;
	private Bank bank; // Replace with your actual Bank implementation

	@BeforeEach
	void setUp() {
		bank = new Bank(); // Replace with actual implementation
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void command_with_no_spaces() {
		String command = "CREATEchecking123456785.0extraArg extra";
		assertFalse(commandValidator.validate(command));
	}

	@Test
	void invalidCommand_too_many_arguments() {
		String command = "CREATE checking 12345678 5.0 extraArg extra";
		assertFalse(commandValidator.validate(command));
	}

	@Test
	void invalidCommand_too_few_arguments() {
		String command = "CREATE checking";
		assertFalse(commandValidator.validate(command));
	}

	@Test
	void case_insensitive_create_command() {
		String command = "cReAte checking 12345678 1.2";
		assertTrue(commandValidator.validate(command));
	}

	@Test
	void case_insensitive_deposit_command() {
		String command = "dEpOsit 12345678 100";
		assertTrue(commandValidator.validate(command));
	}

	@Test
	void invalid_command() {
		String command = "hello my name is jeff";
		assertFalse(commandValidator.validate(command));
	}

	@Test
	void too_many_digits_in_accountID() {
		assertFalse(commandValidator.isValidAccountId("1234567891"));
	}

	@Test
	void too_less_digits_in_accountID() {
		assertFalse(commandValidator.isValidAccountId("17891"));
	}

	@Test
	void non_numeric_8_characters_id() {
		assertFalse(commandValidator.isValidAccountId("abcdefgh"));
	}

	@Test
	void valid_account_id_but_with_non_numeric_characters() {
		assertFalse(commandValidator.isValidAccountId("12345678abcde"));

	}

	@Test
	void duplicate_account_id_is_invalid() {
		bank.addAccount("Checking", 12345678, 0.6, 0);
		boolean actual = commandValidator.doesAccountExist("12345678");
		assertTrue(actual);
	}
}
