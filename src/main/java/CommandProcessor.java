public class CommandProcessor {
	protected Bank bank;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public String[] commandParser(String command) {
		String[] parts = command.split(" ");
		return parts;
	}

	void commandProcessor(String[] parts) {
	}
}
