package application;
	
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
  private static BorderPane root = new BorderPane();
//starts the application or program
  public static BorderPane getRoot() {
    return root;
  }
  
  @Override
  public void start(Stage primaryStage) throws IOException {
   // URL menuBarUrl = getClass().getResource("mymenus.fxml");
	//MenuBar bar = FXMLLoader.load( menuBarUrl );
	
    URL url = getClass().getResource("LoginView.fxml");
    AnchorPane pane = FXMLLoader.load( url );
	
	
	//root.setTop(bar);
	root.setCenter(pane);
	
    Scene scene = new Scene( root,800, 600 );
	
	primaryStage.setScene( scene );
    primaryStage.setTitle( "JavaFX TableView" );
    primaryStage.show();
  }
  
	public static void main(String[] args) {
		launch(args);
	}
}