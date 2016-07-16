package com.acme.vending;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.*;

import javax.swing.SpringLayout.Constraints;

import org.junit.Before;
import org.junit.Test;

public class VendingMachineTest {
	
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
		assertEquals(DEFAULT_VENDING_MACHINE_MESSAGE, vendingMachine.getDisplay());
	}
	
	@Test
	public void whenAnInvalidCoinHasBeenInserted_itDisplaysTheDefaultMessage() {
		vendingMachine.insertCoin(CoinType.PENNY);
		assertEquals(DEFAULT_VENDING_MACHINE_MESSAGE, vendingMachine.getDisplay());
	}
	
	@Test
	public void whenOneInvalidCoinHasBeenInserted_itAddsThatCoinToTheCoinReturn() {
		vendingMachine.insertCoin(CoinType.PENNY);
		assertThat(vendingMachine.getCoinsInCoinReturn(), hasItem(CoinType.PENNY));
	}
}
