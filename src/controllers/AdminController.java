package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.MyListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Cash;

public class AdminController implements Initializable{

    @FXML
    private TableColumn<Cash, String> tab_client;

    @FXML
    private TableColumn<Cash, String> tab_date;

    @FXML
    private TableColumn<Cash, String> tab_description;

    @FXML
    private TableColumn<Cash, Float> tab_price;

    @FXML
    private TableColumn<Cash, String> tab_product;

    @FXML
    private TableColumn<Cash, Integer> tab_quantity;

    @FXML
    private TableColumn<Cash, Float> tab_total;

    @FXML
    private TableView<Cash> table_cash;

    
    
    public ListView<String> list_view = new ListView<String>(ServerController.list_ip);

    public void loadTableCash() {
    	System.out.println("Called loading");
    	table_cash.getItems().clear();
    	ObservableList<Cash> list_cash = FXCollections.observableArrayList();
    	
    	String query = "select * from Cash";
    	try {
			ServerController.statement = ServerController.cnx.prepareStatement(query);
			ServerController.result = ServerController.statement.executeQuery();
			while(ServerController.result.next()) {
				Cash cash = new Cash(ServerController.result.getInt("id"), ServerController.result.getString("client_name"), ServerController.result.getString("product_name"), ServerController.result.getString("description"), ServerController.result.getFloat("price"), ServerController.result.getInt("quantity"), ServerController.result.getFloat("total_price"), ServerController.result.getString("date_com"));
				list_cash.add(cash);
			}
			
		} catch (SQLException e) {
		}
    	
    	tab_client.setCellValueFactory(new PropertyValueFactory<Cash, String>("client_name"));
    	tab_product.setCellValueFactory(new PropertyValueFactory<Cash, String>("product_name"));
    	tab_description.setCellValueFactory(new PropertyValueFactory<Cash, String>("description"));
    	tab_price.setCellValueFactory(new PropertyValueFactory<Cash, Float>("price"));
    	tab_quantity.setCellValueFactory(new PropertyValueFactory<Cash, Integer>("quantity"));
    	tab_total.setCellValueFactory(new PropertyValueFactory<Cash, Float>("total_price"));
    	tab_date.setCellValueFactory(new PropertyValueFactory<Cash, String>("date"));
    	
    	table_cash.setItems(list_cash);
    }
    
    public void Refresh() {
    	System.out.println("Okokoko");
    	list_view.getItems().setAll(ServerController.list_ip);
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		list_view.getItems().addAll(ServerController.list_ip);
		loadTableCash();
	}
	
	public void showCoord() {
		MyListener.coordDevNotif();
	}

	
}
