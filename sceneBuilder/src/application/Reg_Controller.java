package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

@SuppressWarnings("unused")
public class Reg_Controller {

    @FXML
    private ChoiceBox<String> CityField;

    @FXML
    private DatePicker DOB;

    @FXML
    private ChoiceBox<String> BloodOptions;

    @FXML
    private TextField FName;

    @FXML
    private ChoiceBox<String> GenderOptions;

    @FXML
    private TextField LName;

    @FXML
    private TextField UserName;

    @FXML
    private Button SignUp;

    @FXML
    private Label alreadyRegistered;

    @FXML
    private PasswordField confirmpass;

    @FXML
    private TextField email;

    @FXML
    private PasswordField pass;
    
    @FXML
    private Label lb;

    public void initialize() {
        ObservableList<String> items = FXCollections.observableArrayList(
            "A-", "A+", "B+", "B-", "AB+", "AB-", "O+", "O-");
        ObservableList<String> genders = FXCollections.observableArrayList("Male", "Female");
        ObservableList<String> cities = FXCollections.observableArrayList("Lahore", "Quetta", "Peshawar", "Karachi", "Islamabad");
        
        
        BloodOptions.setItems(items);
        GenderOptions.setItems(genders);
        CityField.setItems(cities);
        
        BloodOptions.setValue("Blood Group");
        GenderOptions.setValue("Gender");
        CityField.setValue("City");
    }
    
 
    @FXML
    void RegButton(ActionEvent event) {
        
        Connection conn = DatabaseConnection.getConnection();
        // Window owner = SignUp.getScene().getWindow();
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
        if (pass.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please enter your password", ButtonType.OK);
            a.show();
            return;
        }
        if (confirmpass.getText().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please enter confirmation password", ButtonType.OK);
            a.show();
            return;
        }
        if (!pass.getText().equals(confirmpass.getText())) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Passwords need to be same", ButtonType.OK);
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
        if (GenderOptions.getValue().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please enter gender", ButtonType.OK);
            a.show();
            return;
        }
        if (BloodOptions.getValue().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Please enter blood group", ButtonType.OK);
            a.show();
            return;
        }
        if (CityField.getValue().isEmpty())
        {
        	new Alert(Alert.AlertType.ERROR, "Please enter City").show();
        	return;
        }

        String firstName = FName.getText(), lastName = LName.getText(), City = CityField.getValue();
        String uName = UserName.getText(), password = pass.getText(), mail = email.getText();
        String birth = DOB.getValue().toString(), Gender = GenderOptions.getValue();
        String BGroup = BloodOptions.getValue();
        
        try {
            PreparedStatement pst = conn.prepareStatement(
            		"INSERT INTO blood_bank.person (UserName, FirstName, LastName, Email, Bloodtype, " +
                            "City, DOB, Password, Gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pst.setString(1, uName);
            pst.setString(2, firstName);
            pst.setString(3, lastName);
            pst.setString(4, mail);
            pst.setString(5, BGroup);
            pst.setString(6, City);
            pst.setString(7, birth);
            pst.setString(8, password);
            pst.setString(9, Gender);
            
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0)
            {
            	new Alert(Alert.AlertType.INFORMATION, "Sign Up Successfull").showAndWait();
            	DatabaseConnection.setUserName(uName);
            	Loaders.DisplayScene("Home");
            	
            	 // Close the current stage
                ((Stage) SignUp.getScene().getWindow()).close();
            }
            else
            {
            	new Alert(Alert.AlertType.ERROR, "Sign Up failed").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    void alreadyRegistered(MouseEvent event) 
    {
    	try {
			Loaders.DisplayScene("Login");
			 // Close the current stage
            ((Stage) SignUp.getScene().getWindow()).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void HideLabel(ActionEvent event) 
    {
    	lb.setVisible(false);
    }
}
