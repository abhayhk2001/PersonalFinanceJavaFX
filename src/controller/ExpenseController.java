package controller;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ExpenseController implements Initializable {
    @FXML
    private TextField amount, name;
    private int userID;

    private void insertExpense(String Name,float Amount) {
        String query = "insert into expenses values('" + Name + "'," + this.userID +","+ Amount + ")";
        System.out.println(query);
        executeQuery(query);
    }

    public void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try {
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userID = 1;
    }
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public void AddExpense() throws IOException {
        float Amount = Float.parseFloat(amount.getText());
        String Name = name.getText();
        insertExpense(Name,Amount);
        new Main().changeScene("/view/MainPage.fxml",null);
    }
}
