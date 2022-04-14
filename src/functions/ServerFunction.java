package functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import controllers.ServerController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Item;
import models.Menu;

public class ServerFunction implements Initializable{
	
    	@FXML
    private Label src_label;

    @FXML
    private TextField cat_name_field;
    
    @FXML
    private ImageView cat_img_view;

	   
    static Menu menu;
    FileChooser fileChooser = new FileChooser();
    String path;
    
    
    private String __style_error = "-fx-border-color: RED; -fx-border-width: 2px";
    private String __style_success = "-fx-border-color: GREEN; -fx-border-width: 2px";

	public static List<Menu> loadMenu(String query, ArrayList<Menu> data) {
		data.clear();
		try {
			ServerController.statement = ServerController.cnx.prepareStatement(query);
			ServerController.result = ServerController.statement.executeQuery();
			while(ServerController.result.next()) {
				menu = new Menu();
				
				menu.setCategory_name(ServerController.result.getString("categoryName"));
				menu.setPath(ServerController.result.getString("path"));
				
				data.add(menu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	public void exit() {
		src_label.getScene().getWindow().hide();
	}
    
	
	public static List<Item> getDataItems(List<Item> data){
		data.clear();
		
		Item item;
		
		String query = "select * from Product";
		
		try {
			ServerController.statement = ServerController.cnx.prepareStatement(query);
			ServerController.result = ServerController.statement.executeQuery();
			while(ServerController.result.next()) {
				item = new Item();
				
				item.setId(ServerController.result.getInt("idProduct"));
				item.setName(ServerController.result.getString("productName"));
				item.setCategory(ServerController.result.getString("category"));
				item.setQuantity(ServerController.result.getInt("quantity"));
				item.setPrice(ServerController.result.getFloat("price"));
				item.setDescription(ServerController.result.getString("description"));
				item.setImg_path(ServerController.result.getString("img_pth"));
				
				data.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	public void addCategory() {
		if(!cat_name_field.getText().isEmpty()) {
			if(isValidFormatInput(cat_name_field, "text")) {
				String query = "insert into Category (categoryName, path) values(?,?)";
				try {
					ServerController.statement = ServerController.cnx.prepareStatement(query);
					ServerController.statement.setString(1, cat_name_field.getText());
					ServerController.statement.setString(2, path);
					ServerController.statement.execute();
					System.out.println("Cat inserted");
					hide();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else {
				isValidFormatInput(cat_name_field, "text");
			}
		}else {
			isValidFormatInput(cat_name_field, "text");
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
	
	public void hide() {
		cat_img_view.getScene().getWindow().hide();
	}
	
	 public void chooseImage() {
	    	Stage stage = (Stage) cat_name_field.getScene().getWindow();
	 
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
					cat_img_view.setImage(image);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
	    	}	
	    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	
	

}
