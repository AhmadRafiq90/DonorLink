package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class LoginController {
	
	void handleSignIn()
	{
    	if (UserName.getText().isBlank())
    	{
    		new Alert(Alert.AlertType.ERROR, "Please enter username", ButtonType.OK).show();
    		return;
    	}
    	if (Password.getText().isBlank())
    	{
    		new Alert(Alert.AlertType.ERROR, "Please enter password", ButtonType.OK).show();
    		return;
    	}
    	
    	Connection conn = DatabaseConnection.getConnection();
    	
    	try
    	{
    		String query = "select firstName,lastName from person where UserName = '" + UserName.getText()
    				+ "' and password = '" + Password.getText() + "'";
    		Statement st = conn.createStatement();
    		ResultSet Output = st.executeQuery(query);
    		
    		if (Output.next())
    		{
    			DatabaseConnection.setUserName(
    					UserName.getText());
    			new Alert(Alert.AlertType.INFORMATION, "Log In Successfull").showAndWait();
    			
    			if (DatabaseConnection.getSpecificColumn("Role").equals("Admin"))
    			// Load the new scene from FXML
    				Loaders.DisplayScene("AdminHome");
    			else 
    				Loaders.DisplayScene("Home");
                // Close the current stage
                ((Stage) SignIn.getScene().getWindow()).close();
    		}
    		else
    		{
    			new Alert(Alert.AlertType.ERROR, "Incorrect username or password").show();
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
	}
    @FXML
    private PasswordField Password;

    @FXML
    private Button Register;

    @FXML
    private Button SignIn;

    @FXML
    private TextField UserName;
    
    @FXML
    void KeyChecker(KeyEvent event) 
    {
    	if (event.getCode() == KeyCode.ENTER)
    		handleSignIn();
    }
    
    @FXML
    void RegisterPressed(ActionEvent event) 
    {
    	try {
			Loaders.DisplayScene("Register");
			 // Close the current stage
            ((Stage) Register.getScene().getWindow()).close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    @FXML
    void SignInPressed(ActionEvent event) 
    {
    	handleSignIn();
    }

}