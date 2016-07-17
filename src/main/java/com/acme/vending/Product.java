package com.acme.vending;

public class Product {
	
	private String name = "";
	private int priceInCents = 0;
	private int inventoryCount = 0;
	
	public Product (String name, int priceInCents, int inventoryCount) {
		this.name = name;
		this.priceInCents = priceInCents;
		this.inventoryCount  = inventoryCount;
	}

	public int getPriceInCents() {
		return priceInCents;
	}
	
	public String getName() {
		return name;
	}

	public boolean isInStock() {
		return inventoryCount > 0;
	}

	public void dispenseOne() {
		inventoryCount -= 1;
	}
}
