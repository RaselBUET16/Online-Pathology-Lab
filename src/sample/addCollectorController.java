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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class addCollectorController implements Initializable {
    public String district;
    public String thana;
    public String area;
    public TextField name;
    public TextField username;
    public TextField contactNumber;
    public PasswordField password;
    public ComboBox districtComboBox;
    public ComboBox thanaComboBox;
    public ComboBox areaComboBox;
    public Label usernameMatch;


    ObservableList<String> districtList= FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn=null;

        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
            Statement stmt=conn.createStatement();
            String alldistrict = "select distinct district from location ";
            ResultSet rs = stmt.executeQuery(alldistrict);
            while(rs.next())
            {
                districtList.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        districtComboBox.setItems(districtList);
    }
    public void districtOnAction(ActionEvent actionEvent) {
        district= (String) districtComboBox.getValue();
        Connection conn=null;

        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
            Statement stmt=conn.createStatement();
            String allthana = "select distinct thana from location where district like '" + district + "'";
            ResultSet rs = stmt.executeQuery(allthana);
            ObservableList<String> thanaList=FXCollections.observableArrayList();
            while(rs.next())
            {
                thanaList.add(rs.getString(1));
            }
            thanaComboBox.setItems(thanaList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void thanaOnAction(ActionEvent actionEvent) {
        thana= (String) thanaComboBox.getValue();
        Connection conn=null;

        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
            Statement stmt=conn.createStatement();
            String allarea = "select distinct area from location where thana like '" + thana + "'";
            ResultSet rs = stmt.executeQuery(allarea);
            ObservableList<String> areaList=FXCollections.observableArrayList();
            while(rs.next())
            {
                areaList.add(rs.getString(1));
            }
            areaComboBox.setItems(areaList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void areaOnAction(ActionEvent actionEvent) {
        area= (String) areaComboBox.getValue();
    }

    public void addOnAction(ActionEvent actionEvent) throws SQLException, IOException {

        if(DBcontroller.matchUsernameCollector(username.getText()))
        {
            usernameMatch.setText("Username already exist");

        }
        else
        {
            patient pat=new patient(name.getText(),username.getText(),password.getText(),"male","overAged","No need",
                    contactNumber.getText(),area,thana,district);

            DBcontroller.insertCollector(pat);

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
