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

public class InvestmentController implements Initializable {
    @FXML
    private TextField amount, rate, month,year;

    private int userID;


    private void insertInvestment(float Amount, float Rate, int Month, int Year) {
        String query = "insert into investments values(" + Amount + "," + Rate + ","+ Month + ","  + Year + ","+ this.userID +")";
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
    public void AddInvestment() throws IOException {
        float Amount, Rate;
        int Month,Year;
        Amount = Float.parseFloat(amount.getText());
        Rate = Float.parseFloat(rate.getText());
        Month= Integer.parseInt(month.getText());
        Year = Integer.parseInt(year.getText());
        insertInvestment(Amount, Rate, Month, Year);
        new Main().changeScene("/view/MainPage.fxml",null);
    }
}
