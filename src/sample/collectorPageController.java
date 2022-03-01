package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class collectorPageController implements Initializable {
    public static String collectorUsername;
    public ComboBox availableJobComboBox;
    public Label activityLabel;
    ObservableList<Integer>packageList= FXCollections.observableArrayList();
    ObservableList<Integer>emptyList=FXCollections.observableArrayList();

    public void offlineButtonOnAction(ActionEvent actionEvent) {
        activityLabel.setText("OFFLINE");
        availableJobComboBox.setItems(emptyList);

    }

    public void onlineButtonOnAction(ActionEvent actionEvent) {
        activityLabel.setText("ONLINE");
        availableJobComboBox.setItems(packageList);
    }

    public void availableJobComboBoxOnAction(ActionEvent actionEvent) throws IOException {
        int id= (int) availableJobComboBox.getValue();
        System.out.println(id);
        jobsUnderPackageController.packageId=id;
        Parent root= FXMLLoader.load(getClass().getResource("jobsUnderPackage.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));


    }

    public void takenJobOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("collectorTakenJobTable.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    public void completedJobOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("collectorCompletedJobs.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("homePage.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        activityLabel.setText("ONLINE");
        Connection conn=null;

        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
            Statement stmt=conn.createStatement();

            String allPackages = "select distinct package_id from orders where collector_taken = 'false' and proposed_location IN (" +
                    "select id from location where thana = (" +
                           "select thana from location where id = (" +
                            "select location_id from collector where id = (" +
                    "select id from collector where username = '"+ collectorUsername + "'))))";

            ResultSet rs = stmt.executeQuery(allPackages);
            while(rs.next())
            {
                packageList.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        availableJobComboBox.setItems(packageList);
    }
}
