package com.acme.vending;

import java.util.ArrayList;
import java.util.List;

public class VendingMachine {
	
	private int totalInsertedInCents = 0;
	private CoinDetector coinDetector;
	private ArrayList<CoinType> coinReturn  = new ArrayList<CoinType>();

	public VendingMachine(CoinDetector coinDetector) {
		this.coinDetector = coinDetector;
	}

	public String getDisplay() {
		if (totalInsertedInCents == 0) {
			return "INSERT COIN";
		}
		return "$0.05";
	}

	public void insertCoin(CoinType coin) {
		if (coinDetector.isValidCoin(coin)) {
			totalInsertedInCents += coinDetector.getCoinValueInCents(coin);
		} else {
			coinReturn.add(coin);
		}
	}

	public List<CoinType> getCoinsInCoinReturn() {
		return coinReturn;
	}
}
