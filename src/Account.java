import java.text.NumberFormat;
import java.util.*;

public class Account {
	private String number;
	private String name;
	private double balance = 0;
//	private SortedMap<String, Transaction> datedTransactions = new TreeMap<String, Transaction>();
	//private ArrayList<Transaction> transactionHistory = new ArrayList<Transaction>();

	public Account(String number, String name) {
		this.number = number;
		this.name = name;
	}


	public String getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public double getBalance() {
		return balance;
	}

	public String getFormattedBalance() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		// if(balance<0){
		// return "-"+nf.format(balance);
		// }
		return nf.format(balance);
	}

	public void calcBalance(double balance) {

		this.balance += balance;
		if (this.balance < 0) {
			this.balance -= 35;
		}
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return String.format("%-10s%-20s%s", "Account", "Name", "Balance")+"\n"+
			   String.format("%-10s%-20s%s", this.number, this.name, this.balance);
	}

}

class DateComp implements Comparator<Date> {

	@Override
	public int compare(Date d1, Date d2) {
		return d1.compareTo(d2);
	}
}
