package controllers;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.ConnectionDataBase;
import application.MyListener;
import functions.ServerFunction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Client;
import models.Command;
import models.Conversation;
import models.Item;
import models.Menu;
import models.Message;

public class ServerController extends Thread implements Initializable{
	
    @FXML
    private VBox vbox_category;
    
    @SuppressWarnings("exports")
	@FXML
    public GridPane grid;

    @FXML
    private ScrollPane scrollpane_browse;
    
    @FXML
    private TextField prod_cat;

    @FXML
    private TextArea prod_desc;

    @FXML
    private TextField prod_name;

    @FXML
    private TextField prod_price;

    @FXML
    private TextField prod_quantity;
    
    @FXML
    private ImageView prod_img;
    
    @FXML
    private Label bulle_message;

    @FXML
    private Label bulle_notification;

    @FXML
    private Label label_message;

    @FXML
    private Label label_notification;

    @FXML
    private Label label_setting;
    
    @FXML
    private Label label_user_number;
    
    //variables for the message_views
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
    private TextField search_field;


    //for the tabpane
    @FXML
    private Tab browse_tab;
    
    @FXML
    private TabPane tabpane;
	
	@SuppressWarnings("exports")
	public static Connection cnx;
   	@SuppressWarnings("exports")
   	public static PreparedStatement statement;
   	@SuppressWarnings("exports")
   	public static ResultSet result;
	
	//variable
	public boolean is_active = true;
	private int num_client = 0;
	
	public ArrayList<Menu> list_menu = new ArrayList<Menu>();
	public static ObservableList<String> inc_message = FXCollections.observableArrayList();
	public static ObservableList<String> incoming_notification = FXCollections.observableArrayList();
	public static List<Item> list_items = new ArrayList<Item>();
	public static List<Item> list_command = new ArrayList<Item>();
	
	public static List<Client> client_list = new ArrayList<Client>();
	
	//For the MessageView
	public static ObservableList<Message> list_message = FXCollections.observableArrayList();
	
	public static ObservableList<Message> list_mesg = FXCollections.observableArrayList();
	
	public static ArrayList<String> list_user_connected = new ArrayList<String>();
	public static ArrayList<Conversation> list_user_temp = new ArrayList<Conversation>();
	public static ArrayList<String> list_mesg_tmp = new ArrayList<String>();
	
	public static ObservableList<Item> temp_target = FXCollections.observableArrayList();
	public static ObservableList<Command> obs_command = FXCollections.observableArrayList();
	public static ObservableList<Client> list_client_connected = FXCollections.observableArrayList();
	public ArrayList<Item> tmp_list_item = new ArrayList<Item>();
	
	public static ObservableList<Message> real_mesg = FXCollections.observableArrayList();
	public ArrayList<Item> search_tmp = new ArrayList<Item>();
	public static ObservableList<String> list_ip = FXCollections.observableArrayList();

	 @FXML
	 ListView<Message> list_view_incoming_message = new ListView<Message>(list_message);
	 
	 public static ObservableList<Message> list_sms_temporary = FXCollections.observableArrayList();

	
	@SuppressWarnings("exports")
	public static MyListener my_listener;
	public static MyListener menu_listener;
	
	//Query necessary for the startup
	String query_load_menu = "select categoryName, path from Category";
	
	//Variables for the windows
	Parent fxml;
	//Coordinate of the root
	public static double xOffset = 0;
	public static double yOffset = 0;
	
	public static ClientController client ;
	
	ModelMessageController cli_ctl;
	public static String line;
	
	public static Command command;
	Item choosen_item;
	
