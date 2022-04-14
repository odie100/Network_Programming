package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.MyListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import models.Client;
import models.Command;
import models.Conversation;
import models.Item;
import models.Menu;

public class PanierController implements Initializable{
	  
	  @FXML
	  private GridPane grid;
	  @SuppressWarnings("exports")
	  public MyListener my_listener;
	  public Item choosen_item;
	  
	  public static String username = "";
	  public static Client client;
	
	  public void showItems(List<Item> list_items) {
			grid.getChildren().clear();
			//ServerFunction.getDataItems(list_items);
			
			my_listener = new MyListener() {
				
				@Override
				public void onClickListener(Menu menu) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onClickListener(Conversation conversation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onClickListener(Item item) {
					choosenItem(item);
					
				}
			};
			
			int column = 0;
			int row = 1;
			
			try {
				
				for(int k=0; k<list_items.size();k++) {
					FXMLLoader fxml_loader = new FXMLLoader();
					fxml_loader.setLocation(getClass().getResource("/views/Item.fxml"));
					
					HBox hbox = fxml_loader.load();
					
					ItemController item_controller = fxml_loader.getController();
					item_controller.setData(list_items.get(k), my_listener);
					
					//Here to adjust the alignment
					if(column == 3) {
						column = 0;
						row++;
					}
					
					grid.add(hbox, column++, row);
					
					grid.setMinWidth(Region.USE_COMPUTED_SIZE);
					grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
					grid.setMaxWidth(Region.USE_PREF_SIZE);
					
					grid.setMinHeight(Region.USE_COMPUTED_SIZE);
					grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
					grid.setMaxHeight(Region.USE_PREF_SIZE);
					
				}
				System.out.println("Listener");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			}
	  
	  public void choosenItem(Item item) {
	    choosen_item = new Item();
		choosen_item.setId(item.getId());
		choosen_item.setName(item.getName());
		choosen_item.setPrice(item.getPrice());
		choosen_item.setDescription(item.getDescription());
		choosen_item.setImg_path(item.getImg_path());
		choosen_item.setQuantity(item.getQuantity());
		choosen_item.setIndex(item.getIndex());
		System.out.println("item selected: "+choosen_item.getName());
	 }
	  
	  
	  //Remove the Item selected from the List contains all the commands
	  public void removeToPanier() {
		  int i = 0;
		  ClientController.list_panier.remove(choosen_item.getIndex());
		  System.out.println("Size list panier after deleting : "+ClientController.list_panier.size());
		  showItems(ClientController.list_panier);
		  ClientController.index_item--;
	  }

	  
	  public void confirmCommand() {
		  //open modal windows 
		  try {
			Stage stage = new Stage();
			Parent fxml = FXMLLoader.load(getClass().getResource("/views/Information.fxml"));
			Scene scene = new Scene(fxml);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		  } catch (IOException e1) {
			e1.printStackTrace();
		  }
	  }

	public static void setUsername(String username) {
		PanierController.username = username;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("Size list_panier: "+ClientController.list_panier.size());
		showItems(ClientController.list_panier);
		
	}
}
