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
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class EditMemberController {
	private Member mem;
	
	@FXML
	private TextField firstName;
	
	@FXML
	private TextField lastName;
	
	@FXML
	private TextField midName;
	
	@FXML
	private TextField firstName2;
	
	@FXML
	private TextField lastName2;
	
	@FXML
	private TextField midName2;
	
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
	private ComboBox<String> memberType;
	
	@FXML 
	private ComboBox<String> memberType2;
	
	@FXML 
	private ComboBox<String> searchChoice;
	
	ObservableList<String> listChoice = FXCollections.observableArrayList("First Name", "Last Name");
	
	ObservableList<String> memberTypes = FXCollections.observableArrayList("Student", "Teacher");
	
	@FXML 
    protected void initialize() {
      searchChoice.setValue("First Name");
	  searchChoice.setItems(listChoice);
	  
	  memberType.setValue("Student");
	  memberType.setItems(memberTypes);
	  
	  memberType2.setItems(memberTypes);
	  
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
	
	/**
	 * adds the new member to the database
	 */
	@FXML
	void saveMember(ActionEvent event){
		String first = firstName.getText();
		String last = lastName.getText();
		String mid = midName.getText();
		String type = memberType.getValue();
		DataSource data = new DataSource();
		if(!first.equals("") && !last.equals("")){
			boolean addedCorrect = data.setMemberData(first, last, mid, type);
			if(!addedCorrect){
				System.out.println("saved");
				firstName.clear();
				lastName.clear();
				midName.clear();
			}else
				System.out.println("not saved, error");
		} else 
			System.out.println("empty");
	}
	
	/**
	 * allows the user to edit information about the member
	 */
	@FXML
	void editMember(ActionEvent event){
		mem = memberTable.getSelectionModel().getSelectedItem();
		firstName2.setText(mem.getFirstName());
		lastName2.setText(mem.getLastName());
		midName2.setText(mem.getMidName());
		memberType2.setValue(mem.getMemberType());
	}
	
	/**
	 * allows the user to remove the member
	 */
	@FXML
	void removeMember(ActionEvent event){
		mem = memberTable.getSelectionModel().getSelectedItem();
		DataSource data = new DataSource();
		data.removeMemberData(mem);
	}
	
	/**
	 * updates changed information about the member into the database
	 */
	@FXML
	void updateMember(ActionEvent event){
		DataSource data = new DataSource();
		String first = firstName2.getText();
		String last = lastName2.getText();
		String mid = midName2.getText();
		String type = memberType2.getValue();
		
		data.saveMemChanges(first, last, mid, type, mem);
		firstName2.clear();
		lastName2.clear();
		midName2.clear();
	}
	
}