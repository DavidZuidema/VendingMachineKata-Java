package com.acme.vending;

public class VendingMachine {
	
	private CoinDetector coinDetector;

	public VendingMachine(CoinDetector coinDetector) {
		this.coinDetector = coinDetector;
	}

	public String getDisplay() {
		return "INSERT COIN";
	}
}
