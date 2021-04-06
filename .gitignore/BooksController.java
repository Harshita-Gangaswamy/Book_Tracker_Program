package application;

import java.util.*;
import java.text.*;
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


public class BooksController {
	//tells whether the book will be checked out or reserved
	private boolean checkout = false;
	
	@FXML 
	private TextField book;
	
	@FXML 
    private TableView<Books> booksTable;

    @FXML 
    private TableColumn<Books, String> titleColumn;

    @FXML 
    private TableColumn<Books, String> authorColumn;

    @FXML 
    private TableColumn<Books, String> statusColumn;
	
	@FXML 
    private TableColumn<Books, String> ISBNColumn;
	
	@FXML 
    private TableColumn<Books, String> genreColumn;
	
	@FXML 
	private ComboBox<String> searchChoice;
	
	ObservableList<String> listChoice = FXCollections.observableArrayList("Title", "Author", "Status", "ISBN", "Genre");

    @FXML 
    protected void initialize() {
		searchChoice.setValue("Title");
		searchChoice.setItems(listChoice);

		PropertyValueFactory<Books, String> titleProperty = 
          new PropertyValueFactory<Books, String>("title");
      
		PropertyValueFactory<Books, String> authorProperty = 
          new PropertyValueFactory<Books, String>("author");
      
		PropertyValueFactory<Books, String> statusProperty = 
          new PropertyValueFactory<Books, String>("status");
		  
		PropertyValueFactory<Books, String> ISBNProperty = 
          new PropertyValueFactory<Books, String>("isbn");
		  
		PropertyValueFactory<Books, String> genreProperty = 
          new PropertyValueFactory<Books, String>("genre");
      
		titleColumn.setCellValueFactory( titleProperty );
		authorColumn.setCellValueFactory( authorProperty );
		statusColumn.setCellValueFactory( statusProperty );
		ISBNColumn.setCellValueFactory( ISBNProperty );
		genreColumn.setCellValueFactory( genreProperty );
    }

	/**
	 *searches for the books and puts the result into the table
	 */
	@FXML 
    void findBooks(ActionEvent event) {		
		DataSource data = new DataSource();
		CharSequence searchWords = book.getText();
		String searchFilter = searchChoice.getValue();
		ObservableList<Books> tableItems = data.getBooksData(searchWords, searchFilter);
		booksTable.setItems( tableItems );
	}
	
	/**
	 * takes the book that the user selected and checks it out 
	 * and then changes the page to main page
	 */
	@FXML
	void checkOutBooks(ActionEvent event){
		Books books = booksTable.getSelectionModel().getSelectedItem();
		DataSource data = new DataSource();
		checkout = true;
		boolean check = data.bookCheckOut(books, checkout);
		if(check){
			changePage();
		} else {
			System.out.println("reselect books");
		}
	}
	
	/**
	 * takes the book that the user selected and checks it out 
	 * and then changes the page to main page
	 */
	@FXML
	void reserveBooks(ActionEvent event) {
		Books books = booksTable.getSelectionModel().getSelectedItem();
		DataSource data = new DataSource();
		boolean check = data.bookCheckOut(books, checkout);
		if(check){
			changePage();
		} else {
			System.out.println("reselect books");
		}
	}
	
	/**
	 * changes the page to the main check out page
	 */
	private void changePage() {
		try {      
		  URL paneUrl = getClass().getResource("CheckOutReserveBooksView.fxml");
		  AnchorPane anchorPane = FXMLLoader.load( paneUrl );			  
		  BorderPane border = Main.getRoot();
		  border.setCenter(anchorPane);	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
}