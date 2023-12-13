package banking;

import java.util.List;

public class MasterControl {
	private final CommandProcessor commandProcessor;
	private final CommandValidator commandValidator;
	private final StoreInvalidCommands storeInvalidCommands;
	private final AccountReport accountReport;

	public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor,
			StoreInvalidCommands storeInvalidCommands, AccountReport accountReport) {
		this.commandProcessor = commandProcessor;
		this.commandValidator = commandValidator;
		this.storeInvalidCommands = storeInvalidCommands;
		this.accountReport = accountReport;
	}

	public List<String> start(List<String> input) {
		for (String command : input) {
			if (commandValidator.validate(command)) {
				commandProcessor.commandParser(command);
				accountReport.generateReport(command);

				//////////
			} else {
				storeInvalidCommands.addInvalidCommand(command);
			}

		}

		List<String> bankReport = accountReport.getCompleteBankReport();
		bankReport.addAll(storeInvalidCommands.getAllInvalidCommands());
		return bankReport;
	}
}
