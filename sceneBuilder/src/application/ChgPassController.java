package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ChgPassController {

    @FXML
    private PasswordField ConfirmPass;

    @FXML
    private PasswordField NewPass;

    @FXML
    private PasswordField OldPass;

    @FXML
    void Update(ActionEvent event) throws SQLException 
    {
    	if (ConfirmPass.getText().isBlank() || 
    			NewPass.getText().isBlank() || 
    			OldPass.getText().isBlank())
    	{
    		new Alert(Alert.AlertType.ERROR, "Please enter all fields").showAndWait();
    		return;
    	}
    	if (OldPass.getText().equals(DatabaseConnection.getSpecificColumn("Password")))
    	{
    		if (NewPass.getText().equals(ConfirmPass.getText()))
    		{
    			Connection conn = DatabaseConnection.getConnection();
    			String query = "Update person set Password = ? where person_id = ?";
    			PreparedStatement pst = conn.prepareStatement(query);
    			
    			pst.setString(1, NewPass.getText());
    			pst.setInt(2, Integer.parseInt(DatabaseConnection.getSpecificColumn("person_id")));
    			
    			if (pst.executeUpdate() > 0)
    			{
    				new Alert(Alert.AlertType.INFORMATION, "Password successfully updated.").showAndWait();
    				((Stage) NewPass.getScene().getWindow()).close();
    			}
    			else 
    				new Alert(Alert.AlertType.ERROR, "An unknown error occured").showAndWait();
    		}
    		else
    			new Alert(Alert.AlertType.ERROR, "New passwords must be same").showAndWait();
    	}
    	else
    		new Alert(Alert.AlertType.ERROR, "Passwords do not match").showAndWait();
    }

}
