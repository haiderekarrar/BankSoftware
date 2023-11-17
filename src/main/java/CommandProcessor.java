public class CommandProcessor {
	protected Bank bank;
	private DepositCommandProcessor depositCommandProcessor;
	private CreateCommandProcessor createCommandProcessor;

	public CommandProcessor(Bank bank, DepositCommandProcessor depositCommandProcessor,
			CreateCommandProcessor createCommandProcessor) {
		this.bank = bank;
		this.depositCommandProcessor = depositCommandProcessor;
		this.createCommandProcessor = createCommandProcessor;
	}

	public void commandParser(String command) {
		String[] parts = command.split(" ");
		String commandType = parts[0];
		if (("create".equals(commandType))) {
			createCommandProcessor.commandProcessor(parts);
		} else if (("deposit".equals(commandType))) {
			depositCommandProcessor.commandProcessor(parts);
		}

	}

	protected void commandProcessor(String[] parts) {
		// Default implementation, can be overridden by child classes
	}
}
