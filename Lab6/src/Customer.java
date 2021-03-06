
/**
* Customer.java
* @author Naqib Khan
* @author Pratik Bhandari
* CIS 22C, Lab 6
*/

import java.util.ArrayList;
import java.text.DecimalFormat;

public class Customer {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String accountNum;
	private double cash;

	private BST<MutualFundAccount> funds_value = new BST<>();
	private BST<MutualFundAccount> funds_name = new BST<>();

	/** CONSTRUCTORS */

	/**
	 * Creates a new Customer when only email and password are known
	 * 
	 * @param email    the Customer email
	 * @param password the Customer password Assigns firstName to "first name
	 *                 unknown" Assigns lastName to "last name unknown" Assigns cash
	 *                 to 0 Assigns accountNum to "none"
	 */

	public Customer(String email, String password) {
		this.email = email;
		this.password = password;
		this.firstName = "first name unknown";
		this.lastName = "last name unknown";
		this.cash = 0;
		this.accountNum = "none";
	}

	/**
	 * Creates a new Customer with no cash
	 * 
	 * @param firstName member first name
	 * @param lastName  member last name Assigns cash to 0 Calls getAccountSeed and
	 *                  assigns accountNum to this value after converting it to a
	 *                  String BY USING CONCATENATION (easiest way) Hint: Make sure
	 *                  you get no warnings or you did not call getAccountSeed
	 *                  correctly!
	 */
	public Customer(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.cash = 0;
		this.accountNum = "" + MutualFundAccount.getAccountSeed();
	}

