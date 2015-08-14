import java.util.*;
public class Bank {

//	private HashMap<Integer,Account> members= new HashMap<Integer,Account>();
	BankDBQuery database = new BankDBQuery();
	
	
	//done
	public Member createMember(String name){
		Random r = new Random();
		
		String memberid = String.valueOf(100000+r.nextInt(899999));
		while(database.getUser(memberid)!=null){
			memberid=String.valueOf(100000+r.nextInt(899999));
		}
		Member m = new Member(memberid, name);
		database.createUser(m);
		return m;
	}
	
	public Account createNewAccount(String memberId, int type, double balance){
		Random r = new Random();
		
		String accountId = String.valueOf(100000+r.nextInt(899999));
		if(database.hasAccountAlready(memberId,type)){
			return null;
		}
		while(database.getAccount(memberId,accountId)!=null){
			accountId=String.valueOf(100000+r.nextInt(899999));
		}
		Account a = new Account(accountId, memberId,type);
		a.setBalance(balance);
		database.storeAccount(a);
		return a;
	}
	
	
	public void processAllTransactions( Account account){
		//String print =String.format("%-15s%-10s%s","Transaction","Amount","Date");
		//print+="\n---------------------------------\n";
		ArrayList<Transaction> transactions = database.getTransactions(account);
		for(Transaction t: transactions){
			processTransaction(t,account);
			//print+= t + "\n";
		}
		database.updateTransactionStatus(account);
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
