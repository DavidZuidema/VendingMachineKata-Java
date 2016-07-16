package com.acme.vending;

public class VendingMachine {
	public boolean isValidCoin(CoinType coin) {
		switch (coin) {
		case DIME:
		case NICKEL:
		case QUARTER:
			return true;
		default:
			return false;
		}
	}

	public int getCoinValueInCents(CoinType coin) {
		if (coin.equals(CoinType.NICKEL)) {
			return 5;			
		}
		return 10;
	}
}
