package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginGeneral {
    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("homepage.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void goAdminPageOnAction(ActionEvent actionEvent) throws IOException {
        Parent root=FXMLLoader.load(getClass().getResource("adminLogin.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void goPatientPageOnAction(ActionEvent actionEvent) throws IOException {
        Parent root=FXMLLoader.load(getClass().getResource("patientLogin.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void goCollectorPageOnAction(ActionEvent actionEvent) throws IOException {
        Parent root=FXMLLoader.load(getClass().getResource("collectorLogin.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
