package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositCommandProcessorTest {

	private DepositCommandProcessor depositCommandProcessor;
	private Bank bank;
	private CreateCommandProcessor createCommandProcessor;
	private WithdrawCommandProcessor withdrawCommandProcessor;
	private TransferCommandProcessor transferCommandProcessor;
	private CommandProcessor commandProcessor;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		depositCommandProcessor = new DepositCommandProcessor(bank, depositCommandProcessor, createCommandProcessor,
				withdrawCommandProcessor, transferCommandProcessor);
		createCommandProcessor = new CreateCommandProcessor(bank, depositCommandProcessor, createCommandProcessor,
				withdrawCommandProcessor, transferCommandProcessor);
		withdrawCommandProcessor = new WithdrawCommandProcessor(bank, depositCommandProcessor, createCommandProcessor,
				withdrawCommandProcessor, transferCommandProcessor);
		transferCommandProcessor = new TransferCommandProcessor(bank, depositCommandProcessor, createCommandProcessor,
				withdrawCommandProcessor, transferCommandProcessor);
		commandProcessor = new CommandProcessor(bank, depositCommandProcessor, createCommandProcessor,
				withdrawCommandProcessor, transferCommandProcessor);
	}

	@Test
	void deposit_1800_into_checking_account_with_0_balance() {
		bank.addAccount("Checking", 12345678, 9.5, 0);
		commandProcessor.commandParser("deposit 12345678 1800");
		assertEquals(1800, bank.getBalance(12345678));
	}

	@Test
	void deposit_1800_into_savings_account_with_0_balance() {
		bank.addAccount("Savings", 12345678, 9.5, 0);
		commandProcessor.commandParser("deposit 12345678 1800");
		assertEquals(1800, bank.getBalance(12345678));
	}

	@Test
	void deposit_1800_into_checking_account_with_some_initial_balance() {
		bank.addAccount("Checking", 12345678, 9.5, 0);
		bank.depositMoneyFromBank(12345678, 500);
		commandProcessor.commandParser("deposit 12345678 1800");
		assertEquals(2300, bank.getBalance(12345678));
	}

	@Test
	void deposit_1800_into_savings_account_with_some_initial_balance() {
		bank.addAccount("Savings", 12345678, 9.5, 0);
		bank.depositMoneyFromBank(12345678, 500);
		commandProcessor.commandParser("deposit 12345678 1800");
		assertEquals(2300, bank.getBalance(12345678));
	}
}
