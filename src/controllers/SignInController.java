package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.ConnectionDataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Client;

public class SignInController implements Initializable {
	
	@FXML
	private TextField password_field;

    @FXML
    private TextField username_field;

	public static Client client;
	
	public static String username = "Odie Rayan";
	public static String num_tel;
	public static int num_bank;
	
	@SuppressWarnings("exports")
	public static Connection cnx;
   	@SuppressWarnings("exports")
   	public static PreparedStatement statement;
   	@SuppressWarnings("exports")
   	public static ResultSet result;
   	
   	BorderPane fxml;
	
	
	public void signIn() {
		String query = "select password from Client where username = ? or num_tel = ?";
		Stage root = new Stage();
		
		try {
			statement = cnx.prepareStatement(query);
			statement.setString(1, username_field.getText());
			statement.setString(2, username_field.getText());
			result = statement.executeQuery();
			
			while(result.next()) {
				System.out.println("Ok");
				System.out.println(result.getString("password"));
				if(result.getString("password").equals(password_field.getText())) {
					System.out.println("Confirm");
					ClientController.username = username_field.getText();
					closeCurrentWindow(password_field);
					loadWindow(root, "/views/Client_view.fxml");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/*
		 * execute query and add this condition
		 * 
		 * 
		 * if ok:
		 * 	client.setname(result.gettext());
		 * 
		 * then we get this client model full of data via ClientController and send to server the model
		 * 
		 * 
		 * */
	}
	
	public  void loadWindow(Stage stage_name, String src) {
    	//we need to check with if else if one of the window to open is already running so
    	//if one is already running we need just to set it visible else we need to create
    	//a new instance
    	
    	try {
			fxml = FXMLLoader.load(getClass().getResource(src));
			
			Scene scene = new Scene(fxml);
			//scene.setFill(Color.TRANSPARENT);
			scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
			
			stage_name.setScene(scene);
			//stage_name.initStyle(StageStyle.TRANSPARENT);
			stage_name.show();
		} catch (IOException e) {
			e.printStackTrace();
		}	
    }
	
    public static void closeCurrentWindow(TextField child_name) {
    	child_name.getScene().getWindow().hide();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx = ConnectionDataBase.ConnectDB();
		
	}
}
