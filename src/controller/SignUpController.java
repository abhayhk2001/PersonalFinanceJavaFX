package controller;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.regex.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private TextField name, username, pan, demat, salary;
    @FXML
    private PasswordField password;
    @FXML
    private Label prompt;

    private void insertUser(String Name, String Username, String Password, long Salary, String Pan, long Demat) {
        String query = "insert into user (name,username,password,salary,pan,demat) values('" + Name + "','" + Username + "','" + Password + "'," + Salary + ",'" + Pan + "'," + Demat + ")";
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
    public void CreateButton() throws IOException {
        boolean checkPan = false,checkDemat = false;
        String Name, Username, Password, Pan;
        long Salary, Demat;
        Name = name.getText();
        Username = username.getText();
        Password = password.getText();
        Salary = Long.parseLong(salary.getText());
        Pan = pan.getText();
        Demat = Long.parseLong(demat.getText());
        String panRegex = "[A-Z]{5}[0-9]{4}[A-Z]";
        String dematRegex ="[0-9]{16}";
        if(Pattern.compile(panRegex).matcher(Pan).matches())
            checkPan = true;
        if(Pattern.compile(dematRegex).matcher(demat.getText()).matches())
            checkDemat = true;
        if(checkPan && checkDemat){
            prompt.setText("Correct Details");
            insertUser(Name, Username, Password, Salary, Pan, Demat);
            new Main().changeScene("/view/SignIn.fxml",null);
        }
        else if(checkDemat){
            prompt.setText("Invalid PAN number");
        }
        else {
            prompt.setText("Invalid Demat Acc number");
        }
    }
}
