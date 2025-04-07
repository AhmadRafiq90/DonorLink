package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private Label WelcomeTxt;
    
    public void initialize()
    {
    	WelcomeTxt.setText(DatabaseConnection.getUserName());
    }
    
    @FXML
    void Donate(ActionEvent event) throws IOException 
    {
    	Loaders.DisplayScene("ReceivingRep");
    }

    @FXML
    void ShowInventory(ActionEvent event) throws IOException 
    {
    	Loaders.DisplayScene("Inventory");
    	// Close the current stage
        ((Stage) WelcomeTxt.getScene().getWindow()).close();
    }
    
    @FXML
    void PROFILE(ActionEvent event) throws IOException
    {
    	Loaders.DisplayScene("UserProfile");
    }
    @FXML
    void SignOut(ActionEvent event) throws IOException 
    {
    	Loaders.DisplayScene("Login");
    	// Close the current stage
        ((Stage) WelcomeTxt.getScene().getWindow()).close();
    }

}
