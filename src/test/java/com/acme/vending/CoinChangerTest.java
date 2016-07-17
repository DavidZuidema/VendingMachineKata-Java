package com.acme.vending;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CoinChangerTest {

	private static final CoinType NICKEL = CoinType.NICKEL;
	private static final CoinType DIME = CoinType.DIME;
	private static final CoinType QUARTER = CoinType.QUARTER;

	private CoinChanger coinChanger;

	@Before
	public void setup() {
		coinChanger = new CoinChanger();
	}
	
	@Test
	public void whenMakingChangeForSingleCoins_returnsSameCoin() {
		makeChangeForAmountReturnCoins(5, NICKEL);
		makeChangeForAmountReturnCoins(10, DIME);
		makeChangeForAmountReturnCoins(25, QUARTER);
	}
	
	@Test
	public void whenMakingChange_returnsLargestDenominationsAvailable() {
		makeChangeForAmountReturnCoins(50, QUARTER, QUARTER);
		makeChangeForAmountReturnCoins(20, DIME, DIME);
	}
	
	@Test
	public void whenMakingChange_returnsMultipleDenominations() {
		makeChangeForAmountReturnCoins(15, DIME, NICKEL);
		makeChangeForAmountReturnCoins(70, QUARTER, QUARTER, DIME, DIME);
	}
	
	private void makeChangeForAmountReturnCoins(int amountInCents, CoinType...coinsReturned) {
		assertThat(coinChanger.makeChange(amountInCents), contains(coinsReturned));
	}
}
