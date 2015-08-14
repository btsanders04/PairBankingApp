import java.sql.SQLException;
import java.util.*;

public class EvilCorpApp {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Bank bank = new Bank();
		BankDBQuery database = new BankDBQuery();
		BankDBQuery.openConnection();
		boolean foundAccount = false, loggedOn = false;
		System.out.println("Welcome to Evil Corp Savings and Loan");
		System.out.format("%10s%s", "New User", "1");
		System.out.format("%10s%s", "Log On", "2");
		int enterSite = sc.nextInt();
		sc.nextLine();
		switch (enterSite) {
		case (1): // new user
			System.out.println("Enter your name : ");
			String name = sc.nextLine().trim();
			// create member based on name;
			System.out
					.println("You are now a member of Evil Corp Banking. Your id is : ");
			break;
		case (2):
			System.out.println("Enter your member id: ");
			// check if member id is in database if so set loggedOn=true

			while (loggedOn) {
				System.out.format("%10s%s", "Create Account", "1");
				System.out.format("%10s%s", "Transactions", "2");
				System.out.format("%10s%s", "Close Account", "3");
				System.out.format("%10s%s", "Exit Application", "4");
				int action = sc.nextInt();
				sc.nextLine();
				switch (action) {
				case (1):
					System.out.println("Enter member_id:");
					String memberId = sc.nextLine().trim();
					System.out.println("Enter account type :");
					System.out.format("%10s%s", "Checking", "1");
					System.out.format("%10s%s", "Savings", "2");

					int typeofAccount = sc.nextInt();
					sc.nextLine();

					switch (typeofAccount) {
					case (1): // create checking account check if one already
								// exists
								// update membership # accounts
						break;
					case (2):// check if one already exists
						// createaccount(id,typeofAccount)
						// String accountNumber = generateaccountnumber();
						/*
						 * System.out.println("An account has been created for "
						 * + user.getName() + ". \nYour account" +
						 * " number is : " + user.getNumber());
						 * System.out.println("Enter an initial deposit: ");
						 * 
						 * String initialAmount = sc.nextLine().trim(); while
						 * (!Validator.checkTransactionAmount(initialAmount)) {
						 * System
						 * .out.println("Please enter a correct amount :");
						 * initialAmount = sc.nextLine().trim(); }
						 * 
						 * user.setBalance(Double.parseDouble(initialAmount));
						 */
						break;

					}
					// Account account = new Account(number,user_id)

					// validate

					// database.updateBalance(user);
					// System.out.println("You have " +
					// account.getFormattedBalance()
					// + " in your account.");

					break;

				case (2): // transaction
					System.out.println("Enter your member id: ");
					System.out.println("Please enter your account number");

					String number = sc.nextLine().trim();
					Account account;
					if (database.findAccount(number)) {
						account = database.getAccount(number);
						System.out.println("Your account has been found : ");
						System.out.println(account);
					} else {
						System.out.println("Sorry your account was not found.");
						break;
					}
					int type = 0;

					transLoop: while (type != -1) {
						System.out.println("Enter a transaction type : ");
						System.out.format("%10s%s", "Deposit", "1");
						System.out.format("%10s%s", "Check", "2");
						System.out.format("%10s%s", "Withdraw", "3");
						System.out.format("%10s%s", "Debit", "4");
						System.out.format("%10s%s", "Transfer", "5");
						System.out.format("%10s%s", "Exit", "-1");
						// validate
						type = sc.nextInt();
						sc.nextLine();

						String transType = "";
						switch (type) {

						case (1):
							transType = "Deposit";
							break;
						case (2):
							transType = "Check";
							break;
						case (3):
							transType = "Withdraw";
							break;
						case (4):
							transType = "Debit";
							break;
						case (5):
							transType = "Transfer";
							break;
						case (-1):
							break transLoop;
						}
						System.out.println("Enter the amount of the "
								+ transType + " : ");

						// validate
						String amt = sc.nextLine().trim();

						while (!Validator.checkTransactionAmount(amt)) {
							System.out
									.println("please enter a correct amount : ");
							amt = sc.nextLine().trim();
						}

						Double amount = Double.parseDouble(amt);

						System.out.println("Enter the date of the " + transType
								+ " (mm/dd/yyyy): ");

						String date = sc.nextLine().trim();
						while (!Validator.checkDate(date)) {
							System.out
									.println("please enter a valide date in the form of mm/dd/yyyy");
							date = sc.nextLine().trim();
						}
						Transaction t = new Transaction(type, amount);
						t.setDate(date);
						if (type == 5) {
							System.out
									.println("Enter Account to transfer to :");
							// check if valid account, transfer money
						}
						database.storeTransaction(t, account);
					}
					break;

				case (3): // close account
					System.out
							.println("Enter Account # you would like to close: ");
					String close = sc.nextLine().trim();
					if (database.findAccount(close)) {
						Account deleteAccount = database.getAccount(close);
						if (deleteAccount.getBalance() == 0) {
							database.deleteTranscations(close);
							database.deleteAccount(close);
						} else {
							System.out
									.println("You can not close that account because it does not have a"
											+ " balance of 0");
							System.out.println("Your account balance is "
									+ deleteAccount.getFormattedBalance());
						}
					} else {
						System.out.println("That account cannot be found");
					}
					break;

				case (4):
					loggedOn = false;

					/**
					 * System.out.println(bank.processAllTransactions(user));
					 * database.updateBalance(user);
					 * System.out.println("The account balance for " +
					 * user.getNumber() + " is " + user.getFormattedBalance());
					 * System.out
					 * .println("Do you want to enter another account? (Y/N)");
					 */
					break;
				}

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