	FileChooser fileChooser = new FileChooser();
	String select_path;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		cnx = ConnectionDataBase.ConnectDB();
		packMenu();
		new ServerController().start();
		countMessage();
		showItems();
	}
	
	@Override
	public void run() {
		try {
			ServerSocket server_socket = new ServerSocket(1234);
			ServerSocket message_socket = new ServerSocket(50001);
			
			System.out.println("2 servers launched");
			
			while(is_active) {
				 Socket socket = server_socket.accept();
				 Socket sms_socket = message_socket.accept();
				 System.out.println("client conncted to 2 servers");
				 
				 list_ip.add(socket.getRemoteSocketAddress().toString());
				 
				 command = new Command();
				 command.setUsername(ClientController.username);
				 
				 packMenu();
				 System.out.println("after pack commented");
				 
				 //Add the user into the arraylist [must be a model of user]
				 list_user_connected.add(SignInController.username+ num_client);
				 
				 list_user_temp.add(new Conversation(SignInController.username+ num_client, list_mesg_tmp));
				 
				 Message model_message = new Message();
				 model_message.setAuthor(sms_socket.getRemoteSocketAddress().toString());
				 
				 real_mesg.add(model_message);
				 
				 //Instance new CLient model every client connected
				 Client client = new Client(SignInController.username, SignInController.num_tel, SignInController.num_bank);
				 System.out.println("Start recep");
				 new MainReception(socket, client, command).start();
				 new MessageReception(sms_socket, real_mesg.get(num_client)).start();
				 num_client++;
			}
			
			server_socket.close();
		} catch (IOException e) {
		}
	}
	
	 public void chooseImage() {
	    	Stage stage = (Stage) prod_cat.getScene().getWindow();
	 
	    	fileChooser.getExtensionFilters().addAll(
	    			new FileChooser.ExtensionFilter(".png", "*.png"),
	    			new FileChooser.ExtensionFilter(".jpg", "*.jpg")
	    	);
	    	
	    	File file = fileChooser.showOpenDialog(stage);
	    	if(file != null) {
	    		select_path = file.getAbsolutePath();	
	    		try {
					FileInputStream stream = new FileInputStream(select_path);
					Image image = new Image(stream);
					prod_img.setImage(image);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
	    	}	
	    }
	
	public void update() {
		String query = "update Product set productName = ?, category = ?, quantity = ?, price = ?, description = ?, img_pth = ? where idProduct = ?";
		
		try {
			statement = cnx.prepareStatement(query);
			statement.setString(1, prod_name.getText());
			statement.setString(2, prod_cat.getText());
			statement.setInt(3, Integer.parseInt(prod_quantity.getText()));
			statement.setFloat(4, Float.parseFloat(prod_price.getText()));
			statement.setString(5, prod_desc.getText());
			statement.setString(6, select_path);
			statement.setInt(7, choosen_item.getId());
			
			statement.execute();
			showItems();
			MyListener.successNotification("Update", "Item Updated");
		} catch (SQLException e) {
			String txt_failed = "update failed caused by :"+e;
			MyListener.failedNotification("Update", txt_failed);
		}
	}
	
	public void delete() {
		String query = "delete from Product where idProduct = ?";
		try {
			statement = cnx.prepareStatement(query);
			statement.setInt(1, choosen_item.getId());
			statement.execute();
			showItems();
			System.out.println("item deleted");
		} catch (SQLException e) {
			String txt_failed = "delete failed caused by :"+e;
			MyListener.failedNotification("Delete", txt_failed);
		}
	}
	
	public void exit() {
		System.exit(MAX_PRIORITY);
	}
	
	//Load menu
	public void packMenu() {
		ServerFunction.loadMenu(query_load_menu, list_menu);
		System.out.println("Size from server: "+list_menu.size());
		
		menu_listener = new MyListener() {
			
			@Override
			public void onClickListener(Menu menu) {
				choosenMenu(menu);
				
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
		
		List<HBox> list_hbox = new ArrayList<HBox>();
		try {
			for(int i=0; i<list_menu.size(); i++) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("/views/Category.fxml"));
				HBox hbox = fxmlLoader.load();
				list_hbox.add(hbox);
				
				CategoryController cat_cotroller = fxmlLoader.getController();
				cat_cotroller.setData(list_menu.get(i), menu_listener);
			}
			vbox_category.getChildren().setAll(list_hbox);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void choosenMenu(Menu menu) {
		tmp_list_item.clear();
		
		System.out.println("choosen Menu: "+menu.getCategory_name());
		
		for (Item item : list_items) {
			if(item.getCategory().equals(menu.getCategory_name())) {
				tmp_list_item.add(item);
			}
		}
		
		showSearchedItems(tmp_list_item);
	}
	
	public void addClientToList(String username) {
		try {
			FXMLLoader fxml_loader = new FXMLLoader();
			fxml_loader.setLocation(getClass().getResource("/views/ModelMessageView.fxml"));
			
			VBox vbox_model = fxml_loader.load();
			
			ModelMessageController model_msg_controller = fxml_loader.getController();
			model_msg_controller.setData(username, my_listener);
			
			//MessageViewController.list_message_layout.getChildren().setAll(vbox_model);
			
		} catch (Exception e) {
		}
	}
	
	public void showItems() {
		grid.getChildren().clear();
		ServerFunction.getDataItems(list_items);
		
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
	
	public void showSearchedItems(List<Item> list_items) {
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
		choosen_item = item;
		select_path = item.getImg_path();
		InputStream stream;
		
		reinitializInfos();
		prod_name.setText(item.getName());
		prod_cat.setText(item.getCategory());
		prod_desc.setText(item.getDescription());
		prod_price.setText(Float.toString(item.getPrice()));
		prod_quantity.setText(Integer.toString(item.getQuantity()));
		try {
			stream = new FileInputStream(item.getImg_path());
			Image image = new Image(stream);
    		prod_img.setImage(image);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void reinitializInfos() {
		prod_name.setText("");
		prod_cat.setText("");
		prod_desc.setText("");
		prod_price.setText("");
		prod_quantity.setText("");
	}
	
	//Send data to all the recipients actives
	public void sendDataBroadcast() {
		
	}
	
	public void openNewProduct() {
		Stage root = new Stage();
		loadWindow(root, "/views/NewProductView.fxml");
	}
	
	public void openNewCategory() {
		Stage root = new Stage();
		loadWindow(root, "/views/NewCategory.fxml");
	}
	
	//Make it closable
	public void openMessage() {
		Parent fxml;
		
		VBox vbox = new VBox();
		vbox.setPrefHeight(600);
		vbox.setPrefWidth(1000);
		
		try {
			fxml = FXMLLoader.load(getClass().getResource("/views/MessageView.fxml"));		
			vbox.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Tab tab_message = new Tab("Message");
		tab_message.setClosable(true);
		tab_message.setContent(vbox);
		tabpane.getTabs().add(tab_message);
		
		tabpane.getSelectionModel().select(1);
	}
	
	public void openNotification() {
		Parent fxml;
		
		VBox vbox = new VBox();
		vbox.setPrefHeight(600);
		vbox.setPrefWidth(1000);
		
		try {
			fxml = FXMLLoader.load(getClass().getResource("/views/NotificationView.fxml"));		
			vbox.getChildren().setAll(fxml);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Tab tab_message = new Tab("Notifications");
		tab_message.setClosable(true);
		tab_message.setContent(vbox);
		tabpane.getTabs().add(tab_message);
		
		tabpane.getSelectionModel().select(1);
		
	}
	
	public void openAdminTool() {
		Parent fxml;
		
		VBox vbox = new VBox();
		vbox.setPrefHeight(600);
		vbox.setPrefWidth(1000);
		
		try {
			fxml = FXMLLoader.load(getClass().getResource("/views/AdminToolView.fxml"));		
			vbox.getChildren().setAll(fxml);
		} catch (IOException e) {
		}
		
		Tab tab_message = new Tab("Admin Panel");
		tab_message.setClosable(true);
		tab_message.setContent(vbox);
		tabpane.getTabs().add(tab_message);
		
		tabpane.getSelectionModel().select(1);
		
	}

	public void search() {
		search_tmp.clear();
		for (Item item : list_items) {
			if(item.getName().equals(search_field.getText())) {
				search_tmp.add(item);
			}
		}
		
		if(!search_tmp.isEmpty()) {
			showSearchedItems(search_tmp);
		}
		
		if(search_field.getText().isBlank()) {
			showItems();
		}
	}
	
	public void reload() {
		packMenu();
		showItems();
	}
	
    public void refresh() {
    	System.out.println("Calling new databack");
		try {
			FXMLLoader fxml_loader = new FXMLLoader();
			fxml_loader.setLocation(getClass().getResource("/views/ModelMessageView.fxml"));
			
			VBox vbox_model = fxml_loader.load();
			
			ModelMessageController model_msg_controller = fxml_loader.getController();
			model_msg_controller.setData("Rayan Test", my_listener);
			
			list_message_layout.getChildren().clear();
			
			list_message_layout.getChildren().setAll(vbox_model);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
	public void LoadingDataToObservableList(List<Item> list_command, ObservableList<Item> target) {
		for (Item item : list_command) {
			target.add(new Item(item.getId(), item.getName(), item.getDescription(), item.getCategory(), item.getPrice(), item.getQuantity(), item.getImg_path(), item.getIndex(), item.getDate_com()));
		}
	}
	
	public static void check() {
		System.out.println("Size obs called function "+obs_command.get(0).getList_command().size());
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
			e.printStackTrace();
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
	
	public void countMessage() {
		bulle_message.setText(Integer.toString(inc_message.size()));
	}
	
	public void countNotification() {
		bulle_notification.setText(Integer.toString(incoming_notification.size()));
	}
	
	public void countUserConnected() {
		label_user_number.setText(Integer.toString(num_client));
	}

	
	// Class MT for the Reception
	class MainReception extends Thread{
		
		private Socket socket;
		private Client client;
		public Command command_via_client;
		
		public MainReception(Socket s, Client cli, Command cmd){
			this.socket = s;
			this.client = cli;
			this.command_via_client = cmd;
		}
		
		public void insertData(List<Item> list_data, String user_name) {
			String query = "insert into Cash (client_name, product_name, description, price, quantity, total_price, date_com) values (?,?,?,?,?,?,?) ";
	    	
			try {
				statement = cnx.prepareStatement(query);
				
				for (Item item : list_data) {
					System.out.println("Quant: "+item.getQuantity());
					statement.setString(1, user_name);
					statement.setString(2, item.getName());
					statement.setString(3, item.getDescription());
					statement.setFloat(4, item.getPrice());
					statement.setInt(5, item.getQuantity());
					statement.setFloat(6, item.getTotal_price());
					statement.setString(7, item.getDate_com());
					
					statement.execute();
					System.out.println("Insertion data Ok");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			try {
				int i = 0;
				//Send data to client
				OutputStream output = socket.getOutputStream();
				PrintWriter writer = new PrintWriter(output, true);
				ObjectOutputStream obj_output = new ObjectOutputStream(socket.getOutputStream());

				//Read data to the client
				InputStream input = socket.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));
				ObjectInputStream obj_input = new ObjectInputStream(input);
				
				ArrayList<Client> cli = new ArrayList<Client>();
				
				 while(true) {
					 obj_output.writeObject(list_menu);
					 obj_output.writeObject(list_items);
					 System.out.println("Sent items");
					 
					 LoadingDataToObservableList(list_command, temp_target);
					 command_via_client.setList_command(temp_target);
					 obs_command.add(command_via_client);
					 
					 Object cli_obj = obj_input.readObject();
					 
					 cli = (ArrayList<Client>) cli_obj;

					 
					 if(cli != null ) {
						 //System.out.println("Size cli: "+cli.size());
						 insertData(cli.get(0).getList_cmd(), cli.get(0).getUsername());
					 }
					 
					 list_client_connected.add(cli.get(0));
					 System.out.println("Size connected: "+ list_client_connected.size());
					 
					 System.out.println("Client received");		 
				 }
				
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		public Command getCommand_via_client() {
			return command_via_client;
		}
	}	
	
	public class MessageReception extends Thread{
		InputStream incoming_msg;
		OutputStream output_msg;
		PrintWriter msg_writer;
		BufferedReader msg_reader; 
		ObjectInputStream sms_obj_input;
		ObjectOutputStream sms_obj_output;
		
		Socket msg_socket;
		Message mesg;
		List<String> list_mesg;
		
		ObservableList<String> test = FXCollections.observableArrayList();
		
		public MessageReception(Socket socket, Message msg) {
			this.msg_socket = socket;
			this.mesg = msg;
		}
		
		int number_to_guess = (int) (Math.random()*10);
		
		public String guess(int number) {
			System.out.println("Number to guess: "+number_to_guess);
			if(number<number_to_guess) {
				return "Your number is too small";
			}else if(number>number_to_guess) {
				return "Your number is too big";
			}else {
				number_to_guess = (int) (Math.random()*10);
				return "Congratulations ! You won a ticket for 10.000 Ariary";
			}
		}
		
		@Override
		public void run() {
			try {
				incoming_msg = msg_socket.getInputStream();
				output_msg = msg_socket.getOutputStream();
				msg_writer = new PrintWriter(output_msg, true);
				msg_reader = new BufferedReader(new InputStreamReader(incoming_msg));
				
				while(true) {
					System.out.println("Numer guessed; "+number_to_guess);
					String sms = msg_reader.readLine();
					System.out.println("SMS from : "+msg_socket.getRemoteSocketAddress()+": "+sms);
					msg_writer.println(guess(Integer.parseInt(sms)));
					System.out.println("Rspo sent");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}