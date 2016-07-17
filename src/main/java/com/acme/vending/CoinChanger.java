package com.acme.vending;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class CoinChanger {
	
	/**
	 * LinkedHashMap guarantees iteration will occur in insertion order
	 * which we need to make change from largest to smallest denominations
	 */
	private Map<CoinType, Integer> denominationsAvailable = new LinkedHashMap<CoinType, Integer>();
	
	public CoinChanger() {
		denominationsAvailable.put(CoinType.QUARTER, 25);
		denominationsAvailable.put(CoinType.DIME, 10);
		denominationsAvailable.put(CoinType.NICKEL, 5);
	}

	public Collection<CoinType> makeChange(int amountInCents) {
		assertCanMakeChangeForAmount(amountInCents);
		
		ArrayList<CoinType> coins = new ArrayList<CoinType>();
		
		for (Map.Entry<CoinType, Integer> entry : denominationsAvailable.entrySet()) {
		    CoinType coin = entry.getKey();
		    Integer coinValue = entry.getValue();
		    
			while (amountInCents >= coinValue) {
				coins.add(coin);
				amountInCents -= coinValue;
			}
		}
		
		return coins;
	}
	
	private void assertCanMakeChangeForAmount(int amountInCents) {
		if (amountInCents % 5 > 0) {
			throw new RuntimeException("Cannot make change in amount " + amountInCents);
		}
	}
	
}
