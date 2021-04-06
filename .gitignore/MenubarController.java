package application;

import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MenubarController {

	@FXML 
	private MenuItem checkOutBook; 

	@FXML 
	private MenuItem editBook;
	
	@FXML
	private MenuItem checkInBook;

	@FXML 
	private MenuItem member;
	
	@FXML
	private MenuItem editMember;

	@FXML 
	private MenuItem overdueReport;
	
	@FXML
	private MenuItem issuedReport;
	
	//menubar that directs the user to different pages
	
	//shows the check out books page
    @FXML 
    void checkOutBooks(ActionEvent event) {  
	try {      
		  URL paneUrl = getClass().getResource("CheckOutReserveBooksView.fxml");
		  AnchorPane anchorPane = FXMLLoader.load( paneUrl );			  
		  BorderPane border = Main.getRoot();
		  border.setCenter(anchorPane);	
		} catch (IOException e) {
			e.printStackTrace();
		}	
     
    }
	
	//shows the check in page
	@FXML 
    void editBooks(ActionEvent event) {      
		try {      
		  URL paneUrl = getClass().getResource("EditBooksView.fxml");
		  AnchorPane anchorPane = FXMLLoader.load( paneUrl );			  
		  BorderPane border = Main.getRoot();
		  border.setCenter(anchorPane);	
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	//shows the book editing page
	@FXML 
    void checkInBooks(ActionEvent event) {      
		try {      
		  URL paneUrl = getClass().getResource("CheckInView.fxml");
		  AnchorPane anchorPane = FXMLLoader.load( paneUrl );			  
		  BorderPane border = Main.getRoot();
		  border.setCenter(anchorPane);	
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	//shows the members that you search for
	@FXML 
    void Members(ActionEvent event) {     
     try {      
		  URL paneUrl = getClass().getResource("MemberView.fxml");
		  AnchorPane anchorPane = FXMLLoader.load( paneUrl );			  
		  BorderPane border = Main.getRoot();
		  border.setCenter(anchorPane);	
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	//shows the member editing page
	@FXML 
    void editMembers(ActionEvent event) {     
     try {      
		  URL paneUrl = getClass().getResource("EditMemberView.fxml");
		  AnchorPane anchorPane = FXMLLoader.load( paneUrl );			  
		  BorderPane border = Main.getRoot();
		  border.setCenter(anchorPane);	
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	//shows the books that are overdue
	@FXML 
    void overdueReports(ActionEvent event) {     
		try {      
		  URL paneUrl = getClass().getResource("OverdueReportsView.fxml");
		  AnchorPane anchorPane = FXMLLoader.load( paneUrl );			  
		  BorderPane border = Main.getRoot();
		  border.setCenter(anchorPane);	
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	//shows the books that were checked out or reserved
	@FXML 
    void issuedReports(ActionEvent event) {     
		try {      
		  URL paneUrl = getClass().getResource("IssuedReportsView.fxml");
		  AnchorPane anchorPane = FXMLLoader.load( paneUrl );			  
		  BorderPane border = Main.getRoot();
		  border.setCenter(anchorPane);	
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}