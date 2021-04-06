package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Books {
  private final SimpleStringProperty title;
  private final SimpleStringProperty author;
  private final SimpleStringProperty status;
  private final SimpleStringProperty isbn;
  private final SimpleStringProperty genre;
  private final SimpleIntegerProperty memberid;
  private final SimpleStringProperty checkDate;
  private final SimpleStringProperty dueDate;
  private final SimpleIntegerProperty daysLeft;
  private final SimpleDoubleProperty price;

  
  public Books(
      String title,
      String author,
      String status,
	  String isbn,
	  String genre, 
	  Integer memberid, 
	  String checkDate,
	  String dueDate, 
	  Integer daysLeft,
	  Double price
      ) {
    this.title = new SimpleStringProperty( title );
    this.author = new SimpleStringProperty( author );
    this.status = new SimpleStringProperty( status );
	this.isbn = new SimpleStringProperty( isbn );
	this.genre = new SimpleStringProperty( genre );
	this.memberid = new SimpleIntegerProperty( memberid );
	this.checkDate = new SimpleStringProperty( checkDate );
	this.dueDate = new SimpleStringProperty( dueDate );
	this.daysLeft = new SimpleIntegerProperty( daysLeft );
	this.price = new SimpleDoubleProperty(price);
  }
  
  //get Book title
  public String getTitle() {
    return title.get();
  }
  //set Book title
  public void setTitle(String title) {
    this.title.set( title );
  }
  
  //get Book author
  public String getAuthor() {
    return author.get();
  }
  //set Book author
  public void setAuthor(String author) {
    this.author.set( author );
  }
  
  //get the book's status
  public String getStatus() {
    return status.get();
  }
  //set the status of the book
  public void setStatus(String status) {
    this.status.set( status );
  }
  
  //get the ISBN of the book
  public String getIsbn() {
    return isbn.get();
  }
  //set the book ISBN
  public void setIsbn(String isbn) {
    this.isbn.set( isbn );
  }
  
  //get the book's genre
  public String getGenre() {
    return genre.get();
  }
  //set the genre of the book
  public void setGenre(String genre) {
    this.genre.set( genre );
  }
  
  //get the id of the member who currently has the book
  public Integer getMemberId() {
    return memberid.get();
  }
  //set the id of the member who will have the book
  public void setMemberId(Integer memberid) {
    this.memberid.set( memberid );
  }
  
  //get the date of when it was checked out
  public String getCheckDate() {
    return checkDate.get();
  }
  //set the date of when it is checked out
  public void setCheckDate(String checkDate) {
    this.checkDate.set( checkDate );
  }
  
  //get the date the book will be due
  public String getDueDate() {
    return dueDate.get();
  }
  //set the date of when the book will be due
  public void setDueDate(String dueDate) {
    this.dueDate.set( dueDate );
  }
  
  //get how many days the person can have the book
  public Integer getDaysLeft() {
    return daysLeft.get();
  }
  //set how many days the person can have the book
  public void setDaysLeft(Integer daysLeft) {
    this.daysLeft.set( daysLeft );
  }
  
  //get how much the book costs
  public Double getPrice() {
    return price.get();
  }
  //set the price of the book
  public void setPrice(Double price) {
    this.price.set( price );
  }
  
}