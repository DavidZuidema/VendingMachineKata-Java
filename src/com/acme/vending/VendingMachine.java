package com.acme.vending;

public class VendingMachine {
	public boolean isValidCoin(String coin) {
		if (coin.equals("nickel")) {
			return true;
		}
		return false;
	}
}
