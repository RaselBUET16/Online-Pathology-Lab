package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class testSearchResultController implements Initializable {
    public TableView<tableViewObjectTest> testResultTable;
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

    public void selectTestOnAction(ActionEvent actionEvent) throws SQLException {
        tableViewObjectTest testObject=testResultTable.getSelectionModel().getSelectedItem();
        if(testObject == null) return;
        DBcontroller.addTestIntoTempTable(patientPageController.patientUsername,testObject.getTestId(),testObject.getTestName());
        list.remove(testObject);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        testId.setCellValueFactory(new PropertyValueFactory<tableViewObjectTest,Integer>("testId"));
        testName.setCellValueFactory(new PropertyValueFactory<tableViewObjectTest,String>("testName"));
        testAmount.setCellValueFactory(new PropertyValueFactory<tableViewObjectTest,Integer>("amount"));
        collAvailability.setCellValueFactory(new PropertyValueFactory<tableViewObjectTest,Boolean>("collectorAvailability"));
        description.setCellValueFactory(new PropertyValueFactory<tableViewObjectTest,String>("description"));

        if(patientPageController.organSearchName != null)
        {
            try {
                ResultSet rs=DBcontroller.searchTestForOrgan(patientPageController.organSearchName);
                while(rs.next())
                {
                    list.add(new tableViewObjectTest(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getBoolean(4),rs.getString(5)));
                }
                testResultTable.setItems(list);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(patientPageController.diseaseSearchName != null)
        {
            try {
                ResultSet rs=DBcontroller.searchTestForDisease(patientPageController.diseaseSearchName);
                while(rs.next())
                {
                    list.add(new tableViewObjectTest(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getBoolean(4),rs.getString(5)));
                }
                testResultTable.setItems(list);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(patientPageController.textSearchName != null)
        {
            try {
                ResultSet rs=DBcontroller.searchTestForText(patientPageController.textSearchName);
                while(rs.next())
                {
                    list.add(new tableViewObjectTest(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getBoolean(4),rs.getString(5)));
                }
                testResultTable.setItems(list);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }
}
