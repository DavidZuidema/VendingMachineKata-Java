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
		switch (coin) {
		case DIME:
			return 10;
		case NICKEL:
			return 5;
		case QUARTER:
			return 25;
		default:
			//Invalid coins have no value in the system
			return 0;
		}
	}
}
