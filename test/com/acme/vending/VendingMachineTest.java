package com.acme.vending;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VendingMachineTest {

	private static final CoinType NICKEL = CoinType.NICKEL;
	private static final CoinType DIME = CoinType.DIME;
	private static final CoinType QUARTER = CoinType.QUARTER;
	private static final CoinType ANOTHER_INVALID_COIN = CoinType.LOONIE;
	private static final CoinType INVALID_COIN = CoinType.PENNY;
	
	private static final ProductButtonType A = ProductButtonType.A;
	private static final ProductButtonType B = ProductButtonType.B;
	private static final ProductButtonType C = ProductButtonType.C;

	private static final String INSERT_COIN_MESSAGE = "INSERT COIN";
	private static final String THANK_YOU_MESSAGE = "THANK YOU";
	
	private VendingMachine vendingMachine;
	private CoinDetector coinDetector;

	@Before
	public void setup() {
		coinDetector = new CoinDetector();
		vendingMachine = new VendingMachine(coinDetector);
		vendingMachine.addProduct(A, new Product("Cola", 100));
		vendingMachine.addProduct(B, new Product("Chips", 50));
		vendingMachine.addProduct(C, new Product("Candy", 65));
	}

	@Test
	public void whenNoCoinsHaveBeenInserted_itDisplaysTheDefaultMessage() {
		displaysMessage(INSERT_COIN_MESSAGE);
	}
	
	@Test
	public void whenAnInvalidCoinHasBeenInserted_itDisplaysTheDefaultMessage() {
		insert(INVALID_COIN);
		displaysMessage(INSERT_COIN_MESSAGE);
	}
	
	@Test
	public void whenOneInvalidCoinIsInserted_itAddsThatCoinToTheCoinReturn() {
		insert(INVALID_COIN);
		coinReturnContains(INVALID_COIN);
	}
	
	@Test
	public void whenTwoInvalidCoinsAreInserted_itAddsBothCoinsToTheCoinReturn() {
		insert(INVALID_COIN);
		insert(ANOTHER_INVALID_COIN);
		coinReturnContains(INVALID_COIN, ANOTHER_INVALID_COIN);
	}
	
	@Test
	public void whenValidCoinsAreInserted_theyAreNotAddedToTheCoinReturn() {
		insert(NICKEL, DIME, QUARTER);
		coinReturnIsEmpty();
	}
	
	@Test
	public void whenValidCoinsAreInserted_itDisplaysTheTotalDeposited() {
		insertingCoinsDisplayValue("$0.05", NICKEL);
		insertingCoinsDisplayValue("$0.10", DIME);
		insertingCoinsDisplayValue("$0.15", NICKEL, DIME);
		insertingCoinsDisplayValue("$0.25", QUARTER);
		insertingCoinsDisplayValue("$0.40", NICKEL, DIME, QUARTER);		
		insertingCoinsDisplayValue("$1.00", QUARTER, QUARTER, QUARTER, QUARTER);
		insertingCoinsDisplayValue("$1.05", QUARTER, QUARTER, QUARTER, QUARTER, NICKEL);
	}
	
	@Test
	public void whenAProductIsSelected_andNoCoinsWereDeposited_itDisplaysThePrice_thenTheDefaultValue() {
		pushingButtonDisplaysMessage(A, "PRICE $1.00");
		pushingButtonDisplaysMessage(B, "PRICE $0.50");
		pushingButtonDisplaysMessage(C, "PRICE $0.65");
		displaysMessage(INSERT_COIN_MESSAGE);
	}

	@Test
	public void whenAProductIsSelected_andInsufficientFundsWereDeposited_itDisplaysThePrice_thenTheTotalInserted() {
		insert(QUARTER, QUARTER, QUARTER);
		pushingButtonDisplaysMessage(A, "PRICE $1.00");
		displaysMessage("$0.75");
	}
	
	@Test
	public void whenAProductIsSelected_andSufficientFundsWereDeposited_itSaysThankYou() {
		insert(QUARTER, QUARTER, QUARTER, QUARTER);
		pushingButtonDisplaysMessage(A, THANK_YOU_MESSAGE);
		displaysMessage(INSERT_COIN_MESSAGE);
	}

	private void insertingCoinsDisplayValue(String valueDisplay, CoinType...coins) {
		setup();
		insert(coins);
		displaysMessage(valueDisplay);
	}
	
	private void insert(CoinType...coins) {
		for (CoinType coin : coins) {
			vendingMachine.insertCoin(coin);
		}
	}
	
	private void displaysMessage(String message) {
		assertEquals(message, vendingMachine.getDisplay());
	}
	
	private void pushingButtonDisplaysMessage(ProductButtonType button, String displayMessage) {
		assertEquals(displayMessage, vendingMachine.pushProductButton(button));
	}

	private void coinReturnContains(CoinType...coinTypes) {
		assertEquals(coinTypes.length, vendingMachine.getCoinsInCoinReturn().size());
		assertThat(vendingMachine.getCoinsInCoinReturn(), hasItems(coinTypes));
	}
	
	private void coinReturnIsEmpty() {
		assertEquals(0, vendingMachine.getCoinsInCoinReturn().size());
	}
	
}
