package cse135.model;



public class Product {
	private long id;
	private String name;
	private long sku;
	private int category;
	private double price;
	private long owner;
	
	public Product() {
		this.name = "";
		this.sku = 0;
		this.category = 0;
		this.price = 0;
		this.owner = 0;
	}
	
	public Product (String name, long sku, int category, double price, long owner) {
		this.setName(name);
		this.setSku(sku);
		this.setCategory(category);
		this.setPrice(price);
		this.setOwner(owner);
	}
	
	@Override
	public int hashCode(){  
		  return (int)sku;  
	} 
	
	@Override 
	public boolean equals(Object o) {
		if((o instanceof Product) && ((Product)o).getId() == id) {
			return true;
		}
		return false;
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

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public long getOwner() {
		return owner;
	}

	public void setOwner(long owner) {
		this.owner = owner;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
