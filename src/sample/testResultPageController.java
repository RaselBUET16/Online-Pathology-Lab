package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class testResultPageController implements Initializable {
    public Label testName;
    public TextField reportField;
    public TextField commentField;
    public static int orderNumber;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn= null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
            Statement stmt=conn.createStatement();
            String SQL= "select test_name ,result,comment from orders, result where orders.order_id = '" +
                    orderNumber + "' and result.order_id = '" + orderNumber + "'";
            ResultSet rs=stmt.executeQuery(SQL);
            rs.next();
            testName.setText(rs.getString(1));
            reportField.setText(rs.getString(2));
            commentField.setText(rs.getString(3));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("patientGeneratedReport.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
