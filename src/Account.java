import java.text.NumberFormat;
import java.util.*;

public class Account {
	private String number;
	private int accType;
	private double balance = 0;
	private String user_id;
	private BankDBQuery db = new BankDBQuery();

	public Account(String number, String user_id, int type) {
		this.number = number;
		this.user_id = user_id;
		this.accType = type;
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

	public String getAccountString() {
		switch (this.accType) {
		case (1):
			return "Checking";
		case (2):
			return "Saving";
		}
		return "";
	}

	public String getUser_id() {
		return user_id;
	}

	public String getFormattedBalance() {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return nf.format(balance);
	}

	public void addTransfer(double transfer) {
		this.balance += transfer;
	}

	public void calcBalance(double balance) {

		this.balance += balance;
		if (this.balance < 0) {
			// if less than zero and its a checking account and they have a
			// savings account
			if(accType==1){
				if (db.hasAccountAlready(user_id, 2)) {
					Account savings = db.getAccount(user_id, 2);
					savings.calcBalance(this.balance - 15);
					this.balance = 0;
					db.updateBalance(savings);
				} 
				else {
					this.balance -= 35;
				}
			}
			else {
				this.balance -= 35;
			}
		}

	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return String.format("%-10s%-10s%s", "Account", "Type", "Balance")
				+ "\n"
				+ String.format("%-10s%-10s%s", this.number,
						getAccountString(), this.balance);
	}

}
