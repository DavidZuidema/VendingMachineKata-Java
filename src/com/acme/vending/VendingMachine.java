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
}
