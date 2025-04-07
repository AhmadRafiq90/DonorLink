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

public class MngRecpController {

    @FXML
    private TableColumn<Map<String, String>, String> BGroup;

    @FXML
    private TableColumn<Map<String, String>, String> Date;

    @FXML
    private TableColumn<Map<String, String>, String> UID;

    @FXML
    private TableColumn<Map<String, String>, String> rID;

    @FXML
    private TableView<Map<String, String>> table;
    
    public void initialize() throws SQLException
    {
    	loadData();
    	setupCellValueFactories();
    }
    
    private void loadData() throws SQLException 
    {
        Connection conn = DatabaseConnection.getConnection();
        String query = 
        	"SELECT * FROM requests";

        Statement st = conn.createStatement();
        ResultSet rst = st.executeQuery(query);

        // Create an ObservableList to hold data
        ObservableList<Map<String, String>> data = FXCollections.observableArrayList();

        while (rst.next()) {
            // Create a Map for each row
            Map<String, String> row = new HashMap<>();
            row.put("RequestID", rst.getString("RequestID"));
            row.put("Blood Group", rst.getString("BloodType"));
            row.put("UserID", rst.getString("UserID"));
            row.put("Date", rst.getString("Date"));
            
            // Add the Map to the list
            data.add(row);
        }

        // Set the items in the TableView to the ObservableList
        table.setItems(data);
    }

    private void setupCellValueFactories() {
        // Set up the cell value factories for each column
        BGroup.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("Blood Group")));
        Date.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("Date")));
        UID.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("UserID")));
        rID.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("RequestID")));
    }
}
