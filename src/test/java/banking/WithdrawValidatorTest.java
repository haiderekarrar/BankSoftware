package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawValidatorTest {
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
	void valid_withdraw_command() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		String command = "withdraw 12345677 300";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void withdraw_command_is_case_insensitive() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		String command = "wIthDraW 12345677 300";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void typo_in_withdraw() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		String command = "withdrew 12345677 800";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void withdraw_is_absent() {
		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		String command = "12345677 800";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void numeric_command() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		String command = "withdra12545w 12345677 800";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void zero_can_be_withdrawn_from_savings() {
		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		String command = "withdraw 12345677 0";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void zero_can_be_withdrawn_from_checking() {
		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		String command = "withdraw 12345677 0";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void attempt_to_withdraw_from_account_that_does_not_exist() {
		String command = "withdraw 12345677 300";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void invalid_account_id() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		String command = "withdraw 12345677888 800";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void non_numeric_withdraw() {
		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		String command = "withdraw 12345677 asdasf";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void withdraw_can_be_a_decimal() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		String command = "withdraw 12345677 800.2";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void amount_to_withdraw_cannot_be_negative_for_savings() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		String command = "withdraw 12345677 -100";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void cannot_withdraw_from_savings_twice_in_month() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.depositMoneyFromBank(12345677, 300);
		String command = "withdraw 12345677 100";
		commandValidator.validate(command);
		commandProcessor.commandParser(command);
		commandValidator.validate(command);
		assertFalse(commandValidator.validate(command));

	}

	@Test
	void amount_to_withdraw_cannot_be_negative_for_checking() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		String command = "withdraw 12345677 -100";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void cannot_withdraw_from_cd() {

		bank.addAccount("CD", 12345677, 0.6, 1000);
		bank.depositMoneyFromBank(12345677, 300);
		boolean actual = commandValidator.validate("withdraw 12345677 100");

		assertFalse(actual);

	}

	@Test
	void can_withdraw_from_cd_if_12_months_pass() {

		bank.addAccount("CD", 12345677, 0.6, 1000);
		bank.depositMoneyFromBank(12345677, 300);
		commandProcessor.commandParser("pass 12");
		boolean actual = commandValidator.validate("withdraw 12345677 1500");

		assertTrue(actual);

	}

	@Test
	void can_withdraw_from_cd_if_12_months_pass_in_two_seperate_pass_commands() {

		bank.addAccount("CD", 12345677, 0.6, 1000);
		bank.depositMoneyFromBank(12345677, 300);
		commandProcessor.commandParser("pass 3");
		commandProcessor.commandParser("pass 9");

		boolean actual = commandValidator.validate("withdraw 12345677 1500");

		assertTrue(actual);

	}

	@Test
	void still_works_if_months_greater_than_12_in_one_command() {

		bank.addAccount("CD", 12345677, 0.6, 1000);
		bank.depositMoneyFromBank(12345677, 300);
		commandProcessor.commandParser("pass 20");

		boolean actual = commandValidator.validate("withdraw 12345677 1500");

		assertTrue(actual);

	}

	@Test
	void still_works_if_months_greater_than_12_in_multiple_commands() {

		bank.addAccount("CD", 12345677, 0.6, 1000);
		bank.depositMoneyFromBank(12345677, 300);
		commandProcessor.commandParser("pass 11");
		commandProcessor.commandParser("pass 11");

		boolean actual = commandValidator.validate("withdraw 12345677 1500");

		assertTrue(actual);

	}

	@Test
	void amount_to_withdraw_cannot_be_greater_than_1000_in_savings() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		String command = "withdraw 12345677 3000";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void amount_to_withdraw_can_be_1000_in_savings() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		String command = "withdraw 12345677 1000";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void amount_to_withdraw_can_be_999_in_savings() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		bank.depositMoneyFromBank(12345677, 1200);
		commandProcessor.commandParser("pass 12");
		String command = "withdraw 12345677 999";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void amount_to_withdraw_cannot_be_1001_in_savings() {
		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		String command = "withdraw 12345677 1001";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void amount_to_withdraw_cannot_be_greater_than_400_in_checking() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		String command = "withdraw 12345677 1500";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void amount_to_withdraw_cannot_be_401_in_checking() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		String command = "withdraw 12345677 401";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void amount_to_withdraw_can_be_400_in_checking() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		String command = "withdraw 12345677 400";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void amount_to_withdraw_can_be_399_in_checking() {

		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		String command = "withdraw 12345677 399";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void amount_to_withdraw_can_be_1_in_checking() {
		bank.addAccount("CHECKING", 12345677, 0.6, 0);
		String command = "withdraw 12345677 1";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void amount_to_withdraw_can_be_1_in_savings() {

		bank.addAccount("SAVINGS", 12345677, 0.6, 0);
		String command = "withdraw 12345677 1";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

}