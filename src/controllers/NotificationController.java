package controllers;

import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Client;
import models.Command;
import models.CommandedItem;

public class NotificationController implements Initializable{

	 @FXML
	 private ListView<Command> list_view_client = new ListView<Command>();
	 
	 @FXML
	 private TableColumn<CommandedItem, String> tab_description;
	
     @FXML
     private TableColumn<CommandedItem, Float> tab_price;

     @FXML
     private TableColumn<CommandedItem, String> tab_prod_name;

     @FXML
     private TableColumn<CommandedItem, Integer> tab_quantity;

     @FXML
     private TableColumn<CommandedItem, Float> tab_total;

     @FXML
     private TableView<CommandedItem> table;

     @FXML
     private TableView<Client> table_user;
     
     @FXML
     private TableColumn<Client, String> tab_username;
     
     
     public void loadTableUser() {
    	 table_user.getItems().clear();
    	 
    	 tab_username.setCellValueFactory(new PropertyValueFactory<Client, String>("username"));
    	 
    	 table_user.setItems(ServerController.list_client_connected);
    	 
     }
     
     public void check() {
    	 ServerController.check();
     }
     
     //Only one click
     public void loadTableDetailsChoosenClient() {
    	 table.getItems().clear();
 
    	 String user_name = table_user.getSelectionModel().getSelectedItem().getUsername();
    	 System.out.println("username : "+user_name);
    	
    	 ObservableList<CommandedItem> data = FXCollections.observableArrayList();
    	 
    	 DateFormat format = new SimpleDateFormat("yyyy/MM/dd");	
    	 Date date = new Date();
    	 String date_com = format.format(date);
    	 System.out.println("Actual date: "+date_com);
    	 
    	 String query = "select client_name, product_name, description, price, quantity, total_price, date_com from Cash where client_name = ? and date_com = ?";
    	 
    	 try {
			ServerController.statement = ServerController.cnx.prepareStatement(query);
			ServerController.statement.setString(1, user_name);
			ServerController.statement.setString(2, date_com);
			ServerController.result = ServerController.statement.executeQuery();
	    	 
	    	 while(ServerController.result.next()) {
	    		 CommandedItem commanded_item = new CommandedItem();
	    		 commanded_item.setClient_name(ServerController.result.getString("client_name"));
	    		 commanded_item.setProduct_name(ServerController.result.getString("product_name"));
	    		 commanded_item.setDescription(ServerController.result.getString("description"));
	    		 commanded_item.setPrice(ServerController.result.getFloat("price"));
	    		 commanded_item.setQuantity(ServerController.result.getInt("quantity"));
	    		 commanded_item.setTotal_price(ServerController.result.getFloat("total_price"));
	    		 commanded_item.setDate_com(ServerController.result.getString("date_com"));
	    		 
	    		 data.add(commanded_item);
	    	 }
	    	 
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	 
    	 tab_prod_name.setCellValueFactory(new PropertyValueFactory<CommandedItem, String>("product_name"));
    	 tab_description.setCellValueFactory(new PropertyValueFactory<CommandedItem, String>("description"));
    	 tab_price.setCellValueFactory(new PropertyValueFactory<CommandedItem, Float>("price"));
    	 tab_quantity.setCellValueFactory(new PropertyValueFactory<CommandedItem, Integer>("quantity"));
    	 tab_total.setCellValueFactory(new PropertyValueFactory<CommandedItem, Float>("total_price"));
    	 
    	 table.setItems(data);	 
     }
     
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		loadTableUser();
	}
     
     
	
	//Listen data table
	class DataReception extends Thread {
		
		Socket socket;
		
		public DataReception(Socket socket) {
			this.socket = socket;
		}
		
		
		@Override
		public void run() {
			
		}
	}
     


	
}
