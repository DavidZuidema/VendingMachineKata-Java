package com.acme.vending;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class VendingMachine {
	
	private static final String DEFAULT_MESSAGE = "INSERT COIN";
	private static final String THANK_YOU_MESSAGE = "THANK YOU";
	
	private CoinDetector coinDetector;
	private CoinChanger coinChanger;
	
	private int fundsDepositedInCents = 0;
	private ArrayList<CoinType> coinReturn  = new ArrayList<CoinType>();
	private Map<ProductButtonType, Product> products = new TreeMap<ProductButtonType, Product>();

	public VendingMachine(CoinDetector coinDetector, CoinChanger coinChanger) {
		this.coinDetector = coinDetector;
		this.coinChanger = coinChanger;
	}

	public String getDisplay() {
		if (fundsDepositedInCents == 0) {
			return DEFAULT_MESSAGE;
		}
		return renderDollarAmountFromCents(fundsDepositedInCents);
	}
	
	private String renderDollarAmountFromCents(int valueInCents) {
		int cents = valueInCents % 100;
		int dollars = (valueInCents - cents) / 100;
		return String.format("$%01d.%02d", dollars, cents);
	}

	public void insertCoin(CoinType coin) {
		if (coinDetector.isValidCoin(coin)) {
			fundsDepositedInCents += coinDetector.getCoinValueInCents(coin);
		} else {
			coinReturn.add(coin);
		}
	}

	public List<CoinType> getCoinsInCoinReturn() {
		return coinReturn;
	}

	public String pushProductButton(ProductButtonType button) {
		if (products.containsKey(button)) {
			return selectProduct(products.get(button));
		} else {
			return DEFAULT_MESSAGE;
		}
	}

	private String selectProduct(Product product) {
		if (fundsDepositedInCents < product.getPriceInCents()) {
			return "PRICE " + renderDollarAmountFromCents(product.getPriceInCents());
		} else {
			dispenseProduct(product);
			fundsDepositedInCents = 0;
			return THANK_YOU_MESSAGE;
		}
	}

	private void dispenseProduct(Product product) {
		//Placeholder
	}

	public void addProduct(ProductButtonType button, Product product) {
		products.put(button, product);
	}

	public void returnCoins() {
		coinReturn.addAll(coinChanger.makeChange(fundsDepositedInCents));
		fundsDepositedInCents = 0;
	}

}
