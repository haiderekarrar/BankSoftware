package banking;

public class CommandProcessor {
	protected Bank bank;
	private DepositCommandProcessor depositCommandProcessor;
	private CreateCommandProcessor createCommandProcessor;
	private WithdrawCommandProcessor withdrawCommandProcessor;
	private TransferCommandProcessor transferCommandProcessor;

	public CommandProcessor(Bank bank, DepositCommandProcessor depositCommandProcessor,
			CreateCommandProcessor createCommandProcessor, WithdrawCommandProcessor withdrawCommandProcessor,
			TransferCommandProcessor transferCommandProcessor) {
		this.bank = bank;
		this.depositCommandProcessor = depositCommandProcessor;
		this.createCommandProcessor = createCommandProcessor;
		this.withdrawCommandProcessor = withdrawCommandProcessor;
		this.transferCommandProcessor = transferCommandProcessor;
	}

	public void commandParser(String command) {
		String[] parts = command.split(" ");
		String commandType = parts[0];
		if (("create".equals(commandType))) {
			createCommandProcessor.commandProcessor(parts);
		} else if (("deposit".equals(commandType))) {
			depositCommandProcessor.commandProcessor(parts);
		} else if (("withdraw".equals(commandType))) {
			withdrawCommandProcessor.commandProcessor(parts);
		} else if (("transfer".equals(commandType))) {
			transferCommandProcessor.commandProcessor(parts);
		}

	}

	protected void commandProcessor(String[] parts) {
		// Default implementation, can be overridden by child classes
	}
}
