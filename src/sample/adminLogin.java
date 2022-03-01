package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class adminLogin {
    int loggedPatId;

    public Label passDontMatch;
    public TextField username;
    public PasswordField password;
    public boolean checkUserPass(String user,String pass) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost/PathLab", "postgres", "killer125");
        System.out.println("connection success");
        Statement stmt = conn.createStatement();
        String patId = "select id from admin where username = '" + user + "' and password = '" + pass + "'";
        ResultSet rs=stmt.executeQuery(patId);
        if(rs.next() == false) {
            passDontMatch.setText("Username or Password doesn't match");
            return false;
        }
        else
        {
            loggedPatId=rs.getInt(1);
            return true;
        }

    }

    public void loginOnAction(ActionEvent actionEvent) throws SQLException, IOException {
        boolean exist=checkUserPass(username.getText(),password.getText());
        if(exist)
        {
            Parent root= FXMLLoader.load(getClass().getResource("adminPage.fxml"));
            Scene scene =new Scene(root);
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
        }
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("homepage.fxml"));
        Scene scene =new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
}
