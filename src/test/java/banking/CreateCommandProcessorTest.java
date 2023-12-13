package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateCommandProcessorTest {

	private DepositCommandProcessor depositCommandProcessor;
	private Bank bank;
	private CreateCommandProcessor createCommandProcessor;
	private WithdrawCommandProcessor withdrawCommandProcessor;
	private TransferCommandProcessor transferCommandProcessor;
	private PassCommandProcessor passCommandProcessor;
	private CommandProcessor commandProcessor;

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
	}

	@Test
	void create_account_with_valid_id_according_to_create_command() {
		commandProcessor.commandParser("create SAVINGS 12345678 9.8");
		assertTrue(bank.accountExistsByAccountID(12345678));
	}

	@Test
	void create_account_with_valid_account_type_checking_according_to_create_command() {
		commandProcessor.commandParser("create CHECKING 12345678 9.8");
		assertEquals("Checking", bank.getAccountTypeByAccountID(12345678));
	}

	@Test
	void create_account_with_valid_account_type_savings_according_to_create_command() {
		commandProcessor.commandParser("create SAVINGS 12345678 9.8");
		assertEquals("Savings", bank.getAccountTypeByAccountID(12345678));
	}

	@Test
	void create_account_with_valid_account_type_cd_according_to_create_command() {
		commandProcessor.commandParser("create CD 12345678 9.8 1200");
		assertEquals("Cd", bank.getAccountTypeByAccountID(12345678));
	}

	@Test
	void create_account_with_valid_account_apr_according_to_create_command() {
		commandProcessor.commandParser("create SAVINGS 12345678 9.5");
		assertEquals(9.5, bank.getAccountApr(12345678));
	}

	@Test
	void create_account_with_cd_deposit_according_to_create_command() {
		commandProcessor.commandParser("create CD 12345678 9.8 1200");
		assertEquals(1200, bank.getBalance(12345678));
	}

}
