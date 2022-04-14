package models;

import java.util.ArrayList;

public class Conversation {
	
	String username;
	ArrayList<String> text_mesg;
	
	public Conversation(String user, ArrayList<String> conv) {
		this.username = user;
		this.text_mesg = conv;
	}
	
	public Conversation() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<String> getConversation() {
		return text_mesg;
	}

	public void setConversation(ArrayList<String> conversation) {
		this.text_mesg = conversation;
	}

	
	
}
