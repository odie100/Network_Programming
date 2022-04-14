package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import models.Conversation;

public class ModelMessageController {
	

    @SuppressWarnings("exports")
	@FXML
    public Label label_content;

    @SuppressWarnings("exports")
	@FXML
    public Label label_username;
    
    Conversation conversation;
    MyListener my_listener;
    
    public static ArrayList<String> convers = new ArrayList<String>();
    
//    public void setData(Message message) {
//    	System.out.println(label_content.getText());
//    	label_content.setText(message.getContent());
//    	label_username.setText(message.getAuthor());
//    }
    
    public void setData(String username, MyListener my_listener) {
    	this.my_listener = my_listener;
    	
    	label_username.setText(username);
    	for (String text : convers) {
			System.out.println("Conversation: "+ text);
		}
    }

    @FXML
	private void click(MouseEvent mouseEvent) throws IOException {
		my_listener.onClickListener(conversation);
	}

}
