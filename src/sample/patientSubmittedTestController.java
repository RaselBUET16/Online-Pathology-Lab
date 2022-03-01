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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class patientSubmittedTestController implements Initializable {
    public TableView<orderListObjectTableView> selectedTestTable;
    public TableColumn<orderListObjectTableView,String> testName;
    public TableColumn<orderListObjectTableView,Date> submittedDate;
    public TableColumn<orderListObjectTableView,Boolean> collAvailability;
    public TableColumn<orderListObjectTableView,Boolean> sampleSubmitted;
    public TableColumn<orderListObjectTableView,Boolean> reportComplete;
    public Label nothingSelected;

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("patientPage.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        testName.setCellValueFactory(new PropertyValueFactory<orderListObjectTableView,String>("testName"));
        submittedDate.setCellValueFactory(new PropertyValueFactory<orderListObjectTableView,Date>("submittedDate"));
        collAvailability.setCellValueFactory(new PropertyValueFactory<orderListObjectTableView,Boolean>("collAvailability"));
        sampleSubmitted.setCellValueFactory(new PropertyValueFactory<orderListObjectTableView,Boolean>("sampleSubmitted"));
        reportComplete.setCellValueFactory(new PropertyValueFactory<orderListObjectTableView,Boolean>("reportComplete"));
        ObservableList<orderListObjectTableView>list= FXCollections.observableArrayList();
        try {
            ResultSet rs=DBcontroller.searchTestFromOrderList(patientPageController.patientUsername);
            while (rs.next())
            {
                list.add(new orderListObjectTableView(rs.getString(1),rs.getDate(2),
                        rs.getBoolean(3),rs.getBoolean(4),rs.getBoolean(5)));
            }
            selectedTestTable.setItems(list);
            if(list.size() == 0) nothingSelected.setText("Nothing Is Selected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
