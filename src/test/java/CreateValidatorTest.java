import org.junit.jupiter.api.BeforeEach;

public class CreateValidatorTest {
	private CreateValidator createValidator;
	private Bank bank; // Replace with your actual Bank implementation

	@BeforeEach
	void setUp() {
		bank = new Bank(); // Replace with actual implementation
		createValidator = new CreateValidator(bank);
	}
}
