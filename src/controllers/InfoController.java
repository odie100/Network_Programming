package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.MyListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import models.Client;


public class InfoController implements Initializable {

    @FXML
    private TextField user_card_field;

    @FXML
    private TextField user_name_field;

    @FXML
    private TextField user_tel_field;
    
    private String __style_error = "-fx-border-color: RED; -fx-border-width: 2px";
    private String __style_success = "-fx-border-color: GREEN; -fx-border-width: 2px";
    
    public void setInfo() {
    	
    	if(!user_tel_field.getText().isEmpty() && !user_card_field.getText().isEmpty() && !user_name_field.getText().isEmpty()) {
    		if(isValidNumber(user_tel_field) && isValidFormatInput(user_name_field, "text") && isValidCard(user_card_field)) {
    			Client new_cli = new Client(user_name_field.getText(), user_tel_field.getText(), Integer.parseInt(user_card_field.getText()));
    			new_cli.setList_cmd(ClientController.list_panier);
    	    	
    	    	user_card_field.getScene().getWindow().hide();
    	    	TempDataStorage.client_list.add(new_cli);
    	    	System.out.println("Size client_list"+TempDataStorage.client_list.size());
    	    	for (Client item : TempDataStorage.client_list) {
    				System.out.println("name: "+ item.getUsername());
    			}
    	    	  	
    	    	 try {
    				ClientController.obj_output.writeObject(TempDataStorage.client_list);
    				ClientController.writer.println(" ");
    				System.out.println("Command confirmed");
    			} catch (Exception e) {
    			}
    		}else {
    			isValidNumber(user_tel_field);
    			isValidFormatInput(user_name_field, "text");
    			isValidCard(user_card_field);
    		}
    	}else {
    		isValidNumber(user_tel_field);
			isValidFormatInput(user_name_field, "text");
			isValidCard(user_card_field);
    	}
    	 	
    }

    
    public boolean isValidFormatInput(TextField field, String type) {
    	String reg_exp;
    	
    	if(!field.getText().isEmpty()) {
    		if(type.equals("number")) {
        		//
        		reg_exp = "[0-9]+";
        	}else {
        		reg_exp = "[\\w .]+";
        	}
        	if(field.getText().matches(reg_exp) == false) {
        		field.setStyle(__style_error);
        		return false;
        	}else {
        		field.setStyle(__style_success);
        		return true;
        	}
    	}else {
    		 field.setStyle(__style_error);
   		  	return false; 
    	}
    }
    
    public boolean isValidNumber(TextField field) {
    	String string = field.getText();
    	String reg_exp = "[\\d]{10,10}";  
	
	  if(!field.getText().isEmpty()) {
		  if(string.matches(reg_exp) == false) {
			  field.setStyle(__style_error);
			  return string.matches(reg_exp);
		  }else { 
			  field.setStyle(__style_success);
			  return string.matches(reg_exp); 
		  } 
	  }else {
		  field.setStyle(__style_error);
		  return false; 
	  }
	
	}
    
    public boolean isValidCard(TextField field) {
    	String string = field.getText();
    	String reg_exp = "[\\d]{6,6}";  
	
	  if(!field.getText().isEmpty()) {
		  if(string.matches(reg_exp) == false) {
			  field.setStyle(__style_error);
			  return string.matches(reg_exp);
		  }else { 
			  field.setStyle(__style_success);
			  return string.matches(reg_exp); 
		  } 
	  }else {
		  field.setStyle(__style_error);
		  return false; 
	  }
	
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("Initializable");
		
	}
	
}
