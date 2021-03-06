
/**
 * MutualFundAccount.java
 * @author Naqib Khan
 * @author Pratik Bhandari
 * CIS 22C, Lab 6
 */

import java.text.DecimalFormat;
import java.util.Comparator;

public class MutualFundAccount {
	private double numShares;
	private MutualFund mf;

	private static int accountSeed = 100000000;

	/** CONSTRUCTORS */

	/**
	 * One-argument constructor
	 * 
	 * @param mf the mutual fund for this account
	 */
	public MutualFundAccount(MutualFund mf) {
		this.numShares = 0;
		this.mf = new MutualFund(mf.getFundName(), mf.getTicker(), mf.getPricePerShare(), mf.getTradingFee());
	}

	/**
	 * Two-argument constructor
	 * 
	 * @param numShares total shares of the mutual fund
	 * @param mf        the mutual fund
	 */
	public MutualFundAccount(double numShares, MutualFund mf) {
		this.numShares = numShares;
		this.mf = new MutualFund(mf.getFundName(), mf.getTicker(), mf.getPricePerShare(), mf.getTradingFee());
	}

	/**
	 * Two-argument constructor
	 * 
	 * @param mf        the mutual fund
	 * @param numShares total shares of the mutual fund
	 */
	public MutualFundAccount(MutualFund mf, double numShares) {
		this.mf = new MutualFund(mf.getFundName(), mf.getTicker(), mf.getPricePerShare(), mf.getTradingFee());
		this.numShares = numShares;
	}

	/** ACCESSORS */

	/**
	 * Accesses the total number of shares
	 * 
	 * @return the total shares
	 */
	public double getNumShares() {
		return numShares;
	}

	/**
	 * Accesses the mutual fund
	 * 
	 * @return the mutual fund
	 */
	public MutualFund getMf() {
		return mf;
	}

	/**
	 * Increments the account seed and returns the new value
	 * 
	 * @return the incremented account seed
	 */
	public static int getAccountSeed() {
		return ++accountSeed;
	}

	/** MUTATORS */

	/**
	 * Increases/Decreases the total shares by the given amount
	 * 
	 * @param numShares the amount to increase or decrease
	 */
	public void updateShares(double numShares) {
		this.numShares += numShares;
	}

	/**
	 * Creates a String of the mutual fund information along with the following:
	 * 
	 * Total Shares: <numShares> Value: $<numShares>*<pricePerShare>
	 */
	@Override
	public String toString() {
		String result = "\n";
		result += mf.toString();
		result += "\n\nTotal Shares: " + numShares + "\n";
		result += "Value: $" + new DecimalFormat("###,###.00").format(numShares * mf.getPricePerShare());
		result += "\n";
		return result;
	}

}

class NameComparator implements Comparator<MutualFundAccount> {
	/**
	 * Compares the two mutual fund accounts by name of the fund uses the String
	 * compareTo method to make the comparison
	 * 
	 * @param account1 the first MutualFundAccount
	 * @param account2 the second MutualFundAccount
	 */
	@Override
	public int compare(MutualFundAccount account1, MutualFundAccount account2) {
		return account1.getMf().getFundName().compareTo(account2.getMf().getFundName());
	}
} // end class NameComparator

class ValueComparator implements Comparator<MutualFundAccount> {
	/**
	 * Compares the two mutual fund accounts by total value determines total value
	 * as number of shares times price per share uses the static Double compare
	 * method to make the comparison
	 * 
	 * @param account1 the first MutualFundAccount
	 * @param account2 the second MutualFundAccount
	 */
	@Override
	public int compare(MutualFundAccount account1, MutualFundAccount account2) {
		double acc1Val = account1.getNumShares() * account1.getMf().getPricePerShare();
		double acc2Val = account2.getNumShares() * account2.getMf().getPricePerShare();
		return Double.compare(acc1Val, acc2Val);
	}
}