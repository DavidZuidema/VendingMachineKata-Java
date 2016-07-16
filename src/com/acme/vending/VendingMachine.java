package com.acme.vending;

import java.util.List;

public class VendingMachine {
	
	private CoinDetector coinDetector;

	public VendingMachine(CoinDetector coinDetector) {
		this.coinDetector = coinDetector;
	}

	public String getDisplay() {
		return "INSERT COIN";
	}

	public void insertCoin(CoinType coin) {
		
	}

	public List<CoinType> getCoinsInCoinReturn() {
		return null;
	}
}
