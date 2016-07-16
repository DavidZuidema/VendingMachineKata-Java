package com.acme.vending;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VendingMachineTest {
	
	private static final CoinType ANOTHER_INVALID_COIN = CoinType.LOONIE;
	private static final CoinType INVALID_COIN = CoinType.PENNY;
	private static final String DEFAULT_VENDING_MACHINE_MESSAGE = "INSERT COIN";
	
	private VendingMachine vendingMachine;
	private CoinDetector coinDetector;

	@Before
	public void setup() {
		coinDetector = new CoinDetector();
		vendingMachine = new VendingMachine(coinDetector);		
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
		insert(CoinType.NICKEL);
		coinReturnIsEmpty();
	}
	
	@Test
	public void whenANickelIsInserted_itDisplaysTheTotalDeposited() {
		insert(CoinType.NICKEL);
		displaysMessage("$0.05");
	}
	
	@Test
	public void whenADimeIsInserted_itDisplaysTheTotalDeposited() {
		insert(CoinType.DIME);
		displaysMessage("$0.10");
	}
	

	
	private void insert(CoinType coin) {
		vendingMachine.insertCoin(coin);
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
