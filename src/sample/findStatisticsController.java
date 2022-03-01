package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;

public class findStatisticsController implements Initializable {
    public TableView<statisticsTableObject> statisticsTable;
    public TableColumn<statisticsTableObject, java.sql.Date> from;
    public TableColumn<statisticsTableObject, java.sql.Date> to;
    public TableColumn<statisticsTableObject,Integer> collectorName;

    public void bakcOnAction(ActionEvent actionEvent) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("adminPage.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<statisticsTableObject>list= FXCollections.observableArrayList();
        from.setCellValueFactory(new PropertyValueFactory<statisticsTableObject, java.sql.Date>("from"));
        to.setCellValueFactory(new PropertyValueFactory<statisticsTableObject, java.sql.Date>("to"));
        collectorName.setCellValueFactory(new PropertyValueFactory<statisticsTableObject,Integer>("collectorName"));
        Connection conn= null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
            Statement stmt=conn.createStatement();
            ResultSet rs;
            String SQL= "select for_best()";
            stmt.executeQuery(SQL);
            SQL = "select * from best_statics";
            rs=stmt.executeQuery(SQL);
            while(rs.next())
            {
                list.add(new statisticsTableObject(rs.getDate(1),rs.getDate(2),rs.getInt(3)));
            }
            statisticsTable.setItems(list);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
