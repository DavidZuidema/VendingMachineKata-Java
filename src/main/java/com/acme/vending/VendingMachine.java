package com.acme.vending;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class VendingMachine {
	
	private static final String DEFAULT_MESSAGE = "INSERT COIN";
	private static final String THANK_YOU_MESSAGE = "THANK YOU";
	private static final String OUT_OF_STOCK_MESSAGE = "SOLD OUT";
	
	private CoinDetector coinDetector;
	private CoinChanger coinChanger;
	
	private int fundsDepositedInCents = 0;
	private ArrayList<CoinType> coinReturn  = new ArrayList<CoinType>();
	private Map<ProductButtonType, Product> products = new TreeMap<ProductButtonType, Product>();
	private String flashDisplayMessage = null;
	
	public VendingMachine(CoinDetector coinDetector, CoinChanger coinChanger) {
		this.coinDetector = coinDetector;
		this.coinChanger = coinChanger;
	}

	public String getDisplay() {
		if (flashDisplayMessage != null) {
			String message = flashDisplayMessage;
			flashDisplayMessage = null;
			return message;
		}
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

	public void pushProductButton(ProductButtonType button) {
		if (products.containsKey(button)) {
			displayOneTimeMessage(selectProduct(products.get(button)));
		}
	}

	private String selectProduct(Product product) {
		if (!product.isInStock()) {
			return OUT_OF_STOCK_MESSAGE;
		}
		
		if (fundsDepositedInCents < product.getPriceInCents()) {
			return "PRICE " + renderDollarAmountFromCents(product.getPriceInCents());
		}
		
		dispenseProduct(product);
		fundsDepositedInCents = 0;
		return THANK_YOU_MESSAGE;
	}

	private void dispenseProduct(Product product) {
		//Placeholder
	}
	
	private void displayOneTimeMessage(String flashMessage) {
		flashDisplayMessage = flashMessage;
	}

	public void addProduct(ProductButtonType button, Product product) {
		products.put(button, product);
	}

	public void returnCoins() {
		coinReturn.addAll(coinChanger.makeChange(fundsDepositedInCents));
		fundsDepositedInCents = 0;
	}

}
