package models;

import java.io.Serializable;

public class Menu implements Serializable{
	
    static final long serialVersionUID = 1L;
	
    String category_name;
	String path;
	
	public Menu() {
		
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	
}
