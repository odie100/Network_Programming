package models;

public class Cash {
 
	int id;
	int quantity;
	
	String client_name;
	String product_name;
	String description;
	String date;
	
	float price;
	float total_price;
	
	
	public Cash(int id, String user, String product, String desc, float price, int quantity, float total, String date) {
		this.id = id;
		this.client_name = user;
		this.product_name = product;
		this.description = desc;
		this.price = price;
		this.quantity = quantity;
		this.total_price = total;
		this.date = date;
	}
	
	public Cash() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getTotal_price() {
		return total_price;
	}

	public void setTotal_price(float total_price) {
		this.total_price = total_price;
	}
	
	
	
}
