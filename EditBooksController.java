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

public class EditBooksController{
	private Books books;
	
	@FXML 
	private TextField book;
	
	@FXML 
	private TextField title;
	
	@FXML 
	private TextField author;
	
	@FXML 
	private TextField isbn;
	
	@FXML
	private TextField price;
	
	@FXML 
	private TextField title2;
	
	@FXML 
	private TextField author2;
	
	@FXML 
	private TextField isbn2;
	
	@FXML
	private TextField price2;
	
	@FXML 
	private ComboBox<String> genreChoice;
	
	@FXML 
	private ComboBox<String> statusChoice;
	
	@FXML 
	private ComboBox<String> genreChoice2;
	
	@FXML 
	private ComboBox<String> statusChoice2;
	
	@FXML 
	private ComboBox<String> searchChoice;
	
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
	
	ObservableList<String> listChoice = FXCollections.observableArrayList("Title", "Author", "Status", "ISBN", "Genre");
	
	ObservableList<String> genreList = FXCollections.observableArrayList("NonFiction", "Fiction");
	
	ObservableList<String> statusList = FXCollections.observableArrayList("Available", "Damaged", "Other");
	
	@FXML 
    protected void initialize() {
		genreChoice.setValue("Fiction");
		genreChoice.setItems(genreList);
		
		statusChoice.setValue("Available");
		statusChoice.setItems(statusList);
		
		genreChoice2.setItems(genreList);
		
		statusChoice2.setItems(statusList);
		
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
		  
		PropertyValueFactory<Books, Double> priceProperty = 
          new PropertyValueFactory<Books, Double>("price");
      
		titleColumn.setCellValueFactory( titleProperty );
		authorColumn.setCellValueFactory( authorProperty );
		statusColumn.setCellValueFactory( statusProperty );
		ISBNColumn.setCellValueFactory( ISBNProperty );
		genreColumn.setCellValueFactory( genreProperty );
		priceColumn.setCellValueFactory( priceProperty );
	}
	
	/**
	 * saves the new book to the database
	 */
	@FXML
	void saveBooks(ActionEvent event){
		String bookTitle = title.getText();
		String bookAuthor = author.getText();
		String bookISBN = isbn.getText();
		String status = statusChoice.getValue();
		String genre = genreChoice.getValue();
		DataSource data = new DataSource();
		if(!bookTitle.equals("") && !bookAuthor.equals("") && !bookISBN.equals("") ){
			boolean addedCorrect = data.setBookData(bookTitle, bookAuthor, bookISBN, status, genre);
			if(!addedCorrect){
				System.out.println("saved");
				title.clear();
				author.clear();
				isbn.clear();
			}else
				System.out.println("not saved, error");
		} else 
			System.out.println("empty");
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
	 * allows the user to edit a book
	 */
	@FXML
	void editBooks(ActionEvent event){
		books = booksTable.getSelectionModel().getSelectedItem();
		title2.setText(books.getTitle());
		author2.setText(books.getAuthor());
		isbn2.setText(books.getIsbn());
		price2.setText(books.getPrice().toString());
		statusChoice2.setValue(books.getStatus());
		genreChoice2.setValue(books.getGenre());
		
	}
	
	/**
	 * allows the user to remove a book
	 */
	@FXML
	void removeBooks(ActionEvent event){
		books = booksTable.getSelectionModel().getSelectedItem();
		DataSource data = new DataSource();
		data.removeBookData(books);
	}
	
	/**
	 * saves the changes the user made to the book
	 */
	@FXML
	void updateBooks(ActionEvent event){
		DataSource data = new DataSource();
		data.saveChanges(title2.getText(), author2.getText(), isbn2.getText(), Double.parseDouble(price2.getText()), statusChoice2.getValue(), genreChoice2.getValue(), books);
		title2.clear();
		author2.clear();
		isbn2.clear();
		price2.clear();
	}
	
}