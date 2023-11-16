import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateCommandProcessorTest {
	private CreateCommandProcessor createCommandProcessor;
	private Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		createCommandProcessor = new CreateCommandProcessor(bank);
	}

	@Test
	void create_account_with_valid_id_according_to_create_command() {
		createCommandProcessor.commandProcessor(new String[] { "create", "Checking", "12345678", "9.5" });
		assertTrue(bank.accountExistsByAccountID(12345678));
	}

	@Test
	void create_account_with_valid_account_type_checking_according_to_create_command() {
		createCommandProcessor.commandProcessor(new String[] { "create", "Checking", "12345678", "9.5" });
		assertEquals(bank.getAccountTypeByAccountID(12345678), "Checking");
	}

	@Test
	void create_account_with_valid_account_type_savings_according_to_create_command() {
		createCommandProcessor.commandProcessor(new String[] { "create", "Savings", "12345678", "9.5" });
		assertEquals(bank.getAccountTypeByAccountID(12345678), "Savings");
	}

	@Test
	void create_account_with_valid_account_type_cd_according_to_create_command() {
		createCommandProcessor
				.commandProcessor(new String[] { "create", "Certificate of Deposit", "12345678", "9.5", "1200" });
		assertEquals(bank.getAccountTypeByAccountID(12345678), "Certificate of Deposit");
	}

	@Test
	void create_account_with_valid_account_apr_according_to_create_command() {
		createCommandProcessor.commandProcessor(new String[] { "create", "Checking", "12345678", "9.5" });
		assertEquals(bank.getAccountApr(12345678), 9.5);
	}

	@Test
	void create_account_with_cd_deposit_according_to_create_command() {
		createCommandProcessor
				.commandProcessor(new String[] { "create", "Certificate of Deposit", "12345678", "9.5", "1200" });
		assertEquals(bank.getBalance(12345678), 1200);
	}

}
