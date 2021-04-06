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

public class OverdueReportsController {
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
    private TableColumn<Books, Double> priceColumn;
	
	@FXML 
    protected void initialize() {

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
		
		PropertyValueFactory<Books, Double> priceProperty = 
		  new PropertyValueFactory<Books, Double>("price");
      
		titleColumn.setCellValueFactory( titleProperty );
		authorColumn.setCellValueFactory( authorProperty );
		statusColumn.setCellValueFactory( statusProperty );
		ISBNColumn.setCellValueFactory( ISBNProperty );
		genreColumn.setCellValueFactory( genreProperty );
		priceColumn.setCellValueFactory( priceProperty );
		
		//gets the books that are overdue and displays them
		DataSource data = new DataSource();
		String filter = "overdue";
		boolean overdue = true;
		ObservableList<Books> tableItems = data.getOverdueIssuedBooks(filter, overdue);
		booksTable.setItems( tableItems );
    }

}