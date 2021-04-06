package application;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.TextField;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class LoginController {
	@FXML 
    private TextField loginName;
	
	@FXML 
    private TextField passwordName;

   	// takes to the next page
	@FXML 
    void userLogin(ActionEvent event) {
		try {
		  URL menuBarUrl = getClass().getResource("mymenus.fxml");
		  URL paneUrl = getClass().getResource("CheckOutReserveBooksView.fxml");
		  MenuBar bar = FXMLLoader.load( menuBarUrl );
		  AnchorPane anchorPane = FXMLLoader.load( paneUrl );
		  BorderPane border = Main.getRoot();
		  border.setTop(bar); 
		  
		  border.setCenter(anchorPane);		  
		} catch (IOException e) {
			e.printStackTrace();
		}	
	
	}
	//clear textfields
	@FXML 
    void clearLogin(ActionEvent event) {
		loginName.setText("");
		passwordName.setText("");
	}
    
}