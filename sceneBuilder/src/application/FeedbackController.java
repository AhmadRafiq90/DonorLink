package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class FeedbackController {

    @FXML
    private RadioButton C1;

    @FXML
    private RadioButton C2;

    @FXML
    private RadioButton C3;

    @FXML
    private RadioButton C4;

    @FXML
    private TextArea Comments;

    @FXML
    private RadioButton OE1;

    @FXML
    private RadioButton OE2;

    @FXML
    private RadioButton OE3;

    @FXML
    private RadioButton OE4;

    @FXML
    private RadioButton U1;

    @FXML
    private RadioButton U2;

    @FXML
    private RadioButton U3;

    @FXML
    private RadioButton U4;

    @FXML
    private ToggleGroup convenienceGroup;

    @FXML
    private ToggleGroup experienceGroup;

    @FXML
    private ToggleGroup usabilityGroup;

    @FXML
    void Submit(ActionEvent event) throws NumberFormatException, SQLException 
    {
    	String exp = getRating(OE1, OE2, OE3, OE4);
    	if (exp.isBlank())
    	{
    		new Alert(Alert.AlertType.ERROR, "Please rate your experience.").showAndWait();
    		return;
    	}
    	String conv = getRating(C1, C2, C3, C4);
    	if (conv.isBlank())
    	{
    		new Alert(Alert.AlertType.ERROR, "Please rate convenience.").showAndWait();
    		return;
    	}
    	String use = getRating(U1, U2, U3, U4);
    	if (use.isBlank())
    	{
    		new Alert(Alert.AlertType.ERROR, "Please rate usability.").showAndWait();
    		return;
    	}
    	
    	
    	int id = Integer.parseInt(DatabaseConnection.getSpecificColumn("person_id"));
    	//int id = 1;
    	Connection conn = DatabaseConnection.getConnection();
    	
    	PreparedStatement pst = 
    			conn.prepareStatement(
    					"INSERT INTO FEEDBACK (UserID, Experience, Convenience, Usability, Comments) Values (?,?,?,?,?)");
    	pst.setInt(1, id);
    	pst.setString(2, exp);
    	pst.setString(3, conv);
    	pst.setString(4, use);
    	pst.setString(5, Comments.getText());
    	
    	if (pst.executeUpdate() > 0)
    	{
    		new Alert(Alert.AlertType.INFORMATION, "Thank you for your feedback.").showAndWait();
    		((Stage) U1.getScene().getWindow()).close();
    		return;
    	}
    	new Alert(Alert.AlertType.ERROR, "An unknown error occured.");
    }
    
    String getRating(RadioButton rb1, RadioButton rb2, RadioButton rb3, RadioButton rb4)
    {
    	if (rb1.isSelected())
    		return "Excellent";
    	else if (rb2.isSelected())
    		return "Good";
    	else if (rb3.isSelected())
    		return "OK";
    	else if (rb4.isSelected())
    		return "Poor";
    	else 
    		return "";
    }
}
