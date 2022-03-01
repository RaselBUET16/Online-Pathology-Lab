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
import java.sql.*;
import java.util.ResourceBundle;

public class patientGeneratedReportController implements Initializable {
    public TableView generatedTestTable;
    public TableColumn<takenJobTableObject,String> testName;
    public TableColumn<takenJobTableObject,String> packageId;
    public TableColumn<takenJobTableObject,Date> orderedDate;
    public TableColumn<takenJobTableObject,Integer> orderNumber;
    public Label exceptionLabel;

    ObservableList<takenJobTableObject>list = FXCollections.observableArrayList();

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("patientPage.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void showOnAction(ActionEvent actionEvent) throws IOException {
        takenJobTableObject temp= (takenJobTableObject) generatedTestTable.getSelectionModel().getSelectedItem();
        if(temp == null)
        {
            exceptionLabel.setText("Nothing Is Selected");
        }
        else {
            testResultPageController.orderNumber = temp.getOrderNumber();
            Parent root= FXMLLoader.load(getClass().getResource("testResultPage.fxml"));
            Scene scene =new Scene(root);
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        testName.setCellValueFactory(new PropertyValueFactory<takenJobTableObject,String>("testName"));
        packageId.setCellValueFactory(new PropertyValueFactory<takenJobTableObject,String>("packageId"));
        orderedDate.setCellValueFactory(new PropertyValueFactory<takenJobTableObject,Date>("takenDate"));
        orderNumber.setCellValueFactory(new PropertyValueFactory<takenJobTableObject,Integer>("orderNumber"));
        Connection conn= null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
            Statement stmt=conn.createStatement();
            ResultSet rs;
            String SQL="select test_name,package_id,request_date,order_id from orders where patient_username= '" +
                    patientPageController.patientUsername + "' and " +
                    "report_ready = 'true'";
            rs=stmt.executeQuery(SQL);
            while(rs.next())
            {
                list.add(new takenJobTableObject(rs.getString(1),
                        String.valueOf(rs.getInt(2)),rs.getDate(3),rs.getInt(4)));
            }
            generatedTestTable.setItems(list);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
