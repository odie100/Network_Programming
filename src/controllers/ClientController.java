package controllers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.ConnectionDataBase;
import application.MyListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Conversation;
import models.Item;
import models.Menu;

public class ClientController extends Thread implements Initializable {

	@FXML
	private Button btn_send;

	@FXML
	private TextArea text_area;
	
	@FXML
	private Label message;
	
	@FXML
    private VBox vbox_category;
	
	@FXML
    private GridPane grid;
	
    @FXML
    private Label prod_cat;

    @FXML
    private Label prod_desc;

    @FXML
    private ImageView prod_img;

    @FXML
    private Label prod_name;

    @FXML
    private Label prod_price;
    
    @FXML
    private TextField prod_quantity_field;
    
    @FXML
    private TabPane tabpane;
    
    @FXML
    private TextField search_field;
    
    @FXML
    private TextArea sms_input;
    
    @FXML
    private HBox hbox_contact;
    
    @FXML
    private TextField email_field;
    
    @FXML
    private HBox hbox_email;
    
    @FXML
    private Label lbl_ip;

    
    
    ObservableList<String> list_model  = FXCollections.observableArrayList();
    ObservableList<String> empty_model  = FXCollections.observableArrayList();
    
    @FXML
    ListView<String> sms_listview = new ListView<String>(list_model);
    
    public ArrayList<Menu> list_menu = new ArrayList<Menu>();
    public static ArrayList<Menu> temp_menu = new ArrayList<Menu>();
    //for the varibale temp
    public ArrayList<Item> tmp_list_item = new ArrayList<Item>();
    public ArrayList<Item> search_tmp = new ArrayList<Item>();
    public static List<Item> list_items = new ArrayList<Item>(); 
    public static List<Item> list_panier = new ArrayList<Item>();
    
    Item choosen_item;
    public static int index_item = 0;
    public static String username = "Odie Rayan";
	
	@SuppressWarnings("exports")
	public static Connection cnx;
   	@SuppressWarnings("exports")
   	public static PreparedStatement statement;
   	@SuppressWarnings("exports")
   	public static ResultSet result;
    
	//Variables for the connection
	Socket client_socket;
	Socket sms_socket;
	 
	public static OutputStream output;
	public static ObjectOutputStream obj_output;
	public static PrintWriter writer;
	public static InputStream input;
	public static ObjectInputStream obj_input;
	public static BufferedReader reader;
	
	public static InputStream sms_incoming;
	public static OutputStream sms_output;
	public static PrintWriter sms_writer;
	public static BufferedReader sms_reader;
	public static ObjectInputStream sms_obj_input;
	public static ObjectOutputStream sms_obj_output;
	
	int a = 0;
			
	//Variable for the windows
	Parent fxml;
	//Coordinate of the root
	public static double xOffset = 0;
	public static double yOffset = 0;
	
	@SuppressWarnings("exports")
	public static MyListener my_listener;
	@SuppressWarnings("exports")
	public static MyListener menu_listener;
	
	public ClientController(Socket socket) {
		this.client_socket = socket;
	}
	
	public ClientController() {
		
	}
	
	public void sendMessage() {
		int i = 0;
		try {
			list_model.add("Vous: "+sms_input.getText());
			sms_listview.getItems().setAll(list_model);
			sms_writer.println(sms_input.getText());
		} catch (Exception e) {
		}
	}
	

