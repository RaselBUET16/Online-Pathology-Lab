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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class addTestController implements Initializable{

    public TextField name;
    public TextField amount;
    public TextField duration;
    public TextField description;
    public ComboBox diseaseComboBox;
    public ComboBox sampleComboBox;
    public ComboBox collectorAvailabilityComboBox;
    public ComboBox organComboBox;
    public Label testAlreadyExist;
    ObservableList<String> organList= FXCollections.observableArrayList();
    ObservableList<String> collectorAvailabilityList= FXCollections.observableArrayList("true","false");
    ObservableList<String> diseaseList= FXCollections.observableArrayList();
    ObservableList<String> sampleList= FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        collectorAvailabilityComboBox.setItems(collectorAvailabilityList);
        Connection conn=null;

        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
            Statement stmt=conn.createStatement();
            String organSQL = "select name from organ";
            ResultSet rs = stmt.executeQuery(organSQL);
            while(rs.next())
            {
                organList.add(rs.getString(1));
            }
            String diseaseSQL = "select name from disease";
            rs = stmt.executeQuery(diseaseSQL);
            while(rs.next())
            {
                diseaseList.add(rs.getString(1));
            }

            String sampleSQL = "select name from samples";
            rs = stmt.executeQuery(sampleSQL);
            while(rs.next())
            {
                sampleList.add(rs.getString(1));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        organComboBox.setItems(organList);
        diseaseComboBox.setItems(diseaseList);
        sampleComboBox.setItems(sampleList);
    }

    public void addOnAction(ActionEvent actionEvent) throws IOException, SQLException {
        String organ= (String) organComboBox.getValue();
        String disease= (String) diseaseComboBox.getValue();
        String sample= (String) sampleComboBox.getValue();
        String availability= (String) collectorAvailabilityComboBox.getValue();
        if(DBcontroller.matchTestname(name.getText(),organ,disease))
        {
            testAlreadyExist.setText("This Test Already Exist");
        }
        else
        {
                DBcontroller.insertTest(name.getText(),organ,disease,sample,amount.getText(),duration.getText(),availability,
                    description.getText());
                Parent root= FXMLLoader.load(getClass().getResource("adminPage.fxml"));
                Scene scene =new Scene(root);
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
        }

    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("adminPage.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
