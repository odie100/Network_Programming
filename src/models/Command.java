package models;

import java.io.Serializable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Command implements Serializable{

	private static final long serialVersionUID = 1L;
	
	String username;
	//List<Item> list_command;
	ObservableList<Item> list_commanded_item = FXCollections.observableArrayList();
	
	public Command(String username, ObservableList<Item> list) {
		this.username = username;
		this.list_commanded_item = list;
	}
	
	public Command() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ObservableList<Item> getList_command() {
		return list_commanded_item;
	}

	public void setList_command(ObservableList<Item> list_command) {
		this.list_commanded_item = list_command;
	}
	
	
	
}
