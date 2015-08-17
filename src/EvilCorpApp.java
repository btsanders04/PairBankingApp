import java.sql.SQLException;
import java.util.*;

public class EvilCorpApp {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Bank bank = new Bank();
		BankDBQuery database = new BankDBQuery();
		BankDBQuery.openConnection();
		boolean loggedOn = false;
		System.out.println("Welcome to Evil Corp Savings and Loan");
		System.out.format("%s", "\n----------------\n");
		System.out.format("%-10s%s", "| New User", " - 1 |");
		System.out.format("%s", "\n|--------------|\n");
		System.out.format("%-10s%s", "| Log On", " - 2 |");
		System.out.format("%s", "\n----------------\n");
		System.out.println();
		int enterSite = sc.nextInt();
		sc.nextLine();
		switch (enterSite) {
		case (1): // new user
			System.out.println("Enter your name : ");
			String name = sc.nextLine().trim();
			Member newUser = bank.createMember(name);
			System.out
					.println("You are now a member of Evil Corp Banking. Your id is : "
							+ newUser.getId());
		case (2):
			System.out.println("Enter your member id: ");
			String memberId = sc.nextLine().trim();
			Member member = database.getUser(memberId);
			if (member != null)
				loggedOn = true;

			while (loggedOn) {
				System.out.format("%s", "\n------------------------\n");
				System.out.format("%-20s%s", "|Create Account", "-1 |");
				System.out.format("%s", "\n|----------------------|\n");
				System.out.format("%-20s%s", "|Transactions", "-2 |");
				System.out.format("%s", "\n|----------------------|\n");
				System.out.format("%-20s%s", "|Close Account", "-3 |");
				System.out.format("%s", "\n|----------------------|\n");
				System.out.format("%-20s%s", "|History", "-4 |");
				System.out.format("%s", "\n|----------------------|\n");
				System.out.format("%-20s%s", "|Log Out", "-5 |");
				System.out.format("%s", "\n------------------------\n");
				int action = sc.nextInt();
				sc.nextLine();
				switch (action) {
				case (1):

					System.out.println("Enter account type :");
					System.out.format("%-10s%s", "Checking", "1");
					System.out.println();
					System.out.format("%-10s%s", "Savings", "2");
					System.out.println();

					int typeofAccount = sc.nextInt();

					sc.nextLine();
					if (typeofAccount > 2 && typeofAccount < 1){
						System.out
						.println("You have entered an incorrect value");
					
						break;
					}
					System.out.println("Enter an initial balance : ");
					double balance = sc.nextDouble();
					sc.nextLine();

					Account account = bank.createNewAccount(member.getId(),
							typeofAccount, balance);
					
					if (account == null) {
						System.out
								.println("You already have an account of this type");
						break;
					}

					System.out.println("An account has been created for "
							+ member.getName() + ". \nYour "
							+ account.getAccountString()
							+ " Account number is : " + account.getNumber());

					System.out.println("You have "
							+ account.getFormattedBalance()
							+ " in your account.");

					break;

				case (2): // transaction
					System.out.println("Please enter your account number");
					String number = sc.nextLine().trim();
					Account transactionAccount = database.getAccount(number);

					if (transactionAccount != null) {
						System.out.println("Your account has been found : ");
						System.out.println(transactionAccount);
					} else {
						System.out.println("Sorry your account was not found.");
						break;
					}
					int type = 0;

					transLoop: while (type != -1) {
						System.out.println("Enter a transaction type : ");
						System.out.format("%s", "\n-----------------\n");
						System.out.format("%-10s%s", "|Deposit", " -1   |");
						System.out.format("%s", "\n|---------------|\n");
						System.out.format("%-10s%s", "|Check", " -2   |");
						System.out.format("%s", "\n|---------------|\n");
						System.out.format("%-10s%s", "|Withdraw", " -3   |");
						System.out.format("%s", "\n|---------------|\n");
						System.out.format("%-10s%s", "|Debit", " -4   |");
						System.out.format("%s", "\n|---------------|\n");
						System.out.format("%-10s%s", "|Transfer", " -5   |");
						System.out.format("%s", "\n|---------------|\n");
						System.out.format("%-10s%s", "|Exit", " -(-1)|");
						System.out.format("%s", "\n-----------------\n");

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

						Transaction t = new Transaction(type,
								transactionAccount.getNumber(), amount);
						t.setDate(date);

						if (type == 5) {
							System.out
									.println("Enter Account to transfer to :");
							//String accNum = sc.nextLine().trim());
							Account transferAccount = database.getAccount(sc.nextLine().trim());
							transferAccount.addTransfer(amount);
							database.updateBalance(transferAccount);
						}

						// check if valid account, transfer money
						database.storeTransaction(t, transactionAccount, member.getId());
					}
					bank.processAllTransactions(transactionAccount);
					database.updateBalance(transactionAccount);
					System.out.println("The account balance for "
							+ transactionAccount.getNumber() + " is "
							+ transactionAccount.getFormattedBalance());

					break;

				case (3): // close account
					System.out
							.println("Enter Account # you would like to close: ");
					String close = sc.nextLine().trim();
					Account closeAccount = database.getAccount(close);
					if (closeAccount!=null) {
						if (closeAccount.getBalance() == 0) {
							database.deleteTranscations(close);
							database.deleteAccount(close);
						} else {
							System.out
									.println("You can not close that account because it does not have a"
											+ " balance of 0");
							System.out.println("Your account balance is "
									+ closeAccount.getFormattedBalance());
						}
					} else {
						System.out.println("That account cannot be found");
					}
					break;
				case (4):
					System.out.println(database.transactionHistory(member));
					break;

				case (5):
					loggedOn = false;
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
