package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminHomeController {

    @FXML
    private Label ABneg;

    @FXML
    private Label ABpos;

    @FXML
    private Label Aneg;

    @FXML
    private Label Apos;

    @FXML
    private Label Bneg;

    @FXML
    private Label Bpos;

    @FXML
    private Label DonationCnt;

    @FXML
    private Label Oneg;

    @FXML
    private Label Opos;

    @FXML
    private Label RcvCount;

    @FXML
    private Label UserName;

    public void initialize() throws SQLException {
        UserName.setText(DatabaseConnection.getUserName());
        DonationCnt.setText(Integer.toString(getCount("inventory")));
        RcvCount.setText(Integer.toString(getCount("requests")));
        
        ABneg.setText(
                DatabaseConnection.getSpecificColumn("inventory", "count(*)", "BloodType", "AB-"));
        ABpos.setText(
                DatabaseConnection.getSpecificColumn("inventory", "count(*)", "BloodType", "AB+"));
        Aneg.setText(
                DatabaseConnection.getSpecificColumn("inventory", "count(*)", "BloodType", "A-"));
        Apos.setText(
                DatabaseConnection.getSpecificColumn("inventory", "count(*)", "BloodType", "A+"));
        Bpos.setText(
                DatabaseConnection.getSpecificColumn("inventory", "count(*)", "BloodType", "B+"));
        Bneg.setText(
                DatabaseConnection.getSpecificColumn("inventory", "count(*)", "BloodType", "B-"));
        Oneg.setText(
                DatabaseConnection.getSpecificColumn("inventory", "count(*)", "BloodType", "O-"));
        Opos.setText(
                DatabaseConnection.getSpecificColumn("inventory", "count(*)", "BloodType", "O+"));
    }

    @FXML
    void AdmCamps(ActionEvent event) {

    }
    
    @FXML
    void Feedback(ActionEvent event) throws IOException 
    {
    	Loaders.DisplayScene("ShowFeedback");
    }
    
    @FXML
    void AdmDonors(ActionEvent event) throws IOException {
        Loaders.DisplayScene("MngDonors");
    }

    @FXML
    void AdmRecps(ActionEvent event) throws IOException 
    {
    	Loaders.DisplayScene("MngRecps");
    }

    @FXML
    void SignOut(ActionEvent event) throws IOException {
    	((Stage) Apos.getScene().getWindow()).close();
    	Loaders.DisplayScene("Login");
    }

    int getCount(String table) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        String query = "Select " + "count(*)" + " from " + table;
        Statement st = conn.createStatement();
        try (ResultSet resultSet = st.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt("count(*)");
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the exception as needed
        }
        return 0;
    }
}
