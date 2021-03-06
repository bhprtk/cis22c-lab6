
/**
* CustomerInterface.java
* @author Naqib Khan
* @author Pratik Bhandari
* CIS 22C, Lab 6
*/
import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.ArrayList;

public class CustomerInterface {

	/**
	 * Add cash for the given user
	 * 
	 * @param input    Scanner object for user input
	 * @param customer Add cash for the customer
	 */
	public static void addCash(Scanner input, Customer customer) {
		DecimalFormat df = new DecimalFormat("###,##0.00");
		Double cash;
		System.out.println("\nYour current cash balance is $" + df.format(customer.getCash()));
		System.out.print("\nEnter the amount of cash to add: $");
		cash = input.nextDouble();
		input.nextLine();
		customer.updateCash(cash);
		System.out.println("\nYour current cash balance is $" + df.format(customer.getCash()) + "\n");
	}

	/**
	 * Sell funds for the given user
	 * 
	 * @param input    Scanner object for user input
	 * @param customer Sell funds for the customer
	 */
	public static void sellFunds(Scanner input, Customer customer) {
		String mutualName;
		String sellSharesStr;
		DecimalFormat df = new DecimalFormat("###,##0.00");

		if (!customer.hasOpenAccounts()) {
			System.out.println("\nYou don't have any funds to sell at this time.");
		} else {
			System.out.println("\nYou own the following mutual funds:\n");
			customer.printAccountsByName();
			System.out.print("\nEnter the name of the fund to sell: ");
			mutualName = input.nextLine();
			MutualFundAccount fundAccToSell = customer.getAccountByName(mutualName);
			if (fundAccToSell == null) {
				System.out.println("Sorry you don't own an account by that name.");
			} else {
				System.out.print("Enter the number of shares to sell or \"all\" to sell everything: ");
				sellSharesStr = input.nextLine();
				if (sellSharesStr.compareTo("all") == 0) {
					customer.sellFund(mutualName);
				} else {
					double shares = Double.parseDouble(sellSharesStr);
					customer.sellShares(mutualName, shares);
				}
				System.out.println("\nYou own the following funds:");
				customer.printAccountsByName();
				System.out.println("\nYour current cash balance is $" + df.format(customer.getCash()) + "\n");
			}
		}
	}

	/**
	 * Display funds for the given customer
	 * 
	 * @param input    Scanner object for user input
	 * @param customer Display funds for the customer
	 */
	public static void displayFunds(Scanner input, Customer customer) {
		if (!customer.hasOpenAccounts()) {
			System.out.println("\nYou don't have any funds to display at this time.");
		} else {
			int displayChoice;
			System.out.println("\nView Your Mutual Funds By:.\n");
			System.out.println("1. Name");
			System.out.println("2. Value");
			System.out.print("\nEnter your choice (1 or 2): ");
			displayChoice = input.nextInt();
			input.nextLine();
			if (displayChoice == 1) {
				customer.printAccountsByName();
			} else if (displayChoice == 2) {
				customer.printAccountsByValue();

			} else {
				System.out.println("\nInvalid choice!");
			}
		}
	}

