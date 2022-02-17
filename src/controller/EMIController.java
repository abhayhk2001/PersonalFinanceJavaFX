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

public class EMIController implements Initializable {
    @FXML
    private TextField amount, rate, noofmonths, month, date,year;
    private int userID;

    private void insertEMI(float Amount, float Rate,int NoOfMonths, int Month, int Date,int Year) {
        String query = "insert into emis values(" + Amount + "," + Rate + "," + this.userID +","+ NoOfMonths + "," + Month + "," + Date + "," + Year + ")";
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
    public void AddEMI() throws IOException {
        float Amount, Rate;
        int NoOfMonths, Month, Date,Year;
        Amount = Float.parseFloat(amount.getText());
        Rate = Float.parseFloat(rate.getText());
        NoOfMonths = Integer.parseInt(noofmonths.getText());
        Month= Integer.parseInt(month.getText());
        Date= Integer.parseInt(date.getText());
        Year = Integer.parseInt(year.getText());
        insertEMI(Amount, Rate,NoOfMonths, Month, Date,Year);
        new Main().changeScene("/view/MainPage.fxml",null);
    }
}
