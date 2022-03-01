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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class insertReprtTableController implements Initializable {
    public TextField orderNumber;
    public Label exceptionLabel;
    public TextArea resultField;
    public TextArea commentField;
    public ComboBox orderNumberCombobox;
    ObservableList<String>list= FXCollections.observableArrayList();

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("adminPage.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void addOnAction(ActionEvent actionEvent) throws SQLException {
        if(orderNumber.getText().length()==0)
        {
            exceptionLabel.setText("Order Number Field Is Empty");
        }
        else if(DBcontroller.isValidOrderNumber(orderNumber.getText())== false)
        {
            exceptionLabel.setText("Order Number invalid");
        }

        else if(DBcontroller.isSampleCollected(orderNumber.getText()) == false)
        {
            exceptionLabel.setText("Sample is not Collected Yet");
        }
        else if(DBcontroller.isResultGiven(orderNumber.getText()) == true)
        {
            exceptionLabel.setText("Report for this test has already updated");
            resultField.setText("");
            commentField.setText("");
        }
        else {
            if (resultField.getText().length()==0) exceptionLabel.setText("Result Field Is Empty");

            else if (commentField.getText().length()==0) exceptionLabel.setText("Comment Field is Empty");

            else {
                exceptionLabel.setText("Added");

                DBcontroller.insertIntoReportTable(orderNumber.getText(),resultField.getText(),commentField.getText());
                orderNumber.setText("");
                resultField.setText("");
                commentField.setText("");
            }
        }
    }

    public void orderNumberComboBoxOnAction(ActionEvent actionEvent) {
        String ordernumber= (String) orderNumberCombobox.getValue();
        if(ordernumber == null ) return;
        System.out.println(ordernumber);
        orderNumber.setText(ordernumber);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Connection conn= null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
            Statement stmt=conn.createStatement();
            String SQL= "select order_id from orders where sample_collected = 'true' and report_ready = 'false'";
            ResultSet rs=stmt.executeQuery(SQL);
            while(rs.next())
            {
                list.add(String.valueOf(rs.getInt(1)));
            }
            orderNumberCombobox.setItems(list);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
