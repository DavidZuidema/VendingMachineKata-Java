package com.acme.vending;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.*;

import javax.swing.SpringLayout.Constraints;

import org.junit.Before;
import org.junit.Test;

public class VendingMachineTest {
	
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
	
	private void checkThatMachineDisplaysMessage(String message) {
		assertEquals(message, vendingMachine.getDisplay());
	}
}
