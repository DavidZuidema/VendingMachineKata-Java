package com.acme.vending;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VendingMachineTest {
	
	private VendingMachine vendingMachine;

	@Before
	public void setup() {
		vendingMachine = new VendingMachine();		
	}

	@Test
	public void itIdentifiesInvalidCoinsCorrectly() {
		assertFalse(vendingMachine.isValidCoin(CoinType.PENNY));
	}
	
	@Test
	public void itIdentifiesValidCoinsCorrectly() {
		assertTrue(vendingMachine.isValidCoin(CoinType.NICKEL));
		assertTrue(vendingMachine.isValidCoin(CoinType.DIME));
		assertTrue(vendingMachine.isValidCoin(CoinType.QUARTER));
	}

	@Test
	public void itDeterminesTheCorrectMonetaryValueForCoins() {
		assertEquals(5, vendingMachine.getCoinValueInCents(CoinType.NICKEL));
		assertEquals(10, vendingMachine.getCoinValueInCents(CoinType.DIME));
	}
}
