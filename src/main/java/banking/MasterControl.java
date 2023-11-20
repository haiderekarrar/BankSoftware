package banking;

import java.util.List;

public class MasterControl {
	private CommandProcessor commandProcessor;
	private CommandValidator commandValidator;
	private StoreInvalidCommands storeInvalidCommands;

	public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor,
			StoreInvalidCommands storeInvalidCommands) {
		this.commandProcessor = commandProcessor;
		this.commandValidator = commandValidator;
		this.storeInvalidCommands = storeInvalidCommands;
	}

	public List<String> start(List<String> input) {
		for (String command : input) {
			if (commandValidator.validate(command)) {
				commandProcessor.commandParser(command);
			} else {
				storeInvalidCommands.addInvalidCommand(command);
			}

		}
		return storeInvalidCommands.getAllInvalidCommands();

	}
}
