package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountReportTest {
	private Bank bank;

	private AccountReport accountReport = new AccountReport(null);

	@BeforeEach
	void setUp() {
		bank = new Bank();
		accountReport = new AccountReport(bank);
	}

	@Test
	void valid_account_statuses_given() {

		bank.addAccount("CHECKING", 12345678, 2.2, 0);
		bank.depositMoneyFromBank(12345678, 1000);

		bank.addAccount("CHECKING", 12345677, 2.2, 0);
		bank.depositMoneyFromBank(12345677, 1000);
		bank.withdrawMoneyFromBank(12345677, 100.111);

		bank.addAccount("CHECKING", 12345679, 2.2, 0);
		bank.depositMoneyFromBank(12345679, 1000);
		bank.withdrawMoneyFromBank(12345679, 200);

		bank.addAccount("CD", 12121212, 2.1, 1500);

		accountReport.generateReport("CHECKING 12345678 1000.00 2.20");

		assertEquals("Checking 12345678 1000.00 2.20", bank.getAccountReportbyAccountID(12345678));
		assertEquals("Checking 12345677 899.89 2.20", bank.getAccountReportbyAccountID(12345677));
		assertEquals("Checking 12345679 800.00 2.20", bank.getAccountReportbyAccountID(12345679));
		assertEquals("Cd 12121212 1500.00 2.10", bank.getAccountReportbyAccountID(12121212));

	}

	@Test
	void valid_account_statuses_dipalyed_with_respective_valid_commands() {

		bank.addAccount("CHECKING", 12345678, 2.2, 0);
		bank.depositMoneyFromBank(12345678, 1000);

		bank.addAccount("CHECKING", 12345677, 2.2, 0);
		bank.depositMoneyFromBank(12345677, 1000);
		bank.withdrawMoneyFromBank(12345677, 100.111);

		bank.addAccount("CD", 12121212, 2.1, 1500);

		accountReport.generateReport("create CHECKING 12345678 1000.00 2.20");
		accountReport.generateReport("deposit 12345678 1000");
		accountReport.generateReport("create checking 12345677 2.2");
		accountReport.generateReport("transfer 12345678 12345677 200");

		accountReport.generateReport("withdraw 12345677 100.111");

		accountReport.generateReport("deposit 12345677 1000");

		assertEquals("Checking 12345678 1000.00 2.20", bank.getAccountReportbyAccountID(12345678));

		assertEquals("deposit 12345678 1000", bank.getAccountTransactionHistorybyAccountID(12345678).get(0));
		assertEquals("transfer 12345678 12345677 200", bank.getAccountTransactionHistorybyAccountID(12345678).get(1));

		assertEquals("Checking 12345677 899.89 2.20", bank.getAccountReportbyAccountID(12345677));

		assertEquals("transfer 12345678 12345677 200", bank.getAccountTransactionHistorybyAccountID(12345677).get(0));

		assertEquals("withdraw 12345677 100.111", bank.getAccountTransactionHistorybyAccountID(12345677).get(1));

	}

	@Test
	void valid_bank_report_without_invalid_commands() {

		bank.addAccount("CHECKING", 12345678, 2.2, 0);
		bank.depositMoneyFromBank(12345678, 1000);

		bank.addAccount("CHECKING", 12345677, 2.2, 0);
		bank.depositMoneyFromBank(12345677, 1000);
		bank.withdrawMoneyFromBank(12345677, 100.111);

		bank.addAccount("CD", 12121212, 2.1, 1500);

		accountReport.generateReport("create CHECKING 12345678 1000.00 2.20");
		accountReport.generateReport("deposit 12345678 1000");
		accountReport.generateReport("create checking 12345677 2.2");
		accountReport.generateReport("transfer 12345678 12345677 200");

		accountReport.generateReport("withdraw 12345677 100.111");

		accountReport.generateReport("deposit 12345677 1000");
		List<String> actual = accountReport.getCompleteBankReport();
		assertEquals(8, actual.size());
	}

}
