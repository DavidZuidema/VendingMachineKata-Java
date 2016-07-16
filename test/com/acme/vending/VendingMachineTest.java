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

	private static final String DEFAULT_VENDING_MACHINE_MESSAGE = "INSERT COIN";
	
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
		displaysMessage(DEFAULT_VENDING_MACHINE_MESSAGE);
	}
	
	@Test
	public void whenAnInvalidCoinHasBeenInserted_itDisplaysTheDefaultMessage() {
		insert(INVALID_COIN);
		displaysMessage(DEFAULT_VENDING_MACHINE_MESSAGE);
	}
	
	@Test
	public void whenOneInvalidCoinHasBeenInserted_itAddsThatCoinToTheCoinReturn() {
		insert(INVALID_COIN);
		coinReturnContains(INVALID_COIN);
	}
	
	@Test
	public void whenTwoInvalidCoinsHaveBeenInserted_itAddsBothCoinsToTheCoinReturn() {
		insert(INVALID_COIN);
		insert(ANOTHER_INVALID_COIN);
		coinReturnContains(INVALID_COIN, ANOTHER_INVALID_COIN);
	}
	
	@Test
	public void whenANickelIsInserted_itIsNotAddedToTheCoinReturn() {
		insert(NICKEL);
		coinReturnIsEmpty();
	}
	
	@Test
	public void whenANickelIsInserted_itDisplaysTheTotalDeposited() {
		insertedCoinsDisplayValue("$0.05", NICKEL);
	}
	
	@Test
	public void whenADimeIsInserted_itDisplaysTheTotalDeposited() {
		insertedCoinsDisplayValue("$0.10", DIME);
	}
	
	@Test
	public void whenValidCoinsAreInserted_itDisplaysTheTotalDeposited() {
		insertedCoinsDisplayValue("$0.15", NICKEL, DIME);
		insertedCoinsDisplayValue("$0.25", QUARTER);
		insertedCoinsDisplayValue("$0.40", NICKEL, DIME, QUARTER);		
		insertedCoinsDisplayValue("$1.00", QUARTER, QUARTER, QUARTER, QUARTER);
		insertedCoinsDisplayValue("$1.05", QUARTER, QUARTER, QUARTER, QUARTER, NICKEL);
	}
	
	@Test
	public void whenAProductIsSelected_andNoCoinsWereDeposited_itDisplaysThePrice() {
		assertEquals("PRICE $1.00", vendingMachine.pushProductButton(A));
		assertEquals("PRICE $0.50", vendingMachine.pushProductButton(B));
		assertEquals("PRICE $0.65", vendingMachine.pushProductButton(C));
		displaysMessage(DEFAULT_VENDING_MACHINE_MESSAGE);
	}
	
	@Test
	public void whenAProductIsSelected_andInsufficientFundsWereDeposited_itDisplaysThePriceThenTheTotalInserted() {
		insert(QUARTER, QUARTER, QUARTER);
		assertEquals("PRICE $1.00", vendingMachine.pushProductButton(A));
		displaysMessage("$0.75");
	}
	
	@Test
	public void whenAProductIsSelected_andSufficientFundsWereDeposited_itSaysThankYou() {
		insert(QUARTER, QUARTER, QUARTER, QUARTER);
		assertEquals("THANK YOU", vendingMachine.pushProductButton(A));
		displaysMessage(DEFAULT_VENDING_MACHINE_MESSAGE);
	}

	private void insertedCoinsDisplayValue(String valueDisplay, CoinType...coins) {
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

	private void coinReturnContains(CoinType...coinTypes) {
		assertEquals(coinTypes.length, vendingMachine.getCoinsInCoinReturn().size());
		assertThat(vendingMachine.getCoinsInCoinReturn(), hasItems(coinTypes));
	}
	
	private void coinReturnIsEmpty() {
		assertEquals(0, vendingMachine.getCoinsInCoinReturn().size());
	}
}
