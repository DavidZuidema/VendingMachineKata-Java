package com.acme.vending;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.Lists;

public class VendingMachineTest {

	private static final CoinType NICKEL = CoinType.NICKEL;
	private static final CoinType DIME = CoinType.DIME;
	private static final CoinType QUARTER = CoinType.QUARTER;
	private static final CoinType ANOTHER_INVALID_COIN = CoinType.LOONIE;
	private static final CoinType INVALID_COIN = CoinType.PENNY;
	
	private static final ProductButtonType A = ProductButtonType.A;
	private static final ProductButtonType B = ProductButtonType.B;
	private static final ProductButtonType C = ProductButtonType.C;
	private static final ProductButtonType D = ProductButtonType.D;

	private static final String INSERT_COIN_MESSAGE = "INSERT COIN";
	private static final String THANK_YOU_MESSAGE = "THANK YOU";
	private static final String OUT_OF_STOCK_MESSAGE = "SOLD OUT";
	
	private VendingMachine vendingMachine;
	private CoinDetector coinDetector;
	private CoinChanger coinChanger;

	@Before
	public void setup() {
		setupMockCoinDetector();
		setupMockCoinChanger();
		vendingMachine = new VendingMachine(coinDetector, coinChanger);
		vendingMachine.addProduct(A, new Product("Cola", 100, 1));
		vendingMachine.addProduct(B, new Product("Chips", 50, 1));
		vendingMachine.addProduct(C, new Product("Candy", 65, 1));
		vendingMachine.addProduct(D, new Product("Gum", 25, 0));
	}

	private void setupMockCoinChanger() {
		coinChanger = mock(CoinChanger.class);
		when(coinChanger.makeChange(40)).thenReturn(Lists.newArrayList(QUARTER,DIME,NICKEL));
	}

	private void setupMockCoinDetector() {
		coinDetector = mock(CoinDetector.class);
		when(coinDetector.isValidCoin(INVALID_COIN)).thenReturn(false);
		when(coinDetector.isValidCoin(ANOTHER_INVALID_COIN)).thenReturn(false);
		when(coinDetector.isValidCoin(NICKEL)).thenReturn(true);
		when(coinDetector.isValidCoin(DIME)).thenReturn(true);
		when(coinDetector.isValidCoin(QUARTER)).thenReturn(true);
		when(coinDetector.getCoinValueInCents(NICKEL)).thenReturn(5);
		when(coinDetector.getCoinValueInCents(DIME)).thenReturn(10);
		when(coinDetector.getCoinValueInCents(QUARTER)).thenReturn(25);
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
	
	@Test
	public void whenReturnCoinsIsPushed_allFundsAreSentToTheCoinReturn() {
		insert(QUARTER, NICKEL, DIME);
		vendingMachine.returnCoins();
		coinReturnContains(QUARTER, DIME, NICKEL);
		displaysMessage(INSERT_COIN_MESSAGE);
	}
	
	@Test
	public void whenSelectingAnItemNotInStock_displaysOutOfStockMessage() throws Exception {
		pushingButtonDisplaysMessage(D, OUT_OF_STOCK_MESSAGE);
		displaysMessage(INSERT_COIN_MESSAGE);
	}
	
	@Test
	public void whenSelectingAnItemNotInStock_afterInsertingCoins_displaysOutOfStockMessage() throws Exception {
		insert(QUARTER);
		pushingButtonDisplaysMessage(D, OUT_OF_STOCK_MESSAGE);
		displaysMessage("$0.25");
	}
	
	@Test
	@Ignore
	public void whenBuyingTheLastItem_selectingItAgainDisplaysSoldOut() throws Exception {
		insert(QUARTER, QUARTER, QUARTER, QUARTER);
		pushingButtonDisplaysMessage(A, THANK_YOU_MESSAGE);
		displaysMessage(INSERT_COIN_MESSAGE);
		pushingButtonDisplaysMessage(A, OUT_OF_STOCK_MESSAGE);	
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
		vendingMachine.pushProductButton(button);
		displaysMessage(displayMessage);
	}

	private void coinReturnContains(CoinType...coinTypes) {
		assertThat(vendingMachine.getCoinsInCoinReturn(), contains(coinTypes));
	}
	
	private void coinReturnIsEmpty() {
		assertThat(vendingMachine.getCoinsInCoinReturn(), is(empty()));
	}
	
}
