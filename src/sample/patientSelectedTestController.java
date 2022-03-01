package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class patientSelectedTestController implements Initializable {
    public Label nothingSelected;
    int totalMoney=0;
    public Label totalAmount;
    public TableView<tableViewObjectTest> selectedTestTable;
    public TableColumn<tableViewObjectTest,Integer> testId;
    public TableColumn<tableViewObjectTest,String> testName;
    public TableColumn<tableViewObjectTest,Integer> testAmount;
    public TableColumn<tableViewObjectTest,Boolean> collAvailability;
    public TableColumn<tableViewObjectTest,String> description;
    ObservableList<tableViewObjectTest>list= FXCollections.observableArrayList();

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("patientPage.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void deleteTestOnAction(ActionEvent actionEvent) throws SQLException {
        tableViewObjectTest testObject=selectedTestTable.getSelectionModel().getSelectedItem();
        if(testObject == null) return;
        DBcontroller.deleteTestFromTempTable(patientPageController.patientUsername,testObject.getTestId());
        list.remove(testObject);
        totalMoney -= testObject.getAmount();
        totalAmount.setText(String.valueOf(totalMoney));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        testId.setCellValueFactory(new PropertyValueFactory<tableViewObjectTest,Integer>("testId"));
        testName.setCellValueFactory(new PropertyValueFactory<tableViewObjectTest,String>("testName"));
        testAmount.setCellValueFactory(new PropertyValueFactory<tableViewObjectTest,Integer>("amount"));
        collAvailability.setCellValueFactory(new PropertyValueFactory<tableViewObjectTest,Boolean>("collectorAvailability"));
        description.setCellValueFactory(new PropertyValueFactory<tableViewObjectTest,String>("description"));
        try {
            ResultSet rs=DBcontroller.searchTestByPatientUsername(patientPageController.patientUsername);
            while(rs.next())
            {
                list.add(new tableViewObjectTest(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getBoolean(4),rs.getString(5)));
                totalMoney += rs.getInt(3);
            }
            selectedTestTable.setItems(list);
            if(list.size() == 0) nothingSelected.setText("Nothing Is Selected");
            totalAmount.setText(String.valueOf(totalMoney));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void submitOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        if(list.size() == 0) return;
        DBcontroller.insertTestIntoOrderTable(patientPageController.patientUsername);
        Parent root= FXMLLoader.load(getClass().getResource("thankYouPage.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
