package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class HistoryController {

    @FXML
    private TableColumn <Map<String, String>, String> BloodType;

    @FXML
    private TableColumn<Map<String, String>, String> Date;
    
    @FXML
    private TableColumn<Map<String, String>, String> Date2;

    @FXML
    private TableView<Map<String, String>> DonationTable;

    @FXML
    private TableView<Map<String, String>> ReceivalTable;

    @FXML
    private TableColumn<Map<String, String>, String> Received;

    @FXML
    private TableColumn<Map<String, String>, String> RequestID;

    @FXML
    private TableColumn<Map<String, String>, String> dID;
    
    public void initialize() throws SQLException
    {
    	//DatabaseConnection.setUserName("OA87");
    	loadData1();
    	loadData2();
    	setupCellValueFactories();
    }
    
    private void loadData1() throws SQLException
    {
        Connection conn = DatabaseConnection.getConnection();
        String query = 
        	"SELECT * FROM Donations where DonorID = " + DatabaseConnection.getSpecificColumn("person_id");

        Statement st = conn.createStatement();
        ResultSet rst = st.executeQuery(query);

        // Create an ObservableList to hold data
        ObservableList<Map<String, String>> data = FXCollections.observableArrayList();

        while (rst.next()) {
            // Create a Map for each row
            Map<String, String> row = new HashMap<>();
            row.put("DonationID", rst.getString("DonationID"));
            row.put("Received", rst.getString("Received"));
            row.put("Date", rst.getString("Date"));
            
            // Add the Map to the list
            data.add(row);
        }

        // Set the items in the TableView to the ObservableList
        DonationTable.setItems(data);
    }
    
    private void loadData2() throws SQLException
    {
        Connection conn = DatabaseConnection.getConnection();
        String query = 
        	"SELECT * FROM requests where UserID = " + DatabaseConnection.getSpecificColumn("person_id");

        Statement st = conn.createStatement();
        ResultSet rst = st.executeQuery(query);

        // Create an ObservableList to hold data
        ObservableList<Map<String, String>> data = FXCollections.observableArrayList();

        while (rst.next()) {
            // Create a Map for each row
            Map<String, String> row = new HashMap<>();
            row.put("RequestID", rst.getString("RequestID"));
            row.put("BloodType", rst.getString("BloodType"));
            row.put("Date", rst.getString("Date"));
            
            // Add the Map to the list
            data.add(row);
        }

        // Set the items in the TableView to the ObservableList
        ReceivalTable.setItems(data);
    }
    
    private void setupCellValueFactories() {
        // Set up the cell value factories for each column
        dID.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("DonationID")));
        RequestID.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("RequestID")));
        Received.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("Received")));
        Date.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("Date")));
        Date2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("Date")));
        BloodType.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("BloodType")));
    }

}
