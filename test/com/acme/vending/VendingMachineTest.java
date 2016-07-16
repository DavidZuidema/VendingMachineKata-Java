package com.acme.vending;

import static org.junit.Assert.*;

import org.junit.Test;

public class VendingMachineTest {

	@Test
	public void itIdentifiesInvalidCoinsCorrectly() {
		VendingMachine vendingMachine = new VendingMachine();
		assertFalse(vendingMachine.isValidCoin("penny"));
	}
	

	@Test
	public void itIdentifiesValidCoinsCorrectly() {
		VendingMachine vendingMachine = new VendingMachine();
		assertTrue(vendingMachine.isValidCoin("nickel"));
	}

}
