package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.MyListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class NewProductController implements Initializable{
	
   @FXML
   private ComboBox<String> combo_category;

   @FXML
   private TextArea prod_description_field;

   @FXML
   private ImageView prod_img_view;

   @FXML
   private TextField prod_name_field;

   @FXML
   private TextField prod_price_field;

   @FXML
   private Label src_label;
   
   @FXML
   private TextField prod_quantity_field;
   
   @FXML
   private Label warning_category;

   @FXML
   private Label warning_name;

   @FXML
   private Label warning_price;

   @FXML
   private Label warning_quantity;
   
   FileChooser fileChooser = new FileChooser();
   String path;
   
   private String __style_error = "-fx-border-color: RED; -fx-border-width: 2px";
   private String __style_success = "-fx-border-color: GREEN; -fx-border-width: 2px";
   
	
	public void loadComboCategory() {
		String query = "select categoryName from Category";
		try {
			ServerController.statement = ServerController.cnx.prepareStatement(query);
			ServerController.result = ServerController.statement.executeQuery();
			while(ServerController.result.next()) {
				combo_category.getItems().add(ServerController.result.getString("categoryName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void addProduct() {
		
		if(isValidFormatInput(prod_name_field, "text") && isValidFormatInput(prod_quantity_field, "number") && isValidFormatInput(prod_price_field, "number") && checkComboBox(combo_category)){
			String prod_name = prod_name_field.getText();
			String prod_cat = combo_category.getSelectionModel().getSelectedItem();
			int prod_quant = Integer.parseInt(prod_quantity_field.getText());
			float prod_price = Float.parseFloat(prod_price_field.getText());
			String prod_desc = prod_description_field.getText();
			
			String query = "insert into Product (productName, category, quantity, price, description, img_pth) values(?,?,?,?,?,?)";
			
			try {
				ServerController.statement = ServerController.cnx.prepareStatement(query);
				ServerController.statement.setString(1, prod_name);
				ServerController.statement.setString(2, prod_cat);
				ServerController.statement.setInt(3, prod_quant);
				ServerController.statement.setFloat(4, prod_price);
				ServerController.statement.setString(5, prod_desc);
				ServerController.statement.setString(6, path);
				
				ServerController.statement.execute();
				MyListener.successNotification("Add", "New product added with success !");
				
				prod_description_field.getScene().getWindow().hide();
				
			} catch (SQLException e) {
				String txt_failed = "add failed caused by : "+e;
				MyListener.failedNotification("Add", txt_failed);
			}
		}else {
			isValidFormatInput(prod_name_field, "text");
			isValidFormatInput(prod_quantity_field, "number");
			isValidFormatInput(prod_price_field, "number");
			checkComboBox(combo_category);
		}
		
		
	}
	

    public void chooseImage() {
    	Stage stage = (Stage) prod_description_field.getScene().getWindow();
 
    	fileChooser.getExtensionFilters().addAll(
    			new FileChooser.ExtensionFilter(".png", "*.png"),
    			new FileChooser.ExtensionFilter(".jpg", "*.jpg")
    	);
    	
    	File file = fileChooser.showOpenDialog(stage);
    	if(file != null) {
    		path = file.getAbsolutePath();
    		src_label.setText("");
    		src_label.setText(path);
    		
    		try {
				FileInputStream stream = new FileInputStream(path);
				Image image = new Image(stream);
				prod_img_view.setImage(image);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
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
    
    public boolean checkComboBox(ComboBox<String> combo) {
    	if(combo.getSelectionModel().getSelectedItem() == null) {
    		combo.setStyle(__style_error);
    		return false;
    	}else {
    		combo.setStyle(__style_success);
    		return true;
    	}
    }
    
    
	public void exit() {
		src_label.getScene().getWindow().hide();
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadComboCategory();
	}

	
}
