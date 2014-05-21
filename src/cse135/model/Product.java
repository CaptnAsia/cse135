package cse135.model;



public class Product {
	private long id;
	private String name;
	private String sku;
	private int category;
	private double price;
	
	public Product() {
		this.name = "";
		this.sku = "";
		this.category = 0;
		this.price = 0;
	}
	
	public Product (String name, String sku, int category, double price) {
		this.setName(name);
		this.setSku(sku);
		this.setCategory(category);
		this.setPrice(price);
	}
	
	@Override
	public int hashCode(){  
		  return Integer.parseInt(sku);  
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

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
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


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
