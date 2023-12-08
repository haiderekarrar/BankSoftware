package banking;

public class TransferValidator extends CommandValidator {
	public TransferValidator(Bank bank, DepositValidator depositValidator, CreateValidator createValidator,
			WithdrawValidator withdrawValidator, TransferValidator transferValidator, PassValidator passValidator) {
		super(bank, depositValidator, createValidator, withdrawValidator, transferValidator, passValidator);
	}

	public boolean validateCommand(String[] parts) {

		if (parts.length != 4) {
			return false;
		}
		String transferFromAccountID = parts[1];
		String transferToAccountID = parts[2];
		if (!isValidAccountId(transferFromAccountID)) {
			return false;
		}

		if (!isValidAccountId(transferToAccountID)) {
			return false;
		}

//accounts need to exist in the first place in order to transfer
		if (!(doesAccountExist(transferFromAccountID))) {
			return false;
		}

		if (!(doesAccountExist(transferToAccountID))) {
			return false;
		}
		String transferFromAccountType = accountTypeByAccountId(transferFromAccountID).toUpperCase();
		String transferToAccountType = accountTypeByAccountId(transferToAccountID).toUpperCase();

		try {
			double amountToTransfer = Double.parseDouble(parts[3]);
			if (amountToTransfer < 0) {
				return false;

			} else if ((transferFromAccountType.equals("CD") || (transferToAccountType.equals("CD")))) {
				return false;

				// implimenting checking to checking
				// i just need to check for withdrawal rules because withdrawing amount is less
				// than the amount allowed to be deposited for
				// every account

			} else if ((transferFromAccountType.equals("CHECKING")) && (transferToAccountType.equals("CHECKING"))) {
				if (!(withdrawal_rule_for_checking(amountToTransfer))) {
					return false;
				}

				// implimenting checking to savings
			} else if ((transferFromAccountType.equals("CHECKING")) && (transferToAccountType.equals("SAVINGS"))) {
				if (!(withdrawal_rule_for_checking(amountToTransfer))) {
					return false;
				}

				// implimenting savings to savings
			} else if ((transferFromAccountType.equals("SAVINGS")) && (transferToAccountType.equals("SAVINGS"))) {
				if (!(withdrawal_rule_for_savings(Integer.parseInt(transferFromAccountID), amountToTransfer))) {
					return false;
				}

				// implimenting savings to checking

			} else if ((transferFromAccountType.equals("SAVINGS")) && (transferToAccountType.equals("CHECKING"))) {
				if (!(withdrawal_rule_for_savings(Integer.parseInt(transferFromAccountID), amountToTransfer))) {
					return false;
				}

			}
		} catch (NumberFormatException e) {
			// Parsing failed, return false
			return false;
		}

		return true;
	}
}
