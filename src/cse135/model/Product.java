package cse135.model;

public class Product {
	private String name;
	private long sku;
	private String category;
	private double price;
	private String owner;
	
	public Product (String name, long sku, String category, double price, String owner) {
		this.setName(name);
		this.setSku(sku);
		this.setCategory(category);
		this.setPrice(price);
		this.setOwner(owner);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSku() {
		return sku;
	}

	public void setSku(long sku) {
		this.sku = sku;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}
