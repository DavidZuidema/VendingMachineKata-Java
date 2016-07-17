package com.acme.vending;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.Contains;

import com.google.common.collect.Lists;

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
		assertThat(coinChanger.makeChange(5), hasItem(NICKEL));
		assertThat(coinChanger.makeChange(10), hasItem(DIME));
		assertThat(coinChanger.makeChange(25), hasItem(QUARTER));
	}
	
	@Test
	public void whenMakingChangeForFiftyCents_returnsLargestDenominationsAvailable() {
		makeChangeForAmountReturnCoins(50, QUARTER, QUARTER);
	}
	
	@Test
	public void whenMakingChangeForTwentyCents_returnsLargestDenominationsAvailable() {
		makeChangeForAmountReturnCoins(20, DIME, DIME);
	}
	
	private void makeChangeForAmountReturnCoins(int amountInCents, CoinType...coinsReturned) {
		assertThat(coinChanger.makeChange(amountInCents), contains(coinsReturned));
	}
	
	@Test
	public void whenMakingChangeForFifteenCents_returnsMultipleDenominations() {
		Collection<CoinType> coins = coinChanger.makeChange(15);
		assertEquals(2, coins.size());
		assertThat(coins, hasItems(DIME, NICKEL));
		assertThat(coins, not(hasItems(QUARTER)));
	}
	
	@Test
	public void whenMakingChangeForSeventyCents_returnsMultipleDenominations() {
		Collection<CoinType> coins = coinChanger.makeChange(70);
		assertEquals(4, coins.size());
		assertThat(coins, hasItems(DIME, QUARTER));
		assertThat(coins, not(hasItems(NICKEL)));
	}
}
