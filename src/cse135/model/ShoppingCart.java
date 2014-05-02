package cse135.model;

import java.util.ArrayList;

public class ShoppingCart {
	private int amount = 0;
	private ArrayList<Product> cart = new ArrayList<Product>();
	
	public ShoppingCart() {
		this.setAmount(0);
		this.setCart(null);
	}
	public ArrayList<Product> getCart() {
		return cart;
	}
	public void setCart(ArrayList<Product> cart) {
		cart = cart;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		amount = amount;
	}
	

}
