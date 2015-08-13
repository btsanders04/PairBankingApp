import java.text.NumberFormat;
public class Transaction {
	private int type;
	private double amount;
	private String date;

	public Transaction(int type, double amount){
		this.type = type;
		this.amount = amount;
		
	}
	
	public String getDate() {
		return date;
	}
	
	public int getType() {
		return type;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	public double getAmount() {
		
		return amount;
	}
	
	public String getFormattedAmount(){
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return nf.format(amount);
	}
	

	@Override
	public String toString() {
		String t="";
		switch(type){
		case(1): t+="Deposit";
		break;
		case(2): t+="Check";
		break;
		case(3): t+="Withdraw";
		break;
		case(4): t+="Debit";
		break;
		}
		return String.format("%-15s%-10s%s", t,getFormattedAmount(),getDate());
	}
	
	
	
}
