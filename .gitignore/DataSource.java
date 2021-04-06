package application;

import java.util.*;
import java.text.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSource {
  //list of the members in the database
  private ObservableList<Member> memberData = 
	FXCollections.observableArrayList();
//list of the books in the database
  private ObservableList<Books> booksData = 
	FXCollections.observableArrayList();
//list of the books that are overdue, checked out, reserved in  the database
  private ObservableList<Books> overdueIssuedBooksData = 
	FXCollections.observableArrayList();

  /**
   * retrieves the book data in the database
   * @param CharSequence searchWords 	the words/letters the user looked up
   * @param String searchFilter			how the user wants search for the book
   * 
   * @return 		the ObservableList of the books
   */
  public ObservableList<Books> getBooksData(CharSequence searchWords, String searchFilter) {
	 changeDaysLeft();
	 try (Connection conn = this.connect();
		Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery("select * from books")){
		
		if(searchFilter.equals("Title")){
			while (rs.next()) {
				if(rs.getString("title").toUpperCase().contains(searchWords.toString().toUpperCase())
					&& !rs.getString("status").equals("unactivated")){
				  booksData.add( new Books(
					  rs.getString("title"), 
					  rs.getString("author"), 
					  rs.getString("status"), 
					  rs.getString("isbn"),
					  rs.getString("genre"),
					  rs.getInt("memberid"),
					  rs.getString("checked_out_date"), 
					  rs.getString("due_date"), 
					  rs.getInt("days_left"), 
					  rs.getDouble("price")));
				}
			}
		}
		if(searchFilter.equals("Author")){
			while (rs.next()) {
				if(rs.getString("author").toUpperCase().contains(searchWords.toString().toUpperCase())
					&& !rs.getString("status").equals("unactivated")){
				  booksData.add( new Books(
					  rs.getString("title"), 
					  rs.getString("author"), 
					  rs.getString("status"), 
					  rs.getString("isbn"),
					  rs.getString("genre"),
					  rs.getInt("memberid"),
					  rs.getString("checked_out_date"), 
					  rs.getString("due_date"), 
					  rs.getInt("days_left"), 
					  rs.getDouble("price")));                                    
				}
			}
		}
		if(searchFilter.equals("Status")){
			while (rs.next()) {
				if(rs.getString("status").toUpperCase().contains(searchWords.toString().toUpperCase())
					&& !rs.getString("status").equals("unactivated")){
				  booksData.add( new Books(
					  rs.getString("title"), 
					  rs.getString("author"), 
					  rs.getString("status"), 
					  rs.getString("isbn"),
					  rs.getString("genre"),
					  rs.getInt("memberid"),
					  rs.getString("checked_out_date"), 
					  rs.getString("due_date"), 
					  rs.getInt("days_left"), 
					  rs.getDouble("price")));                                   
				}
			}
		}
		if(searchFilter.equals("ISBN")){
			while (rs.next()) {
				if(rs.getString("isbn").contains(searchWords.toString())
					&& !rs.getString("status").equals("unactivated")){
				  booksData.add( new Books(
					  rs.getString("title"), 
					  rs.getString("author"), 
					  rs.getString("status"), 
					  rs.getString("isbn"),
					  rs.getString("genre"),
					  rs.getInt("memberid"),
					  rs.getString("checked_out_date"), 
					  rs.getString("due_date"), 
					  rs.getInt("days_left"), 
					  rs.getDouble("price")));                                     
				}
			}
		}
		if(searchFilter.equals("Genre")){
			while (rs.next()) {
				if(rs.getString("genre").toUpperCase().contains(searchWords.toString().toUpperCase())
					&& !rs.getString("status").equals("unactivated")){
				  booksData.add( new Books(
					  rs.getString("title"), 
					  rs.getString("author"), 
					  rs.getString("status"), 
					  rs.getString("isbn"),
					  rs.getString("genre"),
					  rs.getInt("memberid"),
					  rs.getString("checked_out_date"), 
					  rs.getString("due_date"), 
					  rs.getInt("days_left"), 
					  rs.getDouble("price")));                              
				}
			}
		}
	} catch (SQLException e) {
		System.out.println(e.getMessage());
		return null;
	}
    return booksData;
  }
  
  /**
   * adds a new book to the database
   * @param String bookTitle 	the title of the book
   * @param String bookAuthor	author of the book
   * @param String bookISBN		ISBN of the book
   * @param String bookStatus	status of the book
   * @param String bookGenre	genre of the book
   *
   * @return 		boolean if the data was successfully added
   */
    public boolean setBookData( String bookTitle, String bookAuthor, String bookISBN, String bookStatus, String bookGenre) {
        String sql = "INSERT INTO books(title, author, status, isbn, genre, days_left) VALUES(?, ?, ?, ?, ?, ?)";
 
        try (Connection conn = this.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookTitle);
            pstmt.setString(2, bookAuthor);
			pstmt.setString(3, bookStatus);
			pstmt.setString(4, bookISBN);
			pstmt.setString(5, bookGenre);
			pstmt.setInt(6, 0);
            boolean addedCorrect = pstmt.execute();
			//booksData.add( new Books(bookTitle, bookAuthor, bookStatus, bookISBN, bookGenre, ));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
			return false;
        }
		return false;
    }

  /**
   * removes books in list but 'deactivates' it in the sql
   * @param Books book	the book that the user wants to remove
   */
    public void removeBookData(Books book) {
        String sql = "UPDATE books SET status = ? WHERE id = ?";
		int id = 0;
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			Statement stmt  = conn.createStatement();
			ResultSet rs    = stmt.executeQuery("select * from books");
			id = bookId(book);
			pstmt.setString(1, "unactivated");
			pstmt.setInt(2, id);
            // update 
            pstmt.executeUpdate();
			booksData.remove(book);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
  /**
   * checkout a book by changing the status of the book to 'checked out' and
   * adding the book to the person who checked it out
   * @param Books book 			the book
   * @param boolean checkout	check if the status needs to be changed to 'reserved' or 'checked out'
   *
   * @return 		boolean if the data was successfully added
   */
	public boolean bookCheckOut(Books book, boolean checkout){
		String bookSql = "UPDATE books SET status = ?, memberid = ?, checked_out_date = ?, due_date = ?, days_left = ? WHERE id = ?";
		String deleteSql = "DELETE FROM memberid WHERE id = 1";
		String memberSql = "UPDATE members SET books_held = ? WHERE id = ?";
		int id = 0;
		int memid = 0;
		int numBooks = 0;
		boolean student = false;
        		
		try (Connection conn = this.connect();
		Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery("select * from memberid")){
			while(rs.next()){
				numBooks = rs.getInt("books_held");
				if(rs.getString("memtype").equalsIgnoreCase("student") && numBooks < 5){
					memid = rs.getInt("memId");
					numBooks = numBooks + 1;
					student = true;
				}
				else if(rs.getString("memtype").equalsIgnoreCase("teacher") && numBooks < 150){
					memid = rs.getInt("memId");
					numBooks = numBooks + 1;
					student = false;
				}
				else if(numBooks < 5 || numBooks < 150){
					System.out.println("Over Books");
					return false;
				}
			}
		} catch (SQLException e) {
            System.out.println(e.getMessage());
			return false;
        }
		
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(bookSql)) {
			
			Statement stmt  = conn.createStatement();
			ResultSet rs    = stmt.executeQuery("select * from books");
			while(rs.next()){
				if(rs.getString("isbn").equals(book.getIsbn()) && rs.getString("title").equals(book.getTitle()) )
					id = rs.getInt("id");
			}
			Calendar current = Calendar.getInstance();
			Calendar future = Calendar.getInstance();
			current.setTime(new Date());
			SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yyyy");
			String currentDate = ft.format(current.getTime());
			
			if(checkout)
				pstmt.setString(1, "Checked Out");
			else
				pstmt.setString(1, "Reserved");
			
			pstmt.setInt(2, memid);
			pstmt.setString(3, currentDate);
			if(student){
				future.add(Calendar.DATE, 21);
				pstmt.setInt(5, 21);
			}else {
				future.add(Calendar.DATE, 90);
				pstmt.setInt(5, 90);
			}
			String futureDate = ft.format(future.getTime());
			pstmt.setString(4, futureDate);
			pstmt.setInt(6, id);
            // update 
            pstmt.executeUpdate();
			booksData.remove(book);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
			return false;
        }
		
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(memberSql)) {
			
			Statement stmt  = conn.createStatement();
			ResultSet rs    = stmt.executeQuery("select * from members");
			//check if booksheld in member id can hold the number of books chosen
			while(rs.next()){
				if(rs.getInt("id") == memid ){
					pstmt.setInt(1, numBooks);
					pstmt.setInt(2, memid);
				}
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
            System.out.println(e.getMessage());
			return false;
        }
		
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
			pstmt.executeUpdate();
		} catch (SQLException e) {
            System.out.println(e.getMessage());
			return false;
        }
		
		return true;
	}
	
  /**
   * adds the member to a table 
   * @param Member member 	member who is checking out or reserving a book
   */
	public void addToMemberId(Member member) {
		int memId = getMemberId(member);
		String sql = "INSERT INTO memberid(memid, memtype, books_held) VALUES(?, ?, ?)";
 
        try (Connection conn = this.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, memId);
			pstmt.setString(2, member.getMemberType());
			pstmt.setInt(3, member.getNumBooks());
            // update 
            pstmt.executeUpdate();
		} catch (SQLException e) {
            System.out.println(e.getMessage());
		}
	}
	
	private int getMemberId(Member member) {
		int id = 0;
		try (Connection conn = this.connect();
		Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery("select * from members")){
			while(rs.next()){
				if(rs.getString("first_name").equalsIgnoreCase(member.getFirstName()) 
					&& rs.getString("last_name").equalsIgnoreCase(member.getLastName()) )
					id = rs.getInt("id");
			}
		} catch (SQLException e) {
            System.out.println(e.getMessage());
		}
		return id;
	}
	
  /**
   * changes the status to available 
   * @param Books book 	book that is being checked in
   */
	public void changeStatus(Books book){
		String bookSql = "UPDATE books SET status = ?, memberid = ? WHERE id = ?";
		int memberid = 0;
		int id = 0;
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(bookSql)) {
			
			Statement stmt  = conn.createStatement();
			ResultSet rs    = stmt.executeQuery("select * from books");
			id = bookId(book);
			pstmt.setString(1, "Available");
			pstmt.setInt(2, memberid);
			pstmt.setInt(3, id);
            // update 
            pstmt.executeUpdate();
			booksData.remove(book);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
  /**
   * adds a new book to the database
   * @param String str 		the status the books have to be in
   * @param boolean overdue	determine if the books being added are overdue or reserved/checked out
   *
   * @return 		observablelist of the books that of a certain status
   */
	public ObservableList<Books> getOverdueIssuedBooks(String str, boolean overdue){
		try (Connection conn = this.connect();
		Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery("select * from books")){
			while(rs.next()){
				if(overdue){
					if(rs.getString("status").equalsIgnoreCase(str)){
						overdueIssuedBooksData.add( new Books(
						  rs.getString("title"), 
						  rs.getString("author"), 
						  rs.getString("status"), 
						  rs.getString("isbn"),
						  rs.getString("genre"),
						  rs.getInt("memberid"),
						  rs.getString("checked_out_date"), 
						  rs.getString("due_date"), 
						  rs.getInt("days_left"), 
						  rs.getDouble("price") ));
					}
				} else {
					if(rs.getString("status").equalsIgnoreCase(str) || rs.getString("status").equalsIgnoreCase("Reserved")){
						overdueIssuedBooksData.add( new Books(
						  rs.getString("title"), 
						  rs.getString("author"), 
						  rs.getString("status"), 
						  rs.getString("isbn"),
						  rs.getString("genre"),
						  rs.getInt("memberid"),
						  rs.getString("checked_out_date"), 
						  rs.getString("due_date"), 
						  rs.getInt("days_left"), 
						  rs.getDouble("price") ));
					}
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return overdueIssuedBooksData;
	}
	
  /**
   * updates the changes to the book into the database
   * @param String title 	the change in title of the book
   * @param String author	the change in author of the book
   * @param String isbn		the change in ISBN of the book
   * @param Double price	the change in price of the book
   * @param String status	the change in status of the book
   * @param String genre	the change in genre of the book
   * @param Books book		book that it being updated
   */
	public void saveChanges(String title, String author, String isbn, Double price, String status, String genre, Books book) {
		String sql = "UPDATE books SET title = ?, author = ?, status = ?, isbn = ?, genre = ?, price = ? WHERE id = ?";	
		int id = bookId(book);
		
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, title);
			pstmt.setString(2, author);
			pstmt.setString(3, status);
			pstmt.setString(4, isbn);
			pstmt.setString(5, genre);
			pstmt.setDouble(6, price);
			pstmt.setInt(7, id);
            // update 
            pstmt.executeUpdate();
			booksData.remove(book);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
  /**
   * returns what number the book is in the database list
   * @param Books book 	book that were finding
   *
   * @return 	number of the book in the list
   */
	private int bookId(Books book){
		int id = 0;
		try (Connection conn = this.connect();
		Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery("select * from books")){
			while(rs.next()){
				if(rs.getString("isbn").equals(book.getIsbn()) && rs.getString("title").equals(book.getTitle()) )
					id = rs.getInt("id");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return id;
	}
	
  /**
   * calculates how many days are left for the person to return their book by 
   * comparing the due date to the current date
   */
	private void changeDaysLeft(){
		String sql = "UPDATE books SET days_left = ?, status = ? WHERE id = ?";
		int daysLeft = 0;
		int id = 0;
		try (Connection conn = this.connect();
		Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery("select * from books")){
			while(rs.next()) {
				if(rs.getString("status").equalsIgnoreCase("checked out") || rs.getString("status").equalsIgnoreCase("reserved")){
					Calendar current = Calendar.getInstance();
					current.setTime(new Date());
					Calendar cal = Calendar.getInstance();
					//String currentDate = current.getTime();
					SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yyyy");
					String dueDate = rs.getString("due_date"); 
					try{
						Date returnDate = ft.parse(dueDate);
						cal.setTime(returnDate);
					} catch (ParseException e) {
						System.out.println(e.getMessage());
					}
					long millis1 = current.getTimeInMillis();
					long millis2 = cal.getTimeInMillis();
					long diff = millis2 - millis1;
					long diffDays = diff / (24 * 60 * 60 * 1000);
					daysLeft = (int) diffDays;
					id = rs.getInt("id");
					String stat = rs.getString("status");
					
					try(PreparedStatement pstmt = conn.prepareStatement(sql)){
						pstmt.setInt(1, daysLeft);
						if(daysLeft < 0)
							pstmt.setString(2, "Overdue");
						else 
							pstmt.setString(2, stat);
						pstmt.setInt(3, id);
						pstmt.executeUpdate();
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
				}					
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	

/* ----------------------------- Member methods -------------------------------*/
	
  /**
   * retrieves the member data in the database
   * @param CharSequence searchWords 	the words/letters the user looked up
   * @param String searchFilter			how the user wants search for the members
   * 
   * @return 		the ObservableList of the members
   */
	public ObservableList<Member> getMemberData(CharSequence searchWords, String searchFilter) {
	try (Connection conn = this.connect();
		Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery("select * from members")){
		if(searchFilter.equals("First Name")){
			while (rs.next()) {
				if(rs.getString("first_name").toUpperCase().contains(searchWords.toString().toUpperCase())){
				  memberData.add( new Member(
					  rs.getString("first_name"), 
					  rs.getString("last_name"),
					  rs.getString("middle_name"),
					  rs.getString("member_title"),
					  rs.getInt("books_held") ));  
				}
			}
		}
		if(searchFilter.equals("Last Name")){
			while (rs.next()) {
				if(rs.getString("last_name").toUpperCase().contains(searchWords.toString().toUpperCase())){
				  memberData.add( new Member(
					  rs.getString("first_name"), 
					  rs.getString("last_name"),
					  rs.getString("middle_name"),
					  rs.getString("member_title"),
					  rs.getInt("books_held") ));  
				}
			}
		}
	} catch (SQLException e) {
		System.out.println(e.getMessage());
		return null;
	}
    return memberData;
  }

  /**
   * adds a new member to the database
   * @param String firstName 	first name of the person
   * @param String lastName		last name of the person
   * @param String midName		middle name of the person (if they have one)
   * @param String type			tells whether the new member is a student or teacher
   *
   * @return 		boolean if the data was successfully added
   */
	public boolean setMemberData( String firstName, String lastName, String midName, String type) {
        String sql = "INSERT INTO members(first_name, last_name, middle_name, member_title, books_held) VALUES(?, ?, ?, ?, ?)";
 
        try (Connection conn = this.connect();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
			pstmt.setString(3, midName);
			pstmt.setString(4, type);
			pstmt.setInt(5, 0);
            boolean addedCorrect = pstmt.execute();
			//booksData.add( new Books(bookTitle, bookAuthor, bookStatus, bookISBN, bookGenre, ));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
			return false;
        }
		return false;
    }
	
  /**
   * removes a person from the list but 'deactivates' the person in the database
   * @param Member member	member that is being removed 	
   */
	public void removeMemberData(Member member) {
        String sql = "UPDATE member SET status = ? WHERE id = ?";
		int id = 0;
 
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			Statement stmt  = conn.createStatement();
			ResultSet rs    = stmt.executeQuery("select * from member");
			while(rs.next()){
				if(rs.getString("first_name").equals(member.getFirstName()) && rs.getString("last_name").equals(member.getLastName()) )
					id = rs.getInt("id");
			}
			pstmt.setString(1, "unactivated");
			pstmt.setInt(2, id);
            // update 
            pstmt.executeUpdate();
			memberData.remove(member);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
  /**
   * updates changes to the database of the member
   * @param String firstName 	first name of the person
   * @param String lastName		last name of the person
   * @param String midName		middle name of the person (if they have one)
   * @param String type			tells whether the new member is a student or teacher
   * @param Member member		member that is being updated
   * 
   */
	public void saveMemChanges(String firstName, String lastName, String midName, String type, Member member) {
		String sql = "UPDATE member SET first_name = ?, last_name = ?, middle_name = ?, member_title = ? WHERE id = ?";	
		int id = memberId(member);
		
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, midName);
			pstmt.setString(4, type);
			pstmt.setInt(5, id);
            // update 
            pstmt.executeUpdate();
			memberData.remove(member);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
  /**
   * returns the number of where the member is in the database
   * @param Member member	member that user is trying to find
   *
   * @return 		int of where the member is
   */
	private int memberId(Member member){
		int id = 0;
		try (Connection conn = this.connect();
		Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery("select * from members")){
			while(rs.next()){
				if(rs.getString("first_name").equals(member.getFirstName()) && rs.getString("last_name").equals(member.getLastName()) )
					id = rs.getInt("id");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return id;
	}
	

/* --------------------------- sqlite connections ---------------------------- */
  
  /**
   * makes a connection from this java file to the database
   */
  private Connection connect() {
        String url = "jdbc:sqlite:test.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
 
	  public boolean runUpdate(String sql) {
       try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
				return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
			return false;
        }
		
    }

}