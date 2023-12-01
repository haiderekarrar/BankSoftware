package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateValidatorTest {

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
	void accountID_is_8_digits_is_valid() {
		String command = "create Savings 12345677 2.2";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);
	}

	@Test
	void accountID_is_more_than_digits() {
		String command = "create Savings 123456775454 2.2";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void command_is_not_create() {
		String command = "deposit Savings 12345677 2.2";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void spelling_error_in_create() {
		String command = "creeeate Savings 12345677 2.2";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void case_insensitive_create() {
		String command = "crEAte Savings 12345677 2.2";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);

	}

	@Test
	void create_absent() {
		String command = "Savings 12345677 2.2";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);

	}

	@Test
	void valid_checking_account_check() {
		String command = "create Checking 12345677 2.2";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);
	}

	@Test
	void valid_savings_account_check() {
		String command = "create Savings 12345677 2.2";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);
	}

	@Test
	void more_than_required_arguments_for_savings() {
		String command = "create Savings 12345677 2.2 dsaaf";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void more_than_required_arguments_for_checking() {
		String command = "create Checking 12345677 2.2 qwdaqw";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void typo_in_savings() {
		String command = "create Saving 12345677 2.2";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void typo_in_checking() {
		String command = "create chekin 12345677 2.2";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void checking_is_case_insenstiive() {
		String command = "create cHecKing 12345677 2.2";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);
	}

	@Test
	void savings_is_case_insenstiive() {
		String command = "create SaViNgs 12345677 2.2";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);
	}

	@Test
	void account_type_absent() {
		String command = "create 12345677 2.2";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void valid_cd_account_check() {
		String command = "create cd 12345677 2.2 1200";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);
	}

	@Test
	void cd_is_case_insensitive_check() {
		String command = "create cD 12345677 2.2 1200";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);
	}

	@Test
	void cd_upper_edge_case_check() {
		String command = "create cd 12345677 2.2 10001";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void cd_lower_edge_case_check() {
		String command = "create cd 12345677 2.2 999";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void cd_1000_deposit_check() {
		String command = "create cd 12345677 2.2 1000";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);
	}

	@Test
	void cd_10000_deposit_check() {
		String command = "create cd 12345677 2.2 10000";
		boolean actual = commandValidator.validate(command);
		assertTrue(actual);
	}

	@Test
	void cd_has_too_many_arguments() {
		String command = "create cd 12345677 2.2 1000 dscnakascna";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void cd_has_too_few_arguments() {
		String command = "create cd 1000";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void cd_amount_is_not_numeric() {
		String command = "create cd 12345677 2.2 hahahah";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void account_apr_is_non_numeric() {
		String command = "create cd 12345677 eadasad 1000";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void account_apr_upper_edge_case() {
		String command = "create cd 12345677 10.1 1000";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void cd_amount_is_negative() {
		String command = "create cd 12345677 2.2 -10";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

	@Test
	void command_is_numeric() {
		String command = "45645343 cd 12345677 2.2 1000";
		boolean actual = commandValidator.validate(command);
		assertFalse(actual);
	}

}
