package banking;

public class CommandProcessor {
	protected Bank bank;
	private DepositCommandProcessor depositCommandProcessor;
	private CreateCommandProcessor createCommandProcessor;
	private WithdrawCommandProcessor withdrawCommandProcessor;
	private TransferCommandProcessor transferCommandProcessor;
	private PassCommandProcessor passCommandProcessor;

	public CommandProcessor(Bank bank, DepositCommandProcessor depositCommandProcessor,
			CreateCommandProcessor createCommandProcessor, WithdrawCommandProcessor withdrawCommandProcessor,
			TransferCommandProcessor transferCommandProcessor, PassCommandProcessor passCommandProcessor) {
		this.bank = bank;
		this.depositCommandProcessor = depositCommandProcessor;
		this.createCommandProcessor = createCommandProcessor;
		this.withdrawCommandProcessor = withdrawCommandProcessor;
		this.transferCommandProcessor = transferCommandProcessor;
		this.passCommandProcessor = passCommandProcessor;
	}

	public void commandParser(String command) {
		String[] parts = command.split(" ");
		String commandType = parts[0].toUpperCase();
		if (("CREATE".equals(commandType))) {
			createCommandProcessor.commandProcessor(parts);
		} else if (("DEPOSIT".equals(commandType))) {
			depositCommandProcessor.commandProcessor(parts);
		} else if (("WITHDRAW".equals(commandType))) {
			withdrawCommandProcessor.commandProcessor(parts);
		} else if (("TRANSFER".equals(commandType))) {
			transferCommandProcessor.commandProcessor(parts);
		} else if (("PASS".equals(commandType))) {
			passCommandProcessor.commandProcessor(parts);
		}
	}

	protected void commandProcessor(String[] parts) {
		// Default implementation, can be overridden by child classes
	}
}
