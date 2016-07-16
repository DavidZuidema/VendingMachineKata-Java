package com.acme.vending;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
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
		checkThatMachineDisplaysMessage(DEFAULT_VENDING_MACHINE_MESSAGE);
	}
	
	@Test
	public void whenAnInvalidCoinHasBeenInserted_itDisplaysTheDefaultMessage() {
		vendingMachine.insertCoin(INVALID_COIN);
		checkThatMachineDisplaysMessage(DEFAULT_VENDING_MACHINE_MESSAGE);
	}
	
	@Test
	public void whenOneInvalidCoinHasBeenInserted_itAddsThatCoinToTheCoinReturn() {
		vendingMachine.insertCoin(INVALID_COIN);
		assertThat(vendingMachine.getCoinsInCoinReturn(), hasItem(INVALID_COIN));
	}
	
	@Test
	public void whenTwoInvalidCoinsHaveBeenInserted_itAddsBothCoinToTheCoinReturn() {
		vendingMachine.insertCoin(INVALID_COIN);
		vendingMachine.insertCoin(ANOTHER_INVALID_COIN);
		assertEquals(2, vendingMachine.getCoinsInCoinReturn().size());
		assertThat(vendingMachine.getCoinsInCoinReturn(), hasItems(INVALID_COIN, ANOTHER_INVALID_COIN));
	}
	
	@Test
	public void whenANickelIsInserted_itIsNotAddedToTheCoinReturn() {
		vendingMachine.insertCoin(CoinType.NICKEL);
		assertEquals(0, vendingMachine.getCoinsInCoinReturn().size());
	}
	
	@Test
	public void whenANickelIsInserted_itDisplaysTheTotalDeposited() {
		vendingMachine.insertCoin(CoinType.NICKEL);
		checkThatMachineDisplaysMessage("$0.05");
	}
	
	private void checkThatMachineDisplaysMessage(String message) {
		assertEquals(message, vendingMachine.getDisplay());
	}
}
