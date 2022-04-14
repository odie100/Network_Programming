package models;

import java.util.List;

public class Message {

	String author;
	List<String> content;
	
	public Message(String author, List<String> content) {
		this.author = author;
		this.content = content;
	}
	
	public Message() {
		
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public List<String> getContent() {
		return content;
	}

	public void setContent(List<String> content) {
		this.content = content;
	}

	
	
}
