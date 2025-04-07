package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


@SuppressWarnings("unused")
public class Main extends Application 
{
	@Override
	public void start(Stage primaryStage) 
	{
		Loaders.DisplayScene(primaryStage, "Login");
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
	
}