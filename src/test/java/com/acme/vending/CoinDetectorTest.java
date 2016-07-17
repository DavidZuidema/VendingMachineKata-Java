package com.acme.vending;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class CoinDetectorTest {
	
	private static final CoinType PENNY = CoinType.PENNY;
	private static final CoinType NICKEL = CoinType.NICKEL;
	private static final CoinType DIME = CoinType.DIME;
	private static final CoinType QUARTER = CoinType.QUARTER;
	
	private CoinDetector coinDetector;

	@Before
	public void setup() {
		coinDetector = new CoinDetector();		
	}

	@Test
	public void itIdentifiesInvalidCoinsCorrectly() {
		isInvalidCoin(PENNY);
	}
	
	@Test
	public void itIdentifiesValidCoinsCorrectly() {
		isValidCoin(NICKEL);
		isValidCoin(DIME);
		isValidCoin(QUARTER);
	}

	@Test
	public void itDeterminesTheCorrectMonetaryValueForCoins() {
		coinHasValue(NICKEL, 5);
		coinHasValue(DIME, 10);
		coinHasValue(QUARTER, 25);
	}
	
	private void coinHasValue(CoinType coin, int expectedValueInCents) {
		assertEquals(expectedValueInCents, coinDetector.getCoinValueInCents(coin));
	}

	private void isValidCoin(CoinType coin) {
		assertTrue(coinDetector.isValidCoin(coin));
	}
	
	private void isInvalidCoin(CoinType coin) {
		assertFalse(coinDetector.isValidCoin(coin));
	}
	
}
