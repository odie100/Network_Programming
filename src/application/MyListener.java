package application;

import models.Conversation;
import models.Item;
import models.Menu;

import org.controlsfx.control.Notifications;

import javafx.geometry.Pos;
import javafx.util.Duration;

public interface MyListener {
	
	public void onClickListener(Item item);
	
	public void onClickListener(Conversation conversation);
	
	public void onClickListener(Menu menu);
	
	// Notification
	
	public static void successNotification(String title, String text) {
			
			//If u want to change icon of notification add this line
			//Image image = new Image("urm");
			//and in graphic must be like .graphic(new ImageView(image);
	
			Notifications notificationBuilder = Notifications.create()
					.title(title)
					.text(text)
					.graphic(null)
					.hideAfter(Duration.seconds(5))
					.position(Pos.TOP_CENTER);
					
			//if u want to change theme active this line
			//notificationBuilder.darkStyle();
			
			notificationBuilder.showConfirm();
			
		};
		
		public static void failedNotification(String title, String text) {
			
			Notifications notificationBuilder = Notifications.create()
					.title(title)
					.text(text)
					.graphic(null)
					.hideAfter(Duration.seconds(5))
					.position(Pos.TOP_CENTER);
					
			notificationBuilder.showError();
			
		};
		
		public static void coordDevNotif() {
			String info = "email: andriampeno.odilon@gmail.com";
			
			Notifications notificationBuilder = Notifications.create()
					.title("Information du développeur")
					.text(info)
					.graphic(null)
					.hideAfter(Duration.seconds(10))
					.position(Pos.BOTTOM_CENTER);
					
			//if u want to change theme active this line
			//notificationBuilder.darkStyle();
			
			notificationBuilder.showInformation();;
		}
		
	public static void warningNotification(String title, String text) {
			
			Notifications notificationBuilder = Notifications.create()
					.title(title)
					.text(text)
					.graphic(null)
					.hideAfter(Duration.seconds(5))
					.position(Pos.TOP_CENTER);
					
			notificationBuilder.showWarning();
			
		};

}
