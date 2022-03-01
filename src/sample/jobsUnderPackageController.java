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

public class jobsUnderPackageController implements Initializable{
    public static int packageId;
    public Label packageID;
    public TableView<packageTableViewObject> packageTable;
    public TableColumn<packageTableViewObject,String> testName;
    public TableColumn<packageTableViewObject,Date> orderedDate;
    public TableColumn<packageTableViewObject,String> sampleType;
    public Label areaLabel;
    public Label thanaLabel;
    public Label districtlabel;
    public Label addressLabel;
    public Label patientPhoneNumber;


    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("collectorPage.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void confirmButtonOnAciton(ActionEvent actionEvent) throws SQLException, IOException {
        Connection conn=null;
        conn = DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String SQL="select id from collector where username = '" + collectorPageController.collectorUsername + "'";
        ResultSet rs=stmt.executeQuery(SQL);
        rs.next();
        int collectorId=rs.getInt(1);
        SQL="update orders set collector_taken = 'true' ,collector_taken_date = current_timestamp,collector_id = '" + collectorId + "'where package_id = '"+ packageId+"'";
        stmt.executeUpdate(SQL);
        Parent root= FXMLLoader.load(getClass().getResource("collectorPage.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<packageTableViewObject>tableList= FXCollections.observableArrayList();
        packageID.setText(String.valueOf(packageId));
        Connection conn=null;
        testName.setCellValueFactory(new PropertyValueFactory<packageTableViewObject,String>("testName"));
        orderedDate.setCellValueFactory(new PropertyValueFactory<packageTableViewObject,Date>("orderedDate"));
        sampleType.setCellValueFactory(new PropertyValueFactory<packageTableViewObject,String>("sampleType"));

        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
            Statement stmt=conn.createStatement();
            String patientUsername="select patient_username from orders where package_id = '" + packageId + "'";
            ResultSet rs1= stmt.executeQuery(patientUsername);
            rs1.next();
            String username=rs1.getString(1);
            patientUsername="select address,contact_number from patient where username='" + username + "'";
            rs1=stmt.executeQuery(patientUsername);
            rs1.next();
            addressLabel.setText(rs1.getString(1));
            patientPhoneNumber.setText(rs1.getString(2));

            String preferedLocationId = "select distinct proposed_location from orders  where package_id='" + packageId+"'";
            ResultSet rs = stmt.executeQuery(preferedLocationId);
            rs.next();
            int locationId=rs.getInt(1);
            String preferedLocation="select area,thana,district from location where id = '" + locationId + "'";
            rs=stmt.executeQuery(preferedLocation);
            rs.next();
            areaLabel.setText(rs.getString(1));
            thanaLabel.setText(rs.getString(2));
            districtlabel.setText(rs.getString(3));
            String tests="select test_name,request_date from orders where package_id = '" + packageId + "'";
            Statement stmt2=conn.createStatement();
            ResultSet rs2;
            String sampletype;
            rs=stmt.executeQuery(tests);
            while(rs.next())
            {
               sampletype="select name from samples where id = ( select sample_id from test where name = '" + rs.getString(1) + "')";
               rs2=stmt2.executeQuery(sampletype);
               rs2.next();
                    tableList.add(new packageTableViewObject(rs.getString(1),rs.getDate(2),rs2.getString(1)));
            }
            packageTable.setItems(tableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
       // availableJobComboBox.setItems(packageList);
    }
    }

