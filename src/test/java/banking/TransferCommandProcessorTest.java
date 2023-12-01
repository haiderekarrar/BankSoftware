package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferCommandProcessorTest {
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
	void transfer_200_from_checking_to_savings_checking_should_have_100_savings_should_have_100() {
		bank.addAccount("Checking", 12345678, 9.5, 0);
		bank.depositMoneyFromBank(12345678, 300);
		bank.addAccount("Savings", 12345677, 9.5, 0);
		commandProcessor.commandParser("transfer 12345678 12345677 200");
		assertEquals(200, bank.getBalance(12345677));
		assertEquals(100, bank.getBalance(12345678));
	}

	@Test
	void transfer_200_from_checking_to_savings_when_checking_only_has_100_should_leave_balance_0_and_add_100_to_savings() {
		bank.addAccount("Checking", 12345678, 9.5, 0);
		bank.depositMoneyFromBank(12345678, 100);
		bank.addAccount("Savings", 12345677, 9.5, 0);
		commandProcessor.commandParser("transfer 12345678 12345677 200");
		assertEquals(0, bank.getBalance(12345678));
		assertEquals(100, bank.getBalance(12345677));

	}

	@Test
	void transferring_200_to_an_account_with_an_initial_balance_of_500_should_now_have_700() {
		bank.addAccount("Checking", 12345678, 9.5, 0);
		bank.depositMoneyFromBank(12345678, 200);
		bank.addAccount("Savings", 12345677, 9.5, 0);
		bank.depositMoneyFromBank(12345677, 500);

		commandProcessor.commandParser("transfer 12345678 12345677 200");
		assertEquals(0, bank.getBalance(12345678));
		assertEquals(700, bank.getBalance(12345677));

	}

	@Test
	void transferring_200_from_an_account_with_an_initial_withdrawal_of_500_and_initial_balance_of_1000_should_now_have_300() {
		bank.addAccount("Checking", 12345678, 9.5, 0);
		bank.depositMoneyFromBank(12345678, 1000);
		bank.addAccount("Savings", 12345677, 9.5, 0);
		bank.depositMoneyFromBank(12345677, 500);
		bank.withdrawMoneyFromBank(12345678, 500);
		commandProcessor.commandParser("transfer 12345678 12345677 200");
		assertEquals(300, bank.getBalance(12345678));
		assertEquals(700, bank.getBalance(12345677));

	}

}
