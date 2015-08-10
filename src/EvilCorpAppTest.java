import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;


public class EvilCorpAppTest {
	Bank b = new Bank();
	WeatherMan w = new WeatherMan();
	@Test
	public void test() {
		Account user = b.createMemberAccount("Brandon");
		Transaction one = new Transaction("deposit", 400.0);
		//b.processTransaction(one, user);
		Date firstD = w.getDate("04/23/1990");
		one.setDate(firstD);
		user.addTransaction(one);
		Transaction two = new Transaction("check", 600.0);
		Date twoDate = w.getDate("04/23/1993");
		two.setDate(twoDate);
		user.addTransaction(two);
		b.processAllTransactions(user);
		System.out.println(user.printTransactions());
		System.out.println("The account balance for " + user.getNumber() + " is " + user.getFormattedBalance());
		Transaction three = new Transaction("deposit", 400.50);
		Date thirdD = w.getDate("06/13/2000");
		three.setDate(thirdD);
		user.addTransaction(three);
		Transaction four = new Transaction("debit", 150.0);
		Date fourthDate = w.getDate("04/23/1995");
		four.setDate(fourthDate);
		user.addTransaction(four);
		b.processAllTransactions(user);
		System.out.println(user.printTransactions());
		System.out.println("The account balance for " + user.getNumber() + " is " + user.getFormattedBalance());
		
	}

}
