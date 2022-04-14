package models;

import java.io.Serializable;

public class Item implements Serializable{

	private static final long serialVersionUID = 1L;
	
	int id;
	int quantity;
	int index;
	
	String name;
	String description;
	String category;
	String img_path;
	String date_com;
	
	float price;
	float total_price;
	
	public Item(int id, String name, String description, String category, float price, int quantity, String path, int indx, String date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.img_path = path;
		this.index = indx;
		this.date_com = date;
	}
	
	public Item() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public float getTotal_price() {
		return this.price * this.quantity;
	}

	public String getDate_com() {
		return date_com;
	}

	public void setDate_com(String date_com) {
		this.date_com = date_com;
	}
	
	
	
	
	
	
	
}
