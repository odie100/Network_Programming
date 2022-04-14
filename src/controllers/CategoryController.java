package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import application.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Menu;

public class CategoryController {

    @FXML
    private ImageView cat_img_view;

    @FXML
    private Label cat_txt_label;
    
    Menu menu;
    MyListener my_listener;
    
    public void setData(Menu menu, MyListener my_listener) {
    	this.menu = menu;
    	this.my_listener = my_listener;
    	
    	cat_txt_label.setText(menu.getCategory_name());
    	
    	try {
			InputStream stream = new FileInputStream(menu.getPath());
			Image image = new Image(stream);
			cat_img_view.setImage(image);
		} catch (FileNotFoundException e) {
			Image image = new Image(getClass().getResourceAsStream("/images/avatar.png"));
			cat_img_view.setImage(image);
		}
    }
    
    
    
    @FXML
	private void click(MouseEvent mouseEvent) throws IOException {
		my_listener.onClickListener(menu);
	}
}