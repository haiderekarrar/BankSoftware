package banking;

public class PassValidator extends CommandValidator {
	public PassValidator(Bank bank, DepositValidator depositValidator, CreateValidator createValidator,
			WithdrawValidator withdrawValidator, TransferValidator transferValidator, PassValidator passValidator) {
		super(bank, depositValidator, createValidator, withdrawValidator, transferValidator, passValidator);
	}

	public boolean validateCommand(String[] parts) {

		if (parts.length != 2) {
			return false;
		}

		try {
			int months = Integer.parseInt(parts[1]);
			if (months > 60 || months < 1) {
				return false;
			}
		} catch (NumberFormatException e) {
			// Parsing failed, return false
			return false;
		}

		return true;
	}

}
