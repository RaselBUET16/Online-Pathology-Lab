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

public class PatientRegistration implements Initializable {
    public Label usernameMatch;
    public Label passwordMatch;
    public TextField contactNo;
    public TextField username;
    public TextField address;
    public TextField age;
    public TextField name;
    public TextField password;
    public TextField confirmPassword;

    String sex;
    String district;
    String thana;
    String area;

    public ComboBox districtComboBox;
    public ComboBox thanaCombobox;
    public ComboBox areaComboBox;
    public ComboBox sexComboBox;
    ObservableList<String> districtList= FXCollections.observableArrayList();
    ObservableList<String> sexList= FXCollections.observableArrayList("Male","Female");


    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("homepage.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void submitOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        if(! confirmPassword.getText().equals(password.getText())){
            passwordMatch.setText("Password doesn't match");

        }
       else if(DBcontroller.matchUsernamePatient(username.getText()))
        {
            usernameMatch.setText("Username already exist");

        }
        else
        {
            patient pat=new patient(name.getText(),username.getText(),password.getText(),sex,age.getText(),address.getText(),
                   contactNo.getText(),area,thana,district);

            DBcontroller.insertPatient(pat);

            Parent root=FXMLLoader.load(getClass().getResource("completeRegestration.fxml"));
            Scene scene =new Scene(root);
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
        }




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
            thanaCombobox.setItems(thanaList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void thanaOnAction(ActionEvent actionEvent) {
        thana= (String) thanaCombobox.getValue();
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

    public void sexOnAction(ActionEvent actionEvent) {
         sex= (String) sexComboBox.getValue();

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sexComboBox.setItems(sexList);
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
}
