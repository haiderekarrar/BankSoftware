package banking;

public class CommandProcessor {
	private final DepositCommandProcessor depositCommandProcessor;
	private final CreateCommandProcessor createCommandProcessor;
	private final WithdrawCommandProcessor withdrawCommandProcessor;
	private final TransferCommandProcessor transferCommandProcessor;
	private final PassCommandProcessor passCommandProcessor;
	protected Bank bank;

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
		switch (commandType) {
		case "CREATE":
			createCommandProcessor.commandProcessor(parts);
			break;
		case "DEPOSIT":
			depositCommandProcessor.commandProcessor(parts);
			break;
		case "WITHDRAW":
			withdrawCommandProcessor.commandProcessor(parts);
			break;
		case "TRANSFER":
			transferCommandProcessor.commandProcessor(parts);
			break;
		case "PASS":
			passCommandProcessor.commandProcessor(parts);
			break;
		}
	}

	protected void commandProcessor(String[] parts) {
		// Default implementation, can be overridden by child classes
	}

}
