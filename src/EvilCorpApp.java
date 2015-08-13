import java.sql.SQLException;
import java.util.*;

public class EvilCorpApp {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Bank bank = new Bank();
		BankDBQuery database = new BankDBQuery();
		BankDBQuery.openConnection();
		boolean foundAccount = false, anotherAcct = true;
		System.out.println("Welcome to Evil Corp Savings and Loan");
		
		
		while (anotherAcct) {
			System.out.println("If new user type new otherwise enter account number. If you wish to close an account type close");
			Account user = null;
			beginningScreen:
			while (!foundAccount) {
				String account = sc.nextLine();
				if(account.equalsIgnoreCase("close")){
					System.out.println("Please enter account number you would like to close : ");
					account = sc.nextLine().trim();
					while(!Validator.checkAccount(account)){
						System.out.println("Please enter correct account number, Enter back to exit");
						account=sc.nextLine().trim();
						if(account.equalsIgnoreCase("back")){
							break beginningScreen;
						}
					}
					if(database.findAccount(account)){
						Account deleteAccount = database.getAccount(account);
						if(deleteAccount.getBalance()==0){
							database.deleteTranscations(account);
							database.deleteAccount(account);
							break beginningScreen;
						}
						else {
							System.out.println("You can not close that account because it does not have a"
									+ " balance of 0");
							System.out.println("Your account balance is " + deleteAccount.getFormattedBalance());
							break beginningScreen;
						}
					}
					else {
						System.out.println("That account cannot be found");
						break beginningScreen;
					}
				}
					
				if (account.equalsIgnoreCase("new")) {
					System.out.println("Please Create an Account");
					System.out.println("Enter your name : ");

				
					String name = sc.nextLine().trim();
					while(!Validator.checkName(name)){
						System.out.println("Please enter a correct name:");
						name = sc.nextLine().trim();	
					}
					
				//	String nameConcat = name.replace(' ', '.');
					user = bank.createMemberAccount(name);
					foundAccount = true;
					System.out.println("An account has been created for "
							+ user.getName() + ". \nYour account"
							+ " number is : " + user.getNumber());
					System.out.println("Enter an initial deposit: ");

					// validate
					String initialAmount=sc.nextLine().trim();
					while(!Validator.checkTransactionAmount(initialAmount)){
						System.out.println("Please enter a correct amount :");
						initialAmount=sc.nextLine().trim();
					}
					
					user.setBalance(Double.parseDouble(initialAmount));
					database.updateBalance(user);
					System.out.println("You have " + user.getFormattedBalance()
							+ " in your account.");
					
				}
				else if(!Validator.checkAccount(account)){
					System.out.println("You must enter a valid account.");
					foundAccount=false;
				}
				
				else if(database.findAccount(account)) {
					user = database.getAccount(account);
					foundAccount = true;
					System.out.println("Your account has been found : ");
					System.out.println(user);
				} else {
					System.out.println("Sorry your account has not been found please reenter your account number : ");
					foundAccount = false;
				}
			}
			String type = "continue";
			if(foundAccount){
			while (!type.equals("-1")) {
				System.out
						.println("Enter a transaction type (1-Deposit, 2-Check, 3-Withdrawal, 4-Debit) or -1 to finish : ");
				
				//validate
				type = sc.nextLine().trim().toLowerCase();
				while(!Validator.checkTransactionType(type)){
					System.out.println("Please Enter correct type :");
					type = sc.nextLine().trim().toLowerCase();
				}
				
				if (type.equals("-1")) {
					break;
				}
				String transType = "";
				switch(type){
				
				case("1"):  transType = "Deposit";
					break;
				case("2"):	transType = "Check";
					break;
				case("3"): 	transType = "Withdraw";
					break;
				case("4"):	transType = "Debit";
					break;
				}
				System.out.println("Enter the amount of the " + transType + " : ");
				
				// validate
				String amt = sc.nextLine().trim();
				
				while(!Validator.checkTransactionAmount(amt)){
					System.out.println("please enter a correct amount : ");
					amt = sc.nextLine().trim();
				}
					Double amount = Double.parseDouble(amt);
					
				System.out.println("Enter the date of the check (mm/dd/yyyy): ");
				
				String date = sc.nextLine().trim();
				while(!Validator.checkDate(date)){
					System.out.println("please enter a valide date in the form of mm/dd/yyyy");
					date = sc.nextLine().trim();
				}
				
				Transaction t = new Transaction(Integer.parseInt(type), amount);
				t.setDate(date);
				database.storeTransaction(t, user);
			}
			
				System.out.println(bank.processAllTransactions(user));
				database.updateBalance(user);
				System.out.println("The account balance for " + user.getNumber() + " is " + user.getFormattedBalance());
				System.out.println("Do you want to enter another account? (Y/N)");
			
			String cont = sc.nextLine();
			if (cont.equalsIgnoreCase("n")) {
				anotherAcct = false;
			}
			foundAccount=false;
		}
		}
try {
	BankDBQuery.conn.close();
	sc.close();
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	}
}
