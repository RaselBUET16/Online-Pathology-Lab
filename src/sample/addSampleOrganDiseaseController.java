package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class addSampleOrganDiseaseController {
    public TextField organName;
    public Label organText;

    public Label sampleText;
    public TextField sampleName;

    public Label diseaseText;
    public TextField diseaseName;
    public Label organExist;
    public Label sampleExist;
    public Label diseaseExist;

    public void organBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("adminPage.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void organAddOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        if(DBcontroller.matchOrganName(organName.getText()))
        {
            organExist.setText("This Organ is Already exist");
        }
        else
        {
            DBcontroller.insertOrgan(organName.getText());
            Parent root= FXMLLoader.load(getClass().getResource("adminPage.fxml"));
            Scene scene =new Scene(root);
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);

        }
    }

    public void samplebackOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("adminPage.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void sampleAddOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        if(DBcontroller.matchSampleName(sampleName.getText()))
        {
            sampleExist.setText("This Sample is Already exist");
        }
        else
        {
            DBcontroller.insertSample(sampleName.getText());
            Parent root= FXMLLoader.load(getClass().getResource("adminPage.fxml"));
            Scene scene =new Scene(root);
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);

        }
    }

    public void diseaseAddOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        if(DBcontroller.matchDiseaseName(diseaseName.getText()))
        {
            diseaseExist.setText("This Disease is Already exist");
        }
        else
        {
            DBcontroller.insertDisease(diseaseName.getText());
            Parent root= FXMLLoader.load(getClass().getResource("adminPage.fxml"));
            Scene scene =new Scene(root);
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);

        }

    }

    public void diseaseBackOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("adminPage.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }


}
