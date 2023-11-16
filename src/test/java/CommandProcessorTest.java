import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	private CommandProcessor commandProcessor;
	private Bank bank; // Replace with your actual Bank implementation

	@BeforeEach
	void setUp() {
		bank = new Bank(); // Replace with actual implementation
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	void check_command_parsing() {
		String command = "create checking 12345678 9.6";
		String[] actual = commandProcessor.commandParser(command);
		assertArrayEquals(new String[] { "create", "checking", "12345678", "9.6" }, actual);

	}
}
