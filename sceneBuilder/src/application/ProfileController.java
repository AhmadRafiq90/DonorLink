package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ProfileController {

    @FXML
    private ChoiceBox<String> CityField;

    @FXML
    private DatePicker DOB;

    @FXML
    private TextField FName;

    @FXML
    private TextField LName;

    @FXML
    private Button SignUp;

    @FXML
    private TextField UserName;

    @FXML
    private TextField email;
    
    public void initialize() throws SQLException
    {
        //DatabaseConnection.setUserName("OA87");
        //Connection conn = DatabaseConnection.getConnection();

    	ObservableList<String> cities = FXCollections.observableArrayList("Lahore", "Quetta", "Peshawar", "Karachi", "Islamabad");
    	CityField.setItems(cities);
        UserName.setText(DatabaseConnection.getSpecificColumn("UserName"));
        FName.setText(DatabaseConnection.getSpecificColumn("FirstName"));
        LName.setText(DatabaseConnection.getSpecificColumn("LastName"));
        email.setText(DatabaseConnection.getSpecificColumn("Email"));
        CityField.setValue(DatabaseConnection.getSpecificColumn("City"));
        String date = DatabaseConnection.getSpecificColumn("DOB");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        DOB.setValue(localDate);
    }
    @FXML
    void ChgPassword(ActionEvent event) throws IOException 
    {
    	Loaders.DisplayScene("ChgPass");
    }

    @FXML
    void Modify(ActionEvent event) throws SQLException 
    {
        if (FName.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please enter your first name", ButtonType.OK);
            a.show();
            return;
        }
        if (LName.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please enter your last name", ButtonType.OK);
            a.show();
            return;
        }
        if (DOB.getValue().toString().isEmpty() || !ValidateDate(DOB)) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please enter correct DOB", ButtonType.OK);
            a.show();
            return;
        }
        if (email.getText().isEmpty() || !isValidEmail(email.getText())) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please enter valid email", ButtonType.OK);
            a.show();
            return;
        }
        if (UserName.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please enter username", ButtonType.OK);
            a.show();
            return;
        }
        if (CityField.getValue().isEmpty())
        {
        	new Alert(Alert.AlertType.ERROR, "Please enter City").show();
        	return;
        }

    	String queryString = "Update person set Username = ?, FirstName = ?, LastName = ?, Email = ?, City = ?, DOB = ? where person_id = ?";
        Connection conn = DatabaseConnection.getConnection();

        PreparedStatement pst = conn.prepareStatement(queryString);
        pst.setString(1, UserName.getText());
        pst.setString(2, FName.getText());
        pst.setString(3, LName.getText());
        pst.setString(4, email.getText());
        pst.setString(5, CityField.getValue());
        pst.setString(6, DOB.getValue().toString());
        pst.setInt(7, Integer.parseInt(DatabaseConnection.getSpecificColumn("person_id")));
        
        if (pst.executeUpdate() > 0)
        	new Alert(Alert.AlertType.INFORMATION, "Profile successfully updated").showAndWait();
        else
        	new Alert(Alert.AlertType.ERROR, "Unknown error occured").showAndWait();
    }
    
    private boolean isValidEmail(String email) 
    {
        // Define a simple regular expression for email validation
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";

        // Compile the regular expression
        Pattern pattern = Pattern.compile(emailRegex);

        // Create a matcher object
        Matcher matcher = pattern.matcher(email);

        // Check if the email matches the pattern
        return matcher.matches();
    }
    
    private boolean ValidateDate(DatePicker Date)
    {
    	LocalDate enteredDate = Date.getValue();
    	LocalDate currentDate = LocalDate.now();
    	
    	return enteredDate.isBefore(currentDate);
    }
    
    @FXML
    void ViewHistory(ActionEvent event) throws IOException 
    {
    	Loaders.DisplayScene("UserHistory");
    }

}
