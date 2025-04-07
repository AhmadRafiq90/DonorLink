package application;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class InventoryController {

	@FXML
	private Label ABneg;

	@FXML
	private Label ABpos;

	@FXML
	private Label Aneg;

	@FXML
	private Label Apos;

	@FXML
	private ChoiceBox<String> BloodTypes;

	@FXML
	private Label Bneg;

	@FXML
	private Label Bpos;

	@FXML
	private ChoiceBox<String> Cities;

	@FXML
	private Label Oneg;

	@FXML
	private Label Opos;

	@FXML
	private ImageView TypeImg;
	
	private boolean available;

	public void initialize() {
		available = false;
		updateLabel("A+", 0);
		updateLabel("A-", 0);
		updateLabel("B+", 0);
		updateLabel("B-", 0);
		updateLabel("B+", 0);
		updateLabel("O+", 0);
		updateLabel("O-", 0);
		updateLabel("AB+", 0);
		updateLabel("AB-", 0);
		ObservableList<String> items = FXCollections.observableArrayList("A-", "A+",
				"B+", "B-", "AB+", "AB-", "O+", "O-");
		ObservableList<String> cities = FXCollections.observableArrayList(
				"Lahore", "Karachi", "Islamabad", "Quetta", "Peshawar");
		BloodTypes.setItems(items);
		BloodTypes.setValue("Blood Group");
		Cities.setValue("City");
		Cities.setItems(cities);
	}

	@FXML
	void SearchPressed(ActionEvent event) throws MalformedURLException {
		available = false;
		if (BloodTypes.getValue().equals("Blood Group") ||
				Cities.getValue().equals("City")) {
			new Alert(Alert.AlertType.ERROR, "Blood Group and City are Mandatory Fields.").showAndWait();
			return;
		}
		updateLabel("A+", 0);
		updateLabel("A-", 0);
		updateLabel("B+", 0);
		updateLabel("B-", 0);
		updateLabel("B+", 0);
		updateLabel("O+", 0);
		updateLabel("O-", 0);
		updateLabel("AB+", 0);
		updateLabel("AB-", 0);
		setImage();
		Connection conn = DatabaseConnection.getConnection();
		try {
			String query = "Select count(*), BloodType from inventory where BloodType = ? and CollectionCenter = ?"; 
			
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, BloodTypes.getValue());
			st.setString(2, Cities.getValue());
			
			ResultSet set = st.executeQuery();
			while (set.next()) {
				if (set.getString("BloodType") == null)
					break;
				String bloodType = set.getString("BloodType");
				int typeCount = set.getInt("count(*)");

				updateLabel(bloodType, typeCount);
				available = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void ReceiveButton(ActionEvent event) {
		try {
			if (available)
				Loaders.DisplayScene("Request");
			else
				new Alert(Alert.AlertType.ERROR, "Requested bloodtype not available").showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    @FXML
    void GoHome(ActionEvent event) throws IOException {
    	
    	Loaders.DisplayScene("Home");
    	((Stage) ABneg.getScene().getWindow()).close();
    }
    
	private void updateLabel(String bloodType, int typeCount) {
		switch (bloodType) {
			case "AB-":
				ABneg.setText(Integer.toString(typeCount));
				break;
			case "AB+":
				ABpos.setText(Integer.toString(typeCount));
				break;
			case "A-":
				Aneg.setText(Integer.toString(typeCount));
				break;
			case "A+":
				Apos.setText(Integer.toString(typeCount));
				break;
			case "B-":
				Bneg.setText(Integer.toString(typeCount));
				break;
			case "B+":
				Bpos.setText(Integer.toString(typeCount));
				break;
			case "O-":
				Oneg.setText(Integer.toString(typeCount));
				break;
			case "O+":
				Opos.setText(Integer.toString(typeCount));
				break;
		}
	}

	void setImage() throws MalformedURLException {
		String path = "D:/projimages/" + BloodTypes.getValue() + "Image.png";
		File file = new File(path);
		String imageUrl = file.toURI().toURL().toString();

		TypeImg.setImage(new Image(imageUrl));
	}
}
