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
import java.util.Date;
import java.util.ResourceBundle;

public class collectorTakenJobTableController implements Initializable {
    public TableView<takenJobTableObject> selectedTestTable;
    public TableColumn<takenJobTableObject,String> testName;
    public TableColumn<takenJobTableObject,String> patientContactNumber;
    public TableColumn<takenJobTableObject,String> patientAddress;
    public Label exceptionLabel;
    public TableColumn<takenJobTableObject,String> packageId;
    public TableColumn<takenJobTableObject,Date> takenDate;
    public TableColumn<takenJobTableObject,Integer> orderNumber;
    ObservableList<takenJobTableObject> list= FXCollections.observableArrayList();

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("collectorPage.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void submitSampleOnAction(ActionEvent actionEvent) throws SQLException {
        takenJobTableObject temp=selectedTestTable.getSelectionModel().getSelectedItem();
        if(temp==null) exceptionLabel.setText("Nothing Is Selected");
        else{
            Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
            Statement stmt=conn.createStatement();
            String updateSql="update orders set sample_collected = 'true',sample_submission_date = current_timestamp where test_name = '"+
                    temp.getTestName() + "' and package_id = '" + temp.getPackageId() + "'";
            stmt.executeUpdate(updateSql);
            exceptionLabel.setText("Sample Submitted");


            list.remove(temp);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        testName.setCellValueFactory(new PropertyValueFactory<takenJobTableObject,String>("testName"));
        patientContactNumber.setCellValueFactory(new PropertyValueFactory<takenJobTableObject,String>("patientContactNumber"));
        patientAddress.setCellValueFactory(new PropertyValueFactory<takenJobTableObject,String>("patientAddress"));
        packageId.setCellValueFactory(new PropertyValueFactory<takenJobTableObject,String>("packageId"));
        takenDate.setCellValueFactory(new PropertyValueFactory<takenJobTableObject,Date>("takenDate"));
        orderNumber.setCellValueFactory(new PropertyValueFactory<takenJobTableObject,Integer>("orderNumber"));

        Connection conn= null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
            Statement stmt=conn.createStatement();
            String SQL="select id from collector where username = '" + collectorPageController.collectorUsername + "'";
            ResultSet rs=stmt.executeQuery(SQL);
            rs.next();
            int collectorId=rs.getInt(1);


             SQL="select test_name,patient_username,package_id,collector_taken_date,order_id from orders where collector_id = '" +collectorId + "' and " +
                     "sample_collected = 'false'";
             rs=stmt.executeQuery(SQL);
             Connection conn2=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
             Statement stmt2=conn2.createStatement();
             ResultSet rs2;
             while(rs.next())
             {
                 SQL="select contact_number,address from patient where username = '" + rs.getString(2) + "'";
                 rs2=stmt2.executeQuery(SQL);
                 rs2.next();
                 list.add(new takenJobTableObject(rs.getString(1),rs2.getString(1),rs2.getString(2),String.valueOf(rs.getInt(3)),rs.getDate(4),rs.getInt(5)));
             }
             selectedTestTable.setItems(list);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
