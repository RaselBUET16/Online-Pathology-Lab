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

import javafx.scene.control.TextField;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class patientPageController implements Initializable {

    public static  String patientUsername;
    public static String organSearchName;
    public static String diseaseSearchName;
    public static String textSearchName;

    public ComboBox organComboBox;
    public ComboBox diseaseComboBox;
    public TextField searchedText;


    ObservableList<String> organList= FXCollections.observableArrayList();
    ObservableList<String> diseaseList= FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn=null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
            Statement stmt=conn.createStatement();
            String allOrgan = "select name from organ";
            ResultSet rs = stmt.executeQuery(allOrgan);
            while(rs.next())
            {
                organList.add(rs.getString(1));
            }
            String allDisease = "select name from disease";
            rs = stmt.executeQuery(allDisease);
            while(rs.next())
            {
                diseaseList.add(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        organComboBox.setItems(organList);
        diseaseComboBox.setItems(diseaseList);

    }

    public void organSearchOnAction(ActionEvent actionEvent) throws IOException, SQLException {

        organSearchName= (String) organComboBox.getValue();
        diseaseSearchName=null;
        textSearchName=null;
        Parent root= FXMLLoader.load(getClass().getResource("testSearchResult.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void diseaseSearchOnAction(ActionEvent actionEvent) throws IOException {
        organSearchName= null;
        diseaseSearchName= (String) diseaseComboBox.getValue();
        textSearchName=null;
        Parent root= FXMLLoader.load(getClass().getResource("testSearchResult.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void testSearchOnAction(ActionEvent actionEvent) throws IOException {
        organSearchName=null;
        diseaseSearchName=null;
        textSearchName=searchedText.getText();
        Parent root= FXMLLoader.load(getClass().getResource("testSearchResult.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void selectedTestOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("patientSelectedTest.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void submittedTestOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("patientSubmittedTest.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void generatedReportOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("patientGeneratedReport.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }


    public void logoutOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("homepage.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
