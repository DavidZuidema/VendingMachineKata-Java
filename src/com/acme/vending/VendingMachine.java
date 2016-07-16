package com.acme.vending;

public class VendingMachine {
	public boolean isValidCoin(CoinType coin) {
		if (coin.equals(CoinType.NICKEL)) {
			return true;
		}
		return false;
	}
}
