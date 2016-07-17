package com.acme.vending;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProductTest {

	@Test
	public void whenAProductHasNoInventoryLeft_isReportsNotInStock() throws Exception {
		Product product = new Product("widget", 100, 0);
		assertFalse(product.isInStock());
	}
	
	@Test
	public void whenAProductHasInventoryLeft_isReportsInStock() throws Exception {
		Product product = new Product("widget", 100, 1);
		assertTrue(product.isInStock());
	}
	
	@Test
	public void whenDispensingAProductWithOneItemLeft_changesItemToOutOfStock() throws Exception {
		Product product = new Product("widget", 100, 1);
		assertTrue(product.isInStock());
		product.dispenseOne();
		assertFalse(product.isInStock());
	}
	
	@Test
	public void whenDispensingAProductWithSeveralItemsLeft_remainsInStock() throws Exception {
		Product product = new Product("widget", 100, 2);
		assertTrue(product.isInStock());
		product.dispenseOne();
		assertTrue(product.isInStock());
	}
	
	@Test(expected=RuntimeException.class)
	public void whenDispensingAnOutOfStockProduct_throwsException() throws Exception {
		Product product = new Product("widget", 100, 0);
		product.dispenseOne();
	}
}
