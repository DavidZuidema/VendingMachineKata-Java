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
		return renderValueInCents(totalInsertedInCents);
	}
	
	private String renderValueInCents(int valueInCents) {
		int cents = valueInCents % 100;
		int dollars = (valueInCents - cents) / 100;
		return String.format("$%01d.%02d", dollars, cents);
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

	public String pushProductButton(String button) {
		return "PRICE $1.00";
	}

}
