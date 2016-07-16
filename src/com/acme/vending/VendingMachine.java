package com.acme.vending;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
	
	private CoinDetector coinDetector;
	private ArrayList<CoinType> coinReturn  = new ArrayList<CoinType>();

	public VendingMachine(CoinDetector coinDetector) {
		this.coinDetector = coinDetector;
	}

	public String getDisplay() {
		return "INSERT COIN";
	}

	public void insertCoin(CoinType coin) {
		if (!coinDetector.isValidCoin(coin)) {
			coinReturn.add(coin);
		}
	}

	public List<CoinType> getCoinsInCoinReturn() {
		return coinReturn;
	}
}
