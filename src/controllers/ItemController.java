package controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import application.MyListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Item;

public class ItemController {

    @FXML
    private Button btn_buy;

    @FXML
    private Label item_description;

    @FXML
    private Label item_name;

    @FXML
    private Label item_price;
    
    @FXML
    private ImageView item_img;
	
	Item item;
	MyListener my_listener;
	
	public void setData(@SuppressWarnings("exports") Item item, @SuppressWarnings("exports") MyListener my_listener) {
		this.item = item;
		this.my_listener = my_listener;
		
		item_name.setText(item.getName());
		item_description.setText(item.getDescription());
		item_price.setText(Float.toString(item.getPrice()));
		
		try {
			InputStream stream = new FileInputStream(item.getImg_path());
			Image image = new Image(stream);
			item_img.setImage(image);
		} catch (FileNotFoundException e) {
			//Hold this src
			Image image = new Image(getClass().getResourceAsStream("/home/odie/Downloads/Pichon/monitor_100px.png"));
			item_img.setImage(image);
		}
	}
	
	@FXML
	private void click(MouseEvent mouseEvent) throws IOException {
		my_listener.onClickListener(item);
	}
}
