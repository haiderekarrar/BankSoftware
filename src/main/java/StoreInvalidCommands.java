import java.util.ArrayList;
import java.util.List;

public class StoreInvalidCommands {
	private List<String> invalidCommands = new ArrayList<>();

	public void addInvalidCommand(String command) {
		invalidCommands.add(command);
	}

	public List<String> getAllInvalidCommands() {
		return new ArrayList<>(invalidCommands);
	}
}
