package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Client implements Serializable{

	private static final long serialVersionUID = 1L;
	
	String username;
	String num_tel;
	int bank_number;
	
	List<Item> list_cmd = new ArrayList<Item>();
	
	public Client(String name, String tel, int bank) {
		this.username = name;
		this.num_tel = tel;
		this.bank_number = bank;
	}
	
	public Client() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNum_tel() {
		return num_tel;
	}

	public void setNum_tel(String num_tel) {
		this.num_tel = num_tel;
	}

	public int getBank_number() {
		return bank_number;
	}

	public void setBank_number(int bank_number) {
		this.bank_number = bank_number;
	}

	public List<Item> getList_cmd() {
		return list_cmd;
	}

	public void setList_cmd(List<Item> list_cmd) {
		this.list_cmd = list_cmd;
	}
	
	
	
	

}
