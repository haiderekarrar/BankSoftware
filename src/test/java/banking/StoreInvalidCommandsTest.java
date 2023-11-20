package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StoreInvalidCommandsTest {
	private StoreInvalidCommands invalidCommand;

	@BeforeEach
	void setUp() {
		invalidCommand = new StoreInvalidCommands();
	}

	@Test
	void test_adding_and_getting_invalid_commands() {
		invalidCommand.addInvalidCommand("invalid command1");
		invalidCommand.addInvalidCommand("invalid command2");

		assertEquals(2, invalidCommand.getAllInvalidCommands().size());
		assertTrue(invalidCommand.getAllInvalidCommands().contains("invalid command1"));
		assertTrue(invalidCommand.getAllInvalidCommands().contains("invalid command2"));
	}

	@Test
	void add_and_get_multiple_invalid_commands() {
		StoreInvalidCommands invalidCommand = new StoreInvalidCommands();

		invalidCommand.addInvalidCommand("invalidCommand1");
		invalidCommand.addInvalidCommand("invalidCommand2");
		invalidCommand.addInvalidCommand("invalidCommand3");

		assertEquals(3, invalidCommand.getAllInvalidCommands().size());
		assertTrue(invalidCommand.getAllInvalidCommands().contains("invalidCommand1"));
		assertTrue(invalidCommand.getAllInvalidCommands().contains("invalidCommand2"));
		assertTrue(invalidCommand.getAllInvalidCommands().contains("invalidCommand3"));
	}

	@Test
	void no_invalid_commands() {
		StoreInvalidCommands invalidCommand = new StoreInvalidCommands();

		assertEquals(0, invalidCommand.getAllInvalidCommands().size());
	}
}
