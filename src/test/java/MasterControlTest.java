import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {

	MasterControl masterControl;
	List<String> input;
	private CreateCommandProcessor createCommandProcessor;
	private Bank bank;
	private DepositCommandProcessor depositCommandProcessor;
	private CommandProcessor commandProcessor;
	private DepositValidator depositValidator;
	private CreateValidator createValidator;
	private CommandValidator commandValidator;

	@BeforeEach
	void setUp() {
		input = new ArrayList<>();
		bank = new Bank();
		depositCommandProcessor = new DepositCommandProcessor(bank, depositCommandProcessor, createCommandProcessor);
		createCommandProcessor = new CreateCommandProcessor(bank, depositCommandProcessor, createCommandProcessor);
		commandProcessor = new CommandProcessor(bank, depositCommandProcessor, createCommandProcessor);
		depositValidator = new DepositValidator(bank, depositValidator, createValidator);
		createValidator = new CreateValidator(bank, depositValidator, createValidator);
		commandValidator = new CommandValidator(bank, depositValidator, createValidator);

		masterControl = new MasterControl(new CommandValidator(bank, depositValidator, createValidator),
				new CommandProcessor(bank, depositCommandProcessor, createCommandProcessor),
				new StoreInvalidCommands());
	}

	private void assertSingleCommand(String command, List<String> actual) {
		assertEquals(1, actual.size());
		assertEquals(command, actual.get(0));
	}

	@Test
	void typo_in_create_command_is_invalid() {
		input.add("creat checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("creat checking 12345678 1.0", actual);

	}

	@Test
	void typo_in_deposit_command_is_invalid() {

		input.add("depositt 12345678 100");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("depositt 12345678 100", actual);

	}

	@Test
	void two_typo_commands_both_invalid() {
		input.add("creat checking 12345678 1.0");
		input.add("depositt 12345678 100");

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0));
		assertEquals("depositt 12345678 100", actual.get(1));

	}

	@Test
	void invalid_to_create_accounts_with_the_same_id() {
		input.add("create Checking 12345678 1.0");
		input.add("create Checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("create Checking 12345678 1.0", actual);

	}
}
