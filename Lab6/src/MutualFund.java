
/**
 * MutualFund.java
 * @author Naqib Khan
 * @author Pratik Bhandari
 * CIS 22C, Lab 6
 */

import java.text.DecimalFormat;

public class MutualFund {
	private final String fundName;
	private final String ticker;
	private double pricePerShare;
	private double tradingFee;

	/** CONSTRUCTORS */

	/**
	 * One argument constructor, when only ticker is known
	 * 
	 * @param ticker the ticker symbol sets fundName to "No name" and assigns 0 to
	 *               pricePerShare and tradingFee
	 */
	public MutualFund(String ticker) {
		this.fundName = "No name";
		this.pricePerShare = 0;
		this.tradingFee = 0;
		this.ticker = ticker;
	}

	/**
	 * One argument constructor, when only name and ticker are known
	 * 
	 * @param name   the name of the fund
	 * @param ticker the ticker symbol Assigns 0 to pricePerShare and tradingFee
	 */
	public MutualFund(String name, String ticker) {
		this.fundName = name;
		this.ticker = ticker;
		this.pricePerShare = 0;
		this.tradingFee = 0;
	}

	/**
	 * Multi-argument constructor when all MutualFund information is known
	 * 
	 * @param fundName      the name of the fund
	 * @param ticker        the ticker symbol
	 * @param pricePerShare the price per share
	 * @param tradingFee    the trading fee as a percent
	 */
	public MutualFund(String fundName, String ticker, double pricePerShare, double tradingFee) {
		this.fundName = fundName;
		this.ticker = ticker;
		this.pricePerShare = pricePerShare;
		this.tradingFee = tradingFee;
	}

	/** ACCESSORS */

	/**
	 * Accesses the name of the fund
	 * 
	 * @return the fund name
	 */
	public String getFundName() {
		return fundName;
	}

	/**
	 * Accesses the ticker symbol
	 * 
	 * @return the ticker symbol
	 */
	public String getTicker() {
		return ticker;
	}

	/**
	 * Accesses the price per share
	 * 
	 * @return the price per share
	 */
	public double getPricePerShare() {
		return pricePerShare;
	}

	/**
	 * Accesses the trading fee
	 * 
	 * @return the trading fee
	 */
	public double getTradingFee() {
		return tradingFee;
	}

	/** MUTATORS */

	/**
	 * Updates the share price
	 * 
	 * @param pricePerShare the new share price
	 */
	public void setPricePerShare(double pricePerShare) {
		this.pricePerShare = pricePerShare;
	}

	/**
	 * Updates the trading fee
	 * 
	 * @param tradingFee the new trading fee
	 */
	public void setTradingFee(double tradingFee) {
		this.tradingFee = tradingFee;
	}

	/** ADDITIONAL OPERATIONS */

	@Override
	public String toString() {
		String result = "";
		result += fundName + "\n";
		result += ticker + "\n";
		result += "Share Price: $" + new DecimalFormat("###,###.00").format(pricePerShare) + "\n";
		result += "Trading Fee: " + new DecimalFormat("##0.00").format(tradingFee) + "%";
		return result;
	}

	/**
	 * Compares this MutualFund to another Object for equality You must use the
	 * formula presented in class for full credit (see Lesson 4)
	 * 
	 * @param o another Object (MutualFund or otherwise)
	 * @return whether o is a MutualFund and has the same ticker as this MutualFund
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof MutualFund)) {
			return false;
		} else {
			MutualFund mf = (MutualFund) o;
			if (this.ticker.equals(mf.ticker)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a consistent hash code for each MutualFund by summing the Unicode
	 * values of each character in the key Key = ticker
	 * 
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		String key = ticker;
		int sum = 0;
		for (int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;
	}
}