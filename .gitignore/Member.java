package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Member {
	private final SimpleStringProperty firstName;
	private final SimpleStringProperty lastName;
	private final SimpleStringProperty midName;	
	private final SimpleStringProperty memberType;
	private final SimpleIntegerProperty numBooks;

  
  public Member(
      String firstName,
      String lastName,
	  String midName,
	  String memberType,
	  Integer numBooks
      ) {
    this.firstName = new SimpleStringProperty( firstName );
    this.lastName = new SimpleStringProperty( lastName );
	this.midName = new SimpleStringProperty( midName );
	this.memberType = new SimpleStringProperty( memberType );
	this.numBooks = new SimpleIntegerProperty( numBooks );
  }
  
  //gets the first name of the person
  public String getFirstName() {
    return firstName.get();
  }
  //set the first name of the person
  public void setFirstName(String firstName) {
    this.firstName.set( firstName );
  }
  
  
  //get the last name of the person
  public String getLastName() {
    return lastName.get();
  }
  //set the last name of the person
  public void setLastName(String lastName) {
    this.lastName.set( lastName );
  }
  
  
  //get the middle name of the person if they have one
  public String getMidName() {
    return midName.get();
  }
  //set the middle name of the person if they have one
  public void setMidName(String midName) {
    this.midName.set( midName );
  }
  
  
  //get the type of member they are: student or teacher
  public String getMemberType() {
    return memberType.get();
  }
  //set the type of member they are: student or teacher
  public void setMemberType(String memberType) {
    this.memberType.set( memberType );
  }
  
  
  //get the number of books they have checked out
  public Integer getNumBooks() {
    return numBooks.get();
  }
  //set the number of books they have checked out
  public void setNumBooks(Integer numBooks) {
    this.numBooks.set( numBooks );
  }
  
}