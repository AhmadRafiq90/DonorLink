package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RequestController {

    @FXML
    private TextField Age;

    @FXML
    private ChoiceBox<String> BType;

    @FXML
    private DatePicker Date;

    @FXML
    private ChoiceBox<String> Gender;
    
    public void initialize() throws SQLException
    {
    	BType.setValue(DatabaseConnection.getSpecificColumn("Bloodtype"));
    	Gender.setValue(DatabaseConnection.getSpecificColumn("Gender"));
    	int age = DatabaseConnection.calculateAge();
    	Age.setText(Integer.toString(age));
    }
    
    @FXML
    void Request(ActionEvent event) throws SQLException, IOException 
    {
    	LocalDate enteredDate = Date.getValue();
    	LocalDate currentDate = LocalDate.now();
    	
    	if (enteredDate.isBefore(currentDate))
    	{
    		new Alert(Alert.AlertType.ERROR, "Invalid Date entered").showAndWait();
    		return;
    	}
    	
    	int id = Integer.parseInt(DatabaseConnection.getSpecificColumn("person_id"));
    	Connection conn = DatabaseConnection.getConnection();
    	PreparedStatement pst = 
    			conn.prepareStatement(
    					"INSERT INTO requests (UserID, Date, BloodType) Values (?,?,?)");
    	pst.setInt(1, id);
    	pst.setString(2, Date.getValue().toString());
    	pst.setString(3, DatabaseConnection.getSpecificColumn("Bloodtype"));
    	
    	if (pst.executeUpdate() > 0)
    	{
    		new Alert(Alert.AlertType.INFORMATION, "Your appointment is successfully scheduled on " +
    			Date.getValue().toString() + ".").showAndWait();
    		((Stage) Date.getScene().getWindow()).close();
    		Loaders.DisplayScene("Feedback");
    		return;
    	}
    	new Alert(Alert.AlertType.ERROR, "An unknown error occured.").showAndWait();
    }

}
