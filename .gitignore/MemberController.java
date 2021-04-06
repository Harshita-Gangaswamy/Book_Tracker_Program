package application;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

public class MemberController {
	@FXML 
	private TextField member;
	
	@FXML
    private TableView<Member> memberTable;

    @FXML 
    private TableColumn<Member, String> firstNameColumn;

    @FXML 
    private TableColumn<Member, String> lastNameColumn;
	
	@FXML
	private TableColumn<Member, String> midNameColumn;
	
	@FXML
	private TableColumn<Member, String> memberTypeColumn;
	
	@FXML
	private TableColumn<Member, Integer> numBooksColumn;
	
	@FXML 
	private ComboBox<String> searchChoice;
	
	ObservableList<String> listChoice = FXCollections.observableArrayList("First Name", "Last Name");

    @FXML 
    protected void initialize() {
      searchChoice.setValue("First Name");
	  searchChoice.setItems(listChoice);
	  
      //setting up the columns
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
	  String searchFilter = searchChoice.getValue();
      ObservableList<Member> tableItems = data.getMemberData(searchWords, searchFilter);
      memberTable.setItems( tableItems );
	
	}
    
}