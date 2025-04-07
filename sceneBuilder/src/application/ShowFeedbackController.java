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

public class ShowFeedbackController {

    @FXML
    private TableColumn<Map<String, String>, String> Conv;

    @FXML
    private TableColumn<Map<String, String>, String> Exp;

    @FXML
    private TableView<Map<String, String>> FeedbackTable;

    @FXML
    private TableColumn<Map<String, String>, String> UID;

    @FXML
    private TableColumn<Map<String, String>, String> comments;

    @FXML
    private TableColumn<Map<String, String>, String> usability;
    
    
    
    public void initialize() throws SQLException
    {
    	loadData();
    	setupCellValueFactories();
    }
    
    private void loadData() throws SQLException
    {
        Connection conn = DatabaseConnection.getConnection();
        String query = 
        	"SELECT * FROM Feedback";

        Statement st = conn.createStatement();
        ResultSet rst = st.executeQuery(query);

        // Create an ObservableList to hold data
        ObservableList<Map<String, String>> data = FXCollections.observableArrayList();

        while (rst.next()) {
            // Create a Map for each row
            Map<String, String> row = new HashMap<>();
            row.put("UserID", rst.getString("UserID"));
            row.put("Experience", rst.getString("Experience"));
            row.put("Convenience", rst.getString("Convenience"));
            row.put("Usability", rst.getString("Usability"));
            row.put("Comments", rst.getString("Comments"));
            
            // Add the Map to the list
            data.add(row);
        }

        // Set the items in the TableView to the ObservableList
        FeedbackTable.setItems(data);
    }
    
    private void setupCellValueFactories() {
        // Set up the cell value factories for each column
        UID.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("UserID")));
        Conv.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("Convenience")));
        Exp.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("Experience")));
        usability.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("Usability")));
        comments.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().get("Comments")));
    }
}
