package models;

public class CommandedItem {

	String client_name;
	String product_name;
	String description;
	String date_com;
	
	float price;
	float total_price;
	
	int quantity;
	
	public CommandedItem(String username, String pro_name, String desc, String date, float price, float total, int quantity) {
		this.client_name = username;
		this.product_name = pro_name;
		this.description = desc;
		this.date_com = date;
		this.price = price;
		this.total_price = total;
		this.quantity = quantity;
	}
	
	public CommandedItem() {
		
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

	public String getDate_com() {
		return date_com;
	}

	public void setDate_com(String date_com) {
		this.date_com = date_com;
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
}
