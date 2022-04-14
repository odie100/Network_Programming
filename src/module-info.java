module Network_Project {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires org.junit.jupiter.api;
	requires java.sql;
	requires jdk.internal.le;
	requires javafx.base;
	requires org.controlsfx.controls;
	
	opens application to javafx.graphics, javafx.fxml;
	opens controllers;
	opens functions;
	
	exports controllers;
	exports models;
	exports functions;
}