	/**
	 * Purchase funds for the given customer
	 * 
	 * @param input    Scanner object for user input
	 * @param funds    HashTable of all funds in the file
	 * @param customer Purchase funds for the customer
	 */
	public static void purchaseFunds(Scanner input, HashTable<MutualFund> funds, Customer customer) {
		String ticker;
		double numShares;
		if (funds.getNumElements() == 0) {
			System.out.println("\nYou don't have any funds to display at this time.\n");
		} else {
			System.out.println("\nPlease select from the options below:\n");
			System.out.println(funds);
			System.out.print("Enter the ticker of the fund to purchase: ");
			ticker = input.nextLine();
			System.out.print("\nEnter the number of shares to purchase: ");
			numShares = input.nextDouble();
			input.nextLine();
			MutualFund fundToBuy = funds.get(new MutualFund(ticker));
			if (!customer.addFund(numShares, fundToBuy)) {
				System.out.println("\nYou don't have enough cash to purchase that fund: ");
				System.out.println("Please add cash to make a purchase\n");
			} else {
				System.out.println("\nYou Successfully added shares of the following fund:\n");
				System.out.println(fundToBuy);
				System.out.println("Number of shares added: " + numShares);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		final int NUM_MUTUAL_FUNDS = 7;
		final int NUM_CUSTOMERS = 100;
		HashTable<MutualFund> funds = new HashTable<>(NUM_MUTUAL_FUNDS * 2);
		HashTable<Customer> customers = new HashTable<>(NUM_CUSTOMERS);

		String first, last, email, password, mutualName, ticker;
		double cash, sharePrice, numShares, fee;
		int numAccounts;
		ArrayList<MutualFundAccount> mfs = new ArrayList<>();
		File file1 = new File("mutual_funds.txt");
		File file2 = new File("customers.txt");

		Scanner input;

		try {
			input = new Scanner(file1);
			while (input.hasNextLine()) {
				mutualName = input.nextLine();
				ticker = input.nextLine();
				sharePrice = Double.parseDouble(input.nextLine());
				fee = Double.parseDouble(input.nextLine());
				funds.put(new MutualFund(mutualName, ticker, sharePrice, fee));
			}
			input.close();
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		}

		try {
			input = new Scanner(file2);
			while (input.hasNextLine()) {
				first = input.next();
				last = input.next();
				input.nextLine();
				email = input.nextLine();
				password = input.nextLine();
				cash = Double.parseDouble(input.nextLine());
				numAccounts = Integer.parseInt(input.nextLine());
				mfs.clear();
				for (int i = 0; i < numAccounts; i++) {
					ticker = input.nextLine();
					numShares = Double.parseDouble(input.nextLine());
					MutualFund mf = funds.get(new MutualFund(ticker));
					mfs.add(new MutualFundAccount(mf, numShares));
				}

				customers.put(new Customer(first, last, email, password, cash, mfs));
			}
			input.close();
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		}

		input = new Scanner(System.in);

		System.out.println("Welcome to Mutual Fund InvestorTrack (TM)!");

		System.out.print("\nPlease enter your email address: ");
		email = input.nextLine();

		System.out.print("Please enter your password: ");
		password = input.nextLine();
		Customer temp = new Customer(email, password);

		if (!customers.contains(temp)) {
			System.out.println("\nWe don't have your account on file...");
			System.out.println("\nLet's create an account for you!");
			System.out.print("Enter your first name: ");
			first = input.nextLine();
			System.out.print("Enter your last name: ");
			last = input.nextLine();
			customers.put(new Customer(first, last, email, password));
		}

		Customer curr = customers.get(temp);
		System.out.println("\nWelcome, " + curr.getFirstName() + " " + curr.getLastName() + "!\n");
		String menuChoice = "";
		while (true) {
			System.out.println("\nPlease select from the following options:\n");
			System.out.println("A. Purchase a Fund");
			System.out.println("B. Sell a Fund");
			System.out.println("C. Add Cash");
			System.out.println("D. Display Your Current Funds");
			System.out.println("X. Exit\n");

			System.out.print("Enter your choice: ");
			menuChoice = input.nextLine();

			if (menuChoice.equals("A")) {
				purchaseFunds(input, funds, curr);
			} else if (menuChoice.equals("B")) {
				sellFunds(input, curr);
			} else if (menuChoice.equals("C")) {
				addCash(input, curr);
			} else if (menuChoice.equals("D")) {
				displayFunds(input, curr);
			} else if (menuChoice.equals("X")) {
				System.out.println("\nGoodbye!");
				break;
			} else {
				System.out.println("\nInvalid menu option. Please enter A-D or X to exit.");
			}

		}

	}
}