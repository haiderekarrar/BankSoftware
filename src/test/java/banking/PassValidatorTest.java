package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassValidatorTest {

	private WithdrawValidator withdrawValidator;
	private CreateValidator createValidator;
	private CommandValidator commandValidator;
	private DepositValidator depositValidator;
	private Bank bank;
	private TransferValidator transferValidator;
	private PassValidator passValidator;

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
	}

	@Test
	void valid_pass_command() {
		String command = "pass 10";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);
	}

	@Test
	void valid_pAsS_command() {
		String command = "pAsS 10";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);
	}

	@Test
	void zero_cannot_be_passed() {
		String command = "pass 0";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void negative_cannot_be_passed() {
		String command = "pass -1";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void decimal_cannot_be_passed() {
		String command = "pass 3.9";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void non_numerical_cannot_be_passed() {
		String command = "pass asda";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void oen_can_be_passed() {
		String command = "pass 1";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);
	}

	@Test
	void sixty_can_be_passed() {
		String command = "pass 60";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);
	}

	@Test
	void fifty_nine_can_be_passed() {
		String command = "pass 59";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);
	}

	@Test
	void sixty_one_cannot_be_passed() {
		String command = "pass 61";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void typo_in_pass() {
		String command = "paas 52";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}
}