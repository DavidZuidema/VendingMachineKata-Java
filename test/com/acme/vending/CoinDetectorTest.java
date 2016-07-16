package com.acme.vending;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class CoinDetectorTest {
	
	private CoinDetector coinDetector;

	@Before
	public void setup() {
		coinDetector = new CoinDetector();		
	}

	@Test
	public void itIdentifiesInvalidCoinsCorrectly() {
		assertFalse(coinDetector.isValidCoin(CoinType.PENNY));
	}
	
	@Test
	public void itIdentifiesValidCoinsCorrectly() {
		assertTrue(coinDetector.isValidCoin(CoinType.NICKEL));
		assertTrue(coinDetector.isValidCoin(CoinType.DIME));
		assertTrue(coinDetector.isValidCoin(CoinType.QUARTER));
	}

	@Test
	public void itDeterminesTheCorrectMonetaryValueForCoins() {
		assertEquals(5, coinDetector.getCoinValueInCents(CoinType.NICKEL));
		assertEquals(10, coinDetector.getCoinValueInCents(CoinType.DIME));
		assertEquals(25, coinDetector.getCoinValueInCents(CoinType.QUARTER));
	}
	
}
