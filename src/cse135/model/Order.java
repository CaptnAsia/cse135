package cse135.model;

public class Order {
	private double price;
	private String name;
	private int amount;
	
	public Order (String name, double price, int amount) {
		this.setName(name);
		this.setPrice(price);
		this.setAmount(amount);
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