	/**
	 * Creates a new Customer when all information is known
	 * 
	 * @param firstName member first name
	 * @param lastName  member last name
	 * @param cash      the starting amount of cash
	 * @param al        the MutualFundAccounts owned by this Customer (Hint: add
	 *                  these to the BSTs) Calls getAccountSeed and assigns
	 *                  accountNum to this value after converting it to a String BY
	 *                  USING CONCATENATION (easiest way) Hint: Make sure you get no
	 *                  warnings or you did not call getAccountSeed correctly!
	 */
	public Customer(String firstName, String lastName, String email, String password, double cash,
			ArrayList<MutualFundAccount> al) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.cash = cash;
		this.accountNum = "" + MutualFundAccount.getAccountSeed();
		for (int i = 0; i < al.size(); i++) {
			funds_value.insert(al.get(i), new ValueComparator());
			funds_name.insert(al.get(i), new NameComparator());
		}
	}

	/** ACCESORS */

	/**
	 * Accesses the customer first name
	 * 
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Accesses the customer last name
	 * 
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Accesses the user's account number
	 * 
	 * @return the account number
	 */
	public String getAccountNum() {
		return accountNum;
	}

	/**
	 * Accesses the email address
	 * 
	 * @return the email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Determines whether a given password matches the customer password
	 * 
	 * @param anotherPassword the password to compare
	 * @return whether the two passwords match
	 */
	public boolean passwordMatch(String anotherPassword) {
		return password.equals(anotherPassword);
	}

	/**
	 * Accesses a specific fund
	 * 
	 * @param name the chosen fund
	 * @return the specified mutual fund
	 */
	public MutualFundAccount getAccountByName(String name) {
		return funds_name.search(new MutualFundAccount(new MutualFund(name, "")), new NameComparator());
	}

	/**
	 * Accesses the amount of cash in your account
	 * 
	 * @return the amount of cash
	 */
	public double getCash() {
		return cash;
	}

	/**
	 * Accesses whether any accounts exist for this customer
	 * 
	 * @return whether the customer currently holds any accounts
	 */
	public boolean hasOpenAccounts() {
		return !funds_name.isEmpty();
	}

	/** MUTATORS */

	/**
	 * Updates the customer first name
	 * 
	 * @param firstName a new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Updates the customer last name
	 * 
	 * @param lastName a new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Updates the value of the email address
	 * 
	 * @param name the Customer's email address
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Updates the value of the password
	 * 
	 * @param name the Customer password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Increases/Decreases the amount of cash by adding to the existing cash
	 * 
	 * @param cash the amount of cash to add
	 */
	public void updateCash(double cash) {
		this.cash += cash;
	}

	/**
	 * Adds a new mutual fund to the user's list of funds or updates a fund to
	 * increase the number of shares held
	 * 
	 * @param shares the desired number of shares
	 * @param mf     a new or existing mutual fund
	 * @return whether the fund was added to the customer's account - i.e. the
	 *         customer had sufficient cash to add the MutualFund Decreases the
	 *         amount of cash if purchase made
	 */
	public boolean addFund(double shares, MutualFund mf) {
		NameComparator nameComparator = new NameComparator();
		ValueComparator valueComparator = new ValueComparator();
		if (Double.compare(cash, mf.getPricePerShare()) < 0) {
			return false;
		} else {
			MutualFundAccount fundAccToAdd = new MutualFundAccount(mf, shares);
			MutualFundAccount name_fundAccInBST = funds_name.search(fundAccToAdd, nameComparator);
			if (name_fundAccInBST != null) {
				funds_value.remove(name_fundAccInBST, valueComparator);
				name_fundAccInBST.updateShares(shares);
				funds_value.insert(name_fundAccInBST, valueComparator);
			} else {
				funds_name.insert(fundAccToAdd, nameComparator);
				funds_value.insert(fundAccToAdd, valueComparator);
			}
			updateCash(-(shares * mf.getPricePerShare()));
			return true;
		}
	}

	/**
	 * Sells a Mutual Fund and returns (the price per share times the number of
	 * shares) to cash minus the fee fee is % * price per share * number of shares
	 * 
	 * @param name the name of the fund
	 */
	public void sellFund(String name) {
		MutualFundAccount fundAccToSell = funds_name.search(new MutualFundAccount(new MutualFund(name, "")),
				new NameComparator());
		double pricePerShare = fundAccToSell.getMf().getPricePerShare();
		double shares = fundAccToSell.getNumShares();
		double fee = fundAccToSell.getMf().getTradingFee();
		funds_name.remove(fundAccToSell, new NameComparator());
		funds_value.remove(fundAccToSell, new NameComparator());
		updateCash(pricePerShare * shares - fee * 0.01 * pricePerShare * shares);

	}

	/**
	 * Sells a Mutual Fund and returns (the price per share times the number of
	 * shares) to cash minus the fee fee is % * price per share * number of shares
	 * 
	 * @param name the name of the fund
	 */
	public void sellShares(String name, double shares) {
		MutualFundAccount fundAccToSell = funds_name.search(new MutualFundAccount(new MutualFund(name, "")),
				new NameComparator());
		double pricePerShare = fundAccToSell.getMf().getPricePerShare();
		double fee = fundAccToSell.getMf().getTradingFee();
		if (Double.compare(shares, fundAccToSell.getNumShares()) == 0) {
			funds_name.remove(fundAccToSell, new NameComparator());
			funds_value.remove(fundAccToSell, new NameComparator());
		} else {
			funds_value.remove(fundAccToSell, new ValueComparator());
			fundAccToSell.updateShares(-shares);
			funds_value.insert(fundAccToSell, new ValueComparator());
		}
		updateCash(pricePerShare * shares - fee * 0.01 * pricePerShare * shares);
	}

	/** ADDITIONAL OPERATIONS */

	/**
	 * Creates a String of customer information in the form Name: <firstName>
	 * <lastName> Account Number: <accountNum> Total Cash: $<cash> Note that cash is
	 * formatted $XXX,XXX,XXX.XX
	 */

	@Override
	public String toString() {
		String result = "";
		result += "Name: " + firstName + " " + lastName + "\n";
		result += "Account Number: " + accountNum + "\n";
		result += "Total Cash: $" + new DecimalFormat("###,###.00").format(cash);
		return result;
	}

	/**
	 * Prints out all the customer accounts alphabetized by name
	 */
	public void printAccountsByName() {
		funds_name.inOrderPrint();
	}

	/**
	 * Prints out all the customer accounts in increasing order of value
	 */
	public void printAccountsByValue() {
		funds_value.inOrderPrint();
	}

	/**
	 * Compares this Customer to another Object for equality You must use the
	 * formula presented in class for full credit. (See Lesson 4)
	 * 
	 * @param o another Object
	 * @return true if o is a Customer and has a matching email and password to this
	 *         Customer
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof Customer)) {
			return false;
		} else {
			Customer c = (Customer) o;
			if (this.email.equals(c.email) && this.password.equals(c.password)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a consistent hash code for each Customer by summing the Unicode
	 * values of each character in the key Key = email + password
	 * 
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		String key = email + password;
		int sum = 0;
		for (int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;
	}

}