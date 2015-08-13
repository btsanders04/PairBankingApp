import java.util.*;
public class Bank {

//	private HashMap<Integer,Account> members= new HashMap<Integer,Account>();
	BankDBQuery database = new BankDBQuery();
	public Account createMemberAccount(String name){
		Random r = new Random();
		
		String acct = String.valueOf(100000+r.nextInt(899999));
		while(database.findAccount(acct)){
			acct=String.valueOf(100000+r.nextInt(899999));
		}
		Account a = new Account(acct, name);
		database.storeAccount(a);
		return a;
	}
	
	public String processAllTransactions(Account a){
		String print =String.format("%-15s%-10s%s","Transaction","Amount","Date");
		print+="\n---------------------------------\n";
		ArrayList<Transaction> transactions = database.getTransactions(a);
		for(Transaction t: transactions){
			processTransaction(t,a);
			print+= t + "\n";
		}
		database.updateTransactionStatus(a);
		return print;
	}
	
	public void processTransaction(Transaction t, Account a){
		double amount = t.getAmount();
		switch(t.getType()){
		case(2):a.calcBalance(-amount);
		break;
		case(4): a.calcBalance(-amount);
		break;
		case(3):a.calcBalance(-amount);
		break;
		case(1):a.calcBalance(amount);
		break;
		}
	}
	
}	
