import java.text.NumberFormat;
import java.util.*;

public class Account {
	private String number;
	private int accType;
	private double balance = 0;
	private String user_id;

	public Account(String number, String user_id,int type) {
		this.number = number;
		this.user_id = user_id;
		this.accType=type;
	}


	public String getNumber() {
		return number;
	}

	public String getUserID() {
		return user_id;
	}

	public double getBalance() {
		return balance;
	}

	public int getAccType() {
		return accType;
	}


	public String getUser_id() {
		return user_id;
	}

	public String getFormattedBalance() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
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
			   String.format("%-10s%-20s%s", this.number, this.user_id, this.balance);
	}

}

