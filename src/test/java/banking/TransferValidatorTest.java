package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferValidatorTest {

	private WithdrawValidator withdrawValidator;
	private CreateValidator createValidator;
	private CommandValidator commandValidator;
	private DepositValidator depositValidator;
	private Bank bank;
	private TransferValidator transferValidator;
	private PassValidator passValidator;
	private DepositCommandProcessor depositCommandProcessor;
	private CreateCommandProcessor createCommandProcessor;
	private WithdrawCommandProcessor withdrawCommandProcessor;
	private TransferCommandProcessor transferCommandProcessor;
	private PassCommandProcessor passCommandProcessor;
	private CommandProcessor commandProcessor;

	@BeforeEach
	void setUp() {
		bank = new Bank();

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
	void valid_transfer_command() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transfer_command_is_case_insensitive() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "tRanSfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void typo_in_transfer_command() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "trensfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transfer_is_absent() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void numeric_command() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "4534345 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void cd_cannot_be_transferred_to() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("CD", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void cd_cannot_be_transferred_from() {

		bank.addAccount("CD", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transfer_from_checking_to_checking_is_valid() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transfer_from_checking_to_savings_is_valid() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("SAVINGS", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transfer_from_savings_to_savings_is_valid() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.addAccount("SAVINGS", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transfer_from_savings_to_checking_is_valid() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transfer_is_invalid_if_to_account_does_not_exist() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);

		String command = "transfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transfer_is_invalid_if_from_account_does_not_exist() {

		bank.addAccount("SAVINGS", 12345678, 0.6, 0);

		String command = "transfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_400_from_checking_to_checking_is_valid() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 400";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_less_than_400_from_checking_to_checking_is_valid() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 350";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_negative_from_checking_to_checking_is_invalid() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 -12";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_more_than_400_from_checking_to_checking_is_invalid() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 450";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_401_from_checking_to_checking_is_invalid() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 401";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_399_from_checking_to_checking_is_valid() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 399";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_400_from_checking_to_saving_is_valid() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("SAVINGS", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 400";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_less_than_400_from_checking_to_saving_is_valid() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("SAVINGS", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 350";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_negative_from_checking_to_saving_is_invalid() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("SAVINGS", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 -12";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_more_than_400_from_checking_to_savings_is_invalid() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("SAVINGS", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 450";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_401_from_checking_to_savings_is_invalid() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("SAVINGS", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 401";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_399_from_checking_to_savings_is_valid() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		bank.addAccount("SAVINGS", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 399";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_1000_from_saving_to_saving_is_valid() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.addAccount("SAVINGS", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 1000";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_less_than_1000_from_saving_to_saving_is_valid() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.addAccount("SAVINGS", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 350";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_negative_from_saving_to_saving_is_invalid() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.addAccount("SAVINGS", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 -12";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_more_than_1000_from_saving_to_saving_is_invalid() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.addAccount("SAVINGS", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 1450";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_1001_from_saving_to_saving_is_invalid() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.addAccount("SAVINGS", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 1001";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_999_from_saving_to_saving_is_valid() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.addAccount("SAVINGS", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 999";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	//////

	@Test
	void transferring_1000_from_saving_to_checking_is_valid() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 1000";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_less_than_1000_from_saving_to_checking_is_valid() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 350";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_negative_from_saving_to_checking_is_invalid() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 -12";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_more_than_1000_from_saving_to_checking_is_invalid() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 1450";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_1001_from_saving_to_checking_is_invalid() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 1001";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_999_from_saving_to_checking_is_valid() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.addAccount("CHECKING", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 999";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transfer_from_savings_twice_in_a_month_has_is_invalid() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.addAccount("SAVINGS", 12345678, 0.7, 0);
		bank.depositMoneyFromBank(12345677, 500);
		String command = "transfer 12345677 12345678 300";
		commandValidator.validate(command);
		commandProcessor.commandParser(command);
		boolean actual = commandValidator.validate(command);

		assertFalse(actual);

	}
}
