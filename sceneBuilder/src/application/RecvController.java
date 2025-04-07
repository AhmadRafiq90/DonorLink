package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RecvController{
	
    @FXML
    private TextField Age;

    @FXML
    private ChoiceBox<String> BType;

    @FXML
    private TextField DiasBP;

    @FXML
    private ChoiceBox<String> Gender;
    
    @FXML
    private TextField HB;

    @FXML
    private TextField SysBP;
    
    @FXML
    public void initialize() throws SQLException
    {
    	//String sID = 
    	int id = Integer.parseInt(DatabaseConnection.getSpecificColumn("person_id"));
    	BType.setValue(DatabaseConnection.getSpecificColumn("Bloodtype"));
    	Gender.setValue(DatabaseConnection.getSpecificColumn("Gender"));
    	SysBP.setText(DatabaseConnection.getSpecificColumn("eligible_donors", "SystolicBP", "DonorID", Integer.toString(id)));
    	DiasBP.setText(DatabaseConnection.getSpecificColumn("eligible_donors", "DiastolicBP", "DonorID", Integer.toString(id)));
    	HB.setText(DatabaseConnection.getSpecificColumn("eligible_donors", "HB", "DonorID", Integer.toString(id)));
    	int age = DatabaseConnection.calculateAge();
    	Age.setText(Integer.toString(age));
    }

    @FXML
    void Check(ActionEvent event) throws NumberFormatException, SQLException, IOException 
    {
    	if (DiasBP.getText().isBlank() || SysBP.getText().isBlank())
    	{
    		new Alert(Alert.AlertType.ERROR, "Please enter blood pressure").show();
    		return;
    	}
    	if (HB.getText().isBlank())
    	{
    		new Alert(Alert.AlertType.ERROR, "Please enter HB Levels").show();
    		return;
    	}
    	
    	int higherLimit = Integer.parseInt(SysBP.getText());
    	int lowerLimit = Integer.parseInt(DiasBP.getText());
    	float Hemo = Float.parseFloat(HB.getText());
    	
    	if (higherLimit < 180 && lowerLimit < 100)
    	{
    		if ((Gender.getValue().equals("Male") && Hemo >= 13) || 
    			 Gender.getValue().equals("Female") && Hemo >= 12.5)
    		{
    			Connection conn = DatabaseConnection.getConnection();
    			int id = Integer.parseInt(DatabaseConnection.getSpecificColumn("person_id"));
    			PreparedStatement pst;
    			// First, insert into eligible_donors if does not exist.
    			if (DatabaseConnection.getSpecificColumn("eligible_donors", "HB", "DonorID", Integer.toString(id)).isBlank())
    			{
	    			pst = conn.prepareStatement(
	    					"INSERT INTO eligible_donors VALUES (?,?,?,?)");
	    			pst.setInt(1, id);
	    			pst.setInt(2, higherLimit);
	    			pst.setInt(3, lowerLimit);
	    			pst.setFloat(4, Hemo);
	    			
	    			pst.executeUpdate();
    			}
    				
    	            // Get the current date
    	            LocalDate currentDate = LocalDate.now();

    	            // Add 10 days to the current date
    	            LocalDate futureDate = currentDate.plusDays(10);

    	            // Convert LocalDate to java.sql.Date
    	            Date sqlDate = Date.valueOf(futureDate);
    				// Now, insert into donations.
    				pst = conn.prepareStatement(
    						"INSERT INTO DONATIONS (DonorID, Received, Date) VALUES (?,?,?)");
    				pst.setInt(1, id);
    				pst.setString(2, "NO");
    				pst.setDate(3, sqlDate);
    				
    				if (pst.executeUpdate() > 0)
    				{
    					new Alert(Alert.AlertType.INFORMATION, "Your donation is scheduled on " + sqlDate.toString()).showAndWait();
    					
    					Loaders.DisplayScene("Feedback");
    					((Stage) Age.getScene().getWindow()).close();
    					
    					return;
    				}
    			}
    			else
    			{
    				new Alert(Alert.AlertType.ERROR, "You are ineligible to donate blood").showAndWait();
    				return;
    			}
    		new Alert(Alert.AlertType.ERROR, "You are ineligible to donate blood").showAndWait();
    	}
    }

}
