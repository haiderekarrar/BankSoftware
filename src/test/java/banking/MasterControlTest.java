package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {

	MasterControl masterControl;
	List<String> input;
	private Bank bank;
	private CreateCommandProcessor createCommandProcessor;
	private DepositCommandProcessor depositCommandProcessor;
	private CommandProcessor commandProcessor;
	private WithdrawCommandProcessor withdrawCommandProcessor;
	private TransferCommandProcessor transferCommandProcessor;
	private PassCommandProcessor passCommandProcessor;
	private DepositValidator depositValidator;
	private CreateValidator createValidator;
	private CommandValidator commandValidator;
	private WithdrawValidator withdrawValidator;
	private TransferValidator transferValidator;
	private PassValidator passValidator;

	@BeforeEach
	void setUp() {
		input = new ArrayList<>();
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

		masterControl = new MasterControl(
				new CommandValidator(bank, depositValidator, createValidator, withdrawValidator, transferValidator,
						passValidator),
				new CommandProcessor(bank, depositCommandProcessor, createCommandProcessor, withdrawCommandProcessor,
						transferCommandProcessor, passCommandProcessor),
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
//Validation Tests

	@Test
	void typo_in_deposit_command_is_invalid() {

		input.add("depositt 12345678 100");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("depositt 12345678 100", actual);

	}

	@Test
	void typo_in_withdraw_command_is_invalid() {

		input.add("weethdraww 12345678 100");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("weethdraww 12345678 100", actual);

	}

	@Test
	void typo_in_transfer_command_is_invalid() {

		input.add("trensferr 12345678 12345677 100");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("trensferr 12345678 12345677 100", actual);

	}

	@Test
	void four_typo_commands_all_invalid() {
		input.add("creat checking 12345678 1.0");
		input.add("depositt 12345678 100");
		input.add("trensferr 12345678 12345677 100");
		input.add("weethdraww 12345678 100");

		List<String> actual = masterControl.start(input);
		assertEquals(4, actual.size());
		assertEquals("creat checking 12345678 1.0", actual.get(0));
		assertEquals("depositt 12345678 100", actual.get(1));
		assertEquals("trensferr 12345678 12345677 100", actual.get(2));
		assertEquals("weethdraww 12345678 100", actual.get(3));

	}

	@Test
	void invalid_to_create_accounts_with_the_same_id() {
		input.add("create Checking 12345678 1.0");
		input.add("create Checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("create Checking 12345678 1.0", actual);

	}

	@Test
	void invalid_to_create_accounts_without_id() {
		input.add("create Checking 1.0");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("create Checking 1.0", actual);

	}

	@Test
	void invalid_to_create_accounts_without_apr() {
		input.add("create Savings 12345678");
		List<String> actual = masterControl.start(input);
		assertSingleCommand("create Savings 12345678", actual);

	}

	// Processing Tests
	@Test
	void valid_create_command_is_implemented() {
		input.add("crEaTe SaVinGs 12345678 1.2");
		masterControl.start(input);
		String accountType = commandProcessor.bank.getAccountTypeByAccountID(12345678);
		assertEquals("SAVINGS", accountType);

	}

}
