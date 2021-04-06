package application;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class CheckOutReserveBooksController {
	@FXML 
	private TextField member;
	
	@FXML // fx:id="customerTable"
    private TableView<Member> memberTable;

    @FXML // fx:id="firstNameColumn"
    private TableColumn<Member, String> firstNameColumn;

    @FXML // fx:id="lastNameColumn"
    private TableColumn<Member, String> lastNameColumn;
	
	@FXML
	private TableColumn<Member, String> midNameColumn;
	
	@FXML
	private TableColumn<Member, String> memberTypeColumn;
	
	@FXML
	private TableColumn<Member, Integer> numBooksColumn;
	
	@FXML 
	private ComboBox<String> memberChoice;
	
	ObservableList<String> memberList = FXCollections.observableArrayList("First Name", "Last Name");
	
    @FXML 
    protected void initialize() {
		memberChoice.setValue("First Name");
		memberChoice.setItems(memberList);

      PropertyValueFactory<Member, String> firstNameProperty = 
          new PropertyValueFactory<Member, String>("firstName");
      
      PropertyValueFactory<Member, String> lastNameProperty = 
          new PropertyValueFactory<Member, String>("lastName");
		  
	  PropertyValueFactory<Member, String> midNameProperty = 
          new PropertyValueFactory<Member, String>("midName");
		  
	  PropertyValueFactory<Member, String> memberTypeProperty = 
          new PropertyValueFactory<Member, String>("memberType");
	  
	  PropertyValueFactory<Member, Integer> numBooksProperty = 
          new PropertyValueFactory<Member, Integer>("numBooks");
      
      firstNameColumn.setCellValueFactory( firstNameProperty );
      lastNameColumn.setCellValueFactory( lastNameProperty );
	  midNameColumn.setCellValueFactory( midNameProperty );
	  memberTypeColumn.setCellValueFactory( memberTypeProperty );
	  numBooksColumn.setCellValueFactory( numBooksProperty );
     
    }
	
	/**
	 * finds the members that the user was searching for and then 
	 * then adds the result to the table
	 */
	@FXML 
    void findMember(ActionEvent event) {
	  DataSource data = new DataSource();
	  CharSequence searchWords = member.getText();
	  String searchFilter = memberChoice.getValue();
      ObservableList<Member> tableItems = data.getMemberData(searchWords, searchFilter);
      memberTable.setItems( tableItems );
	}
	
	/**
	 * makes sure the member can check out a book or reserve a book
	 */
	@FXML
	void checkOutReserveBooks(ActionEvent event) {
		Member member = memberTable.getSelectionModel().getSelectedItem();
		DataSource data = new DataSource();
		data.addToMemberId(member);
		int num = member.getNumBooks();
		if(num < 5 && member.getMemberType().equalsIgnoreCase("student")){
			changePage();
		} else if(num < 150 && member.getMemberType().equalsIgnoreCase("teacher")){
			changePage();
		} else {
			System.out.println("over books");
		}
	}
	
	/**
	 * changes the page to the page to choose what books the user wants to check out
	 */
	private void changePage() {
		try {      
		  URL paneUrl = getClass().getResource("BooksView.fxml");
		  AnchorPane anchorPane = FXMLLoader.load( paneUrl );			  
		  BorderPane border = Main.getRoot();
		  border.setCenter(anchorPane);	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
}