	public void contactUs() {
		if(hbox_contact.isVisible() == true) {
			hbox_contact.setVisible(false);
		}else {
			hbox_contact.setVisible(true);
		}
	}
	
	
	public void packMenu(ArrayList<Menu> list_menu) {
		System.out.println("Called pack size "+ list_menu.size());
		vbox_category.getChildren().clear();
		
		menu_listener = new MyListener() {
			
			@Override
			public void onClickListener(Menu menu) {
				choosenCategory(menu);
				
			}
			
			@Override
			public void onClickListener(Conversation conversation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onClickListener(Item item) {
				// TODO Auto-generated method stub
				
			}
		};
		try {
			for(int i=0; i<list_menu.size(); i++) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("/views/Category.fxml"));
				HBox hbox = fxmlLoader.load();
				CategoryController cat_cotroller = fxmlLoader.getController();
				cat_cotroller.setData(list_menu.get(i), menu_listener);
				vbox_category.getChildren().addAll(hbox);
			}
		} catch (Exception e) {
		}
	}
	
	public void validateEmail() {
		
	}
	
	public void connect() {
		String title = "Connection";
		String success = "Connected to server";
		String failed = "Unable to connect to server";
		try {
			client_socket = new Socket("localhost", 1234);
			sms_socket = new Socket("localhost", 50001);
			
			lbl_ip.setText(client_socket.getRemoteSocketAddress().toString());
			
			input = client_socket.getInputStream();
			output = client_socket.getOutputStream();
			writer = new PrintWriter(output, true);
			reader = new BufferedReader(new InputStreamReader(input));
			
			sms_incoming = sms_socket.getInputStream();
			sms_output = sms_socket.getOutputStream();
			sms_writer = new PrintWriter(sms_output, true);
			sms_reader = new BufferedReader(new InputStreamReader(sms_incoming));
			
			System.out.println("Connect to server Ok");
		} catch (Exception e) {
		}
	}
	
	
	public void showItems(List<Item> list_items) {
		grid.getChildren().clear();
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
				if(column == 2) {
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
				
				GridPane.setMargin(hbox, new Insets(0,20,10,20));
				
			}
		} catch (Exception e) {
		}
		
		}
		
		public void choosenItem(Item item) {
			choosen_item = new Item(item.getId(), item.getName(), item.getDescription(), item.getCategory(), item.getPrice(), item.getQuantity(), item.getImg_path(), item.getIndex(), item.getDate_com());
			InputStream stream;
			
			reinitializInfos();
			
			prod_name.setText(item.getName());
			prod_cat.setText(item.getCategory());
			prod_desc.setText(item.getDescription());
			prod_price.setText(Float.toString(item.getPrice()));
			try {
				stream = new FileInputStream(choosen_item.getImg_path());
				Image image = new Image(stream);
	    		prod_img.setImage(image);
			} catch (FileNotFoundException e) {
			}
		}

		
		public void choosenCategory(Menu menu) {
			tmp_list_item.clear();
			
			System.out.println("choosen Menu: "+menu.getCategory_name());
			
			for (Item item : list_items) {
				if(item.getCategory().equals(menu.getCategory_name())) {
					tmp_list_item.add(item);
				}
			}
			showItems(tmp_list_item);
		}
		
		@FXML
		public void search() {
			System.out.println("Fuuunction");
			search_tmp.clear();
			for (Item item : list_items) {
				if(item.getName().equals(search_field.getText())) {
					search_tmp.add(item);
				}
			}
			
			if(!search_tmp.isEmpty()) {
				showItems(search_tmp);
			}
			
			if(search_field.getText().isBlank()) {
				showItems(list_items);
			}
		}
	
		public void reinitializInfos() {
			prod_name.setText("");
			prod_cat.setText("");
			prod_desc.setText("");
			prod_price.setText("");
			prod_quantity_field.clear();
		}
		
		public void openPanier() {
			Parent fxml;
			
			VBox vbox = new VBox();
			vbox.setPrefHeight(600);
			vbox.setPrefWidth(1000);
			
			try {
				fxml = FXMLLoader.load(getClass().getResource("/views/Panier.fxml"));		
				vbox.getChildren().setAll(fxml);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Tab tab_panier = new Tab("Your Panier");
			tab_panier.setClosable(true);
			tab_panier.setContent(vbox);
			tabpane.getTabs().add(tab_panier);
			
			tabpane.getSelectionModel().select(1);
		}
		
		
		//Manage the windows
		public void loadWindow(Stage stage_name, String src) {
			System.out.println("first");
	    	try {
	 
				fxml = FXMLLoader.load(getClass().getResource(src));
				move(fxml, stage_name);
				Scene scene = new Scene(fxml);
				scene.setFill(Color.TRANSPARENT);
				scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
				
				stage_name.setScene(scene);
				stage_name.initStyle(StageStyle.TRANSPARENT);
				stage_name.show();
			} catch (IOException e) {
			}	
		}
		
		public static void move(Parent root, Stage primaryStage) {
			//grab root 
			root.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					xOffset = event.getSceneX();
					yOffset = event.getSceneY();
				}
			});
			//move around here
			root.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					primaryStage.setX(event.getScreenX() - xOffset);
					primaryStage.setY(event.getScreenY() - yOffset);	
				}
			});
		}
		
		public void addToPanier() {
			DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	    	Date date = new Date();
	    	String date_com = format.format(date);
			
			Item item_added = new Item(choosen_item.getId(), prod_name.getText(), prod_desc.getText(), prod_cat.getText(), Float.parseFloat(prod_price.getText()),Integer.parseInt(prod_quantity_field.getText()), choosen_item.getImg_path(), index_item, date_com);
			
			list_panier.add(item_added);
			index_item++;
			MyListener.successNotification("Cart", "Items added to cart !");
		}
		
	
	@Override
	public void run() {	
		try {
			client_socket = new Socket("localhost", 1234);
			System.out.println("After run ok: adres"+client_socket.getRemoteSocketAddress());
			
			input = client_socket.getInputStream();
			output = client_socket.getOutputStream();
			writer = new PrintWriter(output, true);
			reader = new BufferedReader(new InputStreamReader(input));
		} catch (Exception e) {
		}
	}
	
	public void refresh() {
		packMenu(list_menu);
		showItems(list_items);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cnx = ConnectionDataBase.ConnectDB();
		empty_model.add("");
		connect();
		new Reception(client_socket).start();
		new Message(sms_socket).start();
		sms_listview.getItems().addAll(list_model);
	}

	
	// Thread for the Communication
	class Reception extends Thread{
		private Socket socket;
		private int indexation = 0;
		
		public Reception(Socket socket){
			this.socket = socket;
		}
		
		@Override
		public void run() {
		try {
			output = socket.getOutputStream();
			obj_output = new ObjectOutputStream(output);
			writer = new PrintWriter(output, true);
			
			input = socket.getInputStream();
			obj_input = new ObjectInputStream(input);
			reader = new BufferedReader(new InputStreamReader(input));	
		
			String line;
			
			while(true) {
				//Reveive Menu
				Object object = obj_input.readObject();
				list_menu = (ArrayList<Menu>) object;
				
				System.out.println("menu received");
				indexation++;
				packMenu(list_menu);
				System.out.println("packing");
				
				//Receive items
				Object object_item = obj_input.readObject();
				list_items = (List<Item>) object_item;
				showItems(list_items);
			}
				
			} catch (Exception e) {
			}
		}
	}
	
	public class Message extends Thread{
		Socket sms_socket;
		Message message;
		
		public Message(Socket socket) {
			this.sms_socket = socket;
		}
		
		@Override
		public void run() {
			try {
				sms_incoming = sms_socket.getInputStream();
				sms_reader = new BufferedReader(new InputStreamReader(sms_incoming));
				
				sms_output = sms_socket.getOutputStream();
				sms_writer = new PrintWriter(sms_output, true);
				while(true) {
					String incoming_response = sms_reader.readLine();
					list_model.add("Response: "+incoming_response);
					sms_listview.getItems().setAll(list_model);
					System.out.println("Response from server to new functionnality : "+ incoming_response);
				}
			} catch (IOException e) {
			}
		}
	}
}
