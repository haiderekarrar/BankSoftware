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

	@BeforeEach
	void setUp() {
		bank = new Bank();

		depositValidator = new DepositValidator(bank, depositValidator, createValidator, withdrawValidator,
				transferValidator);
		createValidator = new CreateValidator(bank, depositValidator, createValidator, withdrawValidator,
				transferValidator);
		withdrawValidator = new WithdrawValidator(bank, depositValidator, createValidator, withdrawValidator,
				transferValidator);
		transferValidator = new TransferValidator(bank, depositValidator, createValidator, withdrawValidator,
				transferValidator);
		commandValidator = new CommandValidator(bank, depositValidator, createValidator, withdrawValidator,
				transferValidator);
	}

	@Test
	void valid_transfer_command() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transfer_command_is_case_insensitive() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "tRanSfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void typo_in_transfer_command() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "trensfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transfer_is_absent() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void numeric_command() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "4534345 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void cd_cannot_be_transferred_to() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("cd", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void cd_cannot_be_transferred_from() {

		bank.addAccount("cd", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transfer_from_checking_to_checking_is_valid() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transfer_from_checking_to_savings_is_valid() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("Savings", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transfer_from_savings_to_savings_is_valid() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		bank.addAccount("Savings", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transfer_from_savings_to_checking_is_valid() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transfer_is_invalid_if_to_account_does_not_exist() {

		bank.addAccount("Savings", 12345677, 0.6, 0);

		String command = "transfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transfer_is_invalid_if_from_account_does_not_exist() {

		bank.addAccount("Savings", 12345678, 0.6, 0);

		String command = "transfer 12345677 12345678 300";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_400_from_checking_to_checking_is_valid() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 400";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_less_than_400_from_checking_to_checking_is_valid() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 350";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_negative_from_checking_to_checking_is_invalid() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 -12";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_more_than_400_from_checking_to_checking_is_invalid() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 450";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_401_from_checking_to_checking_is_invalid() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 401";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_399_from_checking_to_checking_is_valid() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 399";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_400_from_checking_to_saving_is_valid() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("Savings", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 400";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_less_than_400_from_checking_to_saving_is_valid() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("Savings", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 350";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_negative_from_checking_to_saving_is_invalid() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("Savings", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 -12";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_more_than_400_from_checking_to_savings_is_invalid() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("Savings", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 450";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_401_from_checking_to_savings_is_invalid() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("Savings", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 401";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_399_from_checking_to_savings_is_valid() {

		bank.addAccount("Checking", 12345677, 0.6, 0);
		bank.addAccount("Savings", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 399";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_1000_from_saving_to_saving_is_valid() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		bank.addAccount("Savings", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 1000";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_less_than_1000_from_saving_to_saving_is_valid() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		bank.addAccount("Savings", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 350";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_negative_from_saving_to_saving_is_invalid() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		bank.addAccount("Savings", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 -12";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_more_than_1000_from_saving_to_saving_is_invalid() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		bank.addAccount("Savings", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 1450";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_1001_from_saving_to_saving_is_invalid() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		bank.addAccount("Savings", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 1001";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_999_from_saving_to_saving_is_valid() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		bank.addAccount("Savings", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 999";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	//////

	@Test
	void transferring_1000_from_saving_to_checking_is_valid() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 1000";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_less_than_1000_from_saving_to_checking_is_valid() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 350";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void transferring_negative_from_saving_to_checking_is_invalid() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 -12";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_more_than_1000_from_saving_to_checking_is_invalid() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 1450";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_1001_from_saving_to_checking_is_invalid() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 1001";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void transferring_999_from_saving_to_checking_is_valid() {

		bank.addAccount("Savings", 12345677, 0.6, 0);
		bank.addAccount("Checking", 12345678, 0.7, 0);
		String command = "transfer 12345677 12345678 999";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}
}
