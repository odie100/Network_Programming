package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.MyListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import models.Client;
import models.Conversation;
import models.Item;
import models.Menu;
import models.Message;

public class MessageViewController extends Thread implements Initializable{
	
	@FXML
    private AnchorPane anchor_pane;

    @FXML
    private Button btn_send;

    @FXML
    private ScrollPane layout_message_view;

    @FXML
    private VBox list_message_layout;

    @FXML
    private TextArea text_input;

    @FXML
    private Label username_label;
    
    @FXML
    private TableColumn<Message, String> tab_user;

    @FXML
    private TableView<Message> table_user_connected;

    
    private MyListener my_listener;
    
    @FXML
    ListView<String> list_view_content = new ListView<String>();
    
    //Variables
    public static ObservableList<Message> list_message = FXCollections.observableArrayList();
    public ArrayList<Conversation> convers = new ArrayList<Conversation>();
    
    public void loadIncomingMessage() {
    	
    }
    
    public void loadTableUserConnected() {
    	System.out.println("Tabl use");
    	System.out.println("Size client connected: "+ ServerController.list_sms_temporary.size());
    	table_user_connected.getItems().clear();
    	
    	tab_user.setCellValueFactory(new PropertyValueFactory<Message, String>("author"));
    	
    	table_user_connected.setItems(ServerController.list_sms_temporary);
    }
    
    @Override
    public void run() {
    	System.out.println("Affichage name "+ SignInController.username);
    }
    
    public void refresh() {
    	loadTableUserConnected();
    }
    
    
    public void reloadList(ArrayList<String> liste) {
    	System.out.println("Call Reload");
    	
    	my_listener = new MyListener() {
			
			@Override
			public void onClickListener(Menu menu) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClickListener(Conversation conversation) {
				choosenDiscussion(conversation);
				
			}
			
			@Override
			public void onClickListener(Item item) {
				// TODO Auto-generated method stub
				
			}
		};
    	
    	for (String user : liste) {
    		try {
    			FXMLLoader fxml_loader = new FXMLLoader();
    			fxml_loader.setLocation(getClass().getResource("/views/ModelMessageView.fxml"));
    			
    			VBox vbox_model = fxml_loader.load();
    			
    			ModelMessageController model_msg_controller = fxml_loader.getController();
    			model_msg_controller.setData(user, my_listener);
    			//Maybe we add the functionnality here
    			model_msg_controller.convers.add(ServerController.line);
    			
    			
    			list_message_layout.getChildren().add(vbox_model);
    			
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
		}
    }
	
    public void choosenDiscussion(Conversation conversation) {
    	System.out.println("Here it is: "+conversation.getConversation().get(0));
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		reloadList(ServerController.list_user_connected);
		loadTableUserConnected();
	}

}
