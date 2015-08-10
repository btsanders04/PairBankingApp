import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class EvilCorpApp {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String filename = (System.getProperty("user.dir") + File.separatorChar + "Bank.txt");
		Bank bank = new Bank();
		WeatherMan w = new WeatherMan();
		boolean foundAccount = false, anotherAcct = true;
		System.out.println("Welcome to Evil Corp Savings and Loan");
		/* Needs work
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String overhead = reader.readLine().trim();
			System.out.println(overhead);
			while(overhead!=null){
				String[] userInfo = reader.readLine().split("\\s");
				System.out.println(reader.readLine()); //+ userInfo[2]);
				System.out.println(userInfo[0] + " " + userInfo[1] + " " + userInfo[2]);
				Account a = new Account(Integer.parseInt(userInfo[0]),userInfo[1]);
				a.calcBalance(Double.parseDouble(userInfo[2]));
				bank.addMember(a);
				overhead = reader.readLine().trim();
				System.out.println(overhead);
			}
		} catch (FileNotFoundException e1) {
			System.out.println("No data to grab from");
		} catch (IOException e) {
			
		}*/
		while (anotherAcct) {
			System.out.println("If new user type new otherwise enter account number");
			Account user = null;
			while (!foundAccount) {
				String account = sc.nextLine();
				if (account.equalsIgnoreCase("new")) {
					System.out.println("Please Create an Account");
					System.out.println("Enter your name : ");

				
					String name = sc.nextLine().trim();
					while(!Validator.checkName(name)){
						System.out.println("Please enter a correct name:");
						name = sc.nextLine().trim();
					}
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
					bank.processTransaction(
							new Transaction("deposit", Double.parseDouble(initialAmount)), user);
					System.out.println("You have " + user.getFormattedBalance()
							+ " in your account.");
					
				}
				else if(!Validator.checkAccount(account)){
					System.out.println("You must enter a valide account.");
					foundAccount=false;
				}
				else if(bank.findMember(Integer.parseInt(account))) {
					user = bank.getMemberAccount(Integer.parseInt(account));
					foundAccount = true;
					System.out.println("Your account has been found : ");
					System.out.println(user);
				} else {
					System.out.println("Sorry your account has not been found please reenter your account number : ");
					foundAccount = false;
				}
			}
			String type = "continue";
			while (!type.equals("-1")) {
				System.out
						.println("Enter a transaction type (Check, Debit, Deposit, Withdrawal) or -1 to finish : ");
				
				//validate
				type = sc.nextLine().trim().toLowerCase();
				while(!Validator.checkTransactionType(type)){
					System.out.println("Please Enter correct type :");
					type = sc.nextLine().trim().toLowerCase();
				}
				
				if (type.equals("-1")) {
					break;
				}
				System.out.println("Enter the amount of the " + type + ":");
				
				// validate
				String amt = sc.nextLine().trim();
				Double amount;
				
				while(!Validator.checkTransactionAmount(amt)){
					System.out.println("please enter a correct amount : ");
					amt = sc.nextLine().trim();
				}
					amount = Double.parseDouble(amt);
				System.out.println("Enter the date of the check (mm/dd/yyyy): ");
				
				// validate
				String date = sc.nextLine().trim();
				while(!Validator.checkDate(date)){
					System.out.println("please enter a valide date in the form of mm/dd/yyyy");
					date = sc.nextLine().trim();
				}
				
				Date d = w.getDate(date);
				Transaction t = new Transaction(type, amount);
				t.setDate(d);
				user.addTransaction(t);
			}
			bank.processAllTransactions(user);
			try {
				PrintWriter p = new PrintWriter(filename);
				p.println(user);
				System.out.println("Transaction Summary");
				System.out.println(user.printTransactions());
				System.out.println("The account balance for " + user.getNumber() + " is " + user.getFormattedBalance());
				System.out.println("Do you want to enter another account? (Y/N)");
				p.close();
			}
			catch (FileNotFoundException e) {
				System.out.println("File not found");
			}
			
			String cont = sc.nextLine();
			if (cont.equalsIgnoreCase("n")) {
				anotherAcct = false;
			}
			foundAccount=false;
		}

	}
}
