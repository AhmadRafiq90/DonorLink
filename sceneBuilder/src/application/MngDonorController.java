package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MngDonorController {

    @FXML
    private TableColumn<Map<String, String>, String> DiasBP;

    @FXML
    private TableColumn<Map<String, String>, String> HB;

    @FXML
    private TableColumn<Map<String, String>, String> SysBP;
    
    @FXML
    private TableColumn<Map<String, String>, String> BGroup;

    @FXML
    private TableColumn<Map<String, String>, String> dID;

    @FXML
    private TableView<Map<String, String>> table;
    
    private String SelectedID;

    public void initialize() throws SQLException 
    {
        loadData();
        setupCellValueFactories();
        
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                //System.out.println("Selected DonorID: " + newValue.get("DonationID"));
            	SelectedID = newValue.get("DonationID");
            }
        });
    }
    
    private void loadData() throws SQLException 
    {
        Connection conn = DatabaseConnection.getConnection();
        String query = 
        	"SELECT * FROM Donations join person on person_id = DONORID WHERE Received = 'NO' AND Date >= CURDATE()";

        Statement st = conn.createStatement();
        ResultSet rst = st.executeQuery(query);

        // Create an ObservableList to hold data
        ObservableList<Map<String, String>> data = FXCollections.observableArrayList();

        while (rst.next()) {
            // Create a Map for each row
            Map<String, String> row = new HashMap<>();
            row.put("DonationID", rst.getString("DonationID"));
            row.put("DonorID", rst.getString("DonorID"));
            row.put("Received", rst.getString("Received"));
            row.put("Date", rst.getString("Date"));
            row.put("Blood Group", rst.getString("Bloodtype"));
            
            // Add the Map to the list
            data.add(row);
        }

        // Set the items in the TableView to the ObservableList
        table.setItems(data);
    }
    
    private void setupCellValueFactories() {
        // Set up the cell value factories for each column
        dID.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("DonationID")));
        SysBP.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("DonorID")));
        DiasBP.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("Received")));
        HB.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("Date")));
        BGroup.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("Blood Group")));
    }
    
    @FXML
    void Received(ActionEvent event) throws SQLException 
    {
    	Connection conn = DatabaseConnection.getConnection();
        String updateQuery = "UPDATE Donations SET Received = 'YES' WHERE DonationID = ?";
        PreparedStatement pst = conn.prepareStatement(updateQuery);
        pst.setString(1, SelectedID);

        String DonorID = DatabaseConnection.getSpecificColumn("Donations", "DonorID", "DonationID", SelectedID);
        String Date = DatabaseConnection.getSpecificColumn("Donations", "Date", "DonationID", SelectedID);

        if (pst.executeUpdate() > 0)
        {
            String InsertQuery = "INSERT INTO INVENTORY (BloodType, DonorID, DonationDate, CollectionCenter) " +
            "VALUES (?,?,?,?)";
            pst = conn.prepareStatement(InsertQuery);
            pst.setString(1, DatabaseConnection.getSpecificColumn("Person", "Bloodtype", "person_id", DonorID));
            pst.setInt(2, Integer.parseInt(DonorID));
            pst.setString(3, Date);
            pst.setString(4, DatabaseConnection.getSpecificColumn("Person", "City", "person_id", DonorID));

            if (pst.executeUpdate() > 0)
            {
                new Alert(Alert.AlertType.INFORMATION, "Donation successfully received").showAndWait();
                loadData();
                return;
            }
            else
            {
                new Alert(Alert.AlertType.ERROR, "An error occured").showAndWait();
                return;
            }
        }
        new Alert(Alert.AlertType.ERROR, "An error occured").showAndWait();
    }
}
