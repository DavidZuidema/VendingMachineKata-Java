package com.acme.vending;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class CoinChangerTest {

	private CoinChanger coinChanger;

	@Before
	public void setup() {
		coinChanger = new CoinChanger();
	}
	
	@Test
	public void whenMakingChangeForSingleCoins_returnsSameCoin() {
		assertThat(coinChanger.makeChange(5), hasItem(CoinType.NICKEL));
		assertThat(coinChanger.makeChange(10), hasItem(CoinType.DIME));
		assertThat(coinChanger.makeChange(25), hasItem(CoinType.QUARTER));
	}
	
}
