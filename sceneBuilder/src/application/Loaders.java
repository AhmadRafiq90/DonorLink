package application;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Loaders 
{
	public static void DisplayScene(String Page) throws IOException
	{
		 final String path = "D:\\Programs\\data\\sceneBuilder\\src\\" + Page + ".fxml";
	     FXMLLoader loader = new FXMLLoader(new File(path).toURI().toURL());
         Parent newScene = loader.load();

         // Create a new stage for the new scene
         Stage newStage = new Stage();
         newStage.setScene(new Scene(newScene));
         newStage.setResizable(false);
         // Show the new stage
         newStage.show();
	}
	public static void DisplayScene(Stage primaryStage, String Page)
	{
		try
		{
			final String path = "D:\\Programs\\data\\sceneBuilder\\src\\" + Page + ".fxml";
			Parent root = FXMLLoader.load(new File(path).toURI().toURL());
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
