package controller;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.LoginUser;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button SignUpButton;
    @FXML
    private Label label;
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
    public void SignUpButton() throws IOException {
        new Main().changeScene("/view/SignUp.fxml",null);
    }
    public void SignInButton() throws IOException {
        LoginUser newUser = new LoginUser(username.getText(),password.getText());
        ObservableList<LoginUser> Users;
        Users = getUser();
        boolean checkUN = false,checkPW = false;
        for (LoginUser user : Users) {
            if (user.getUserName().equals(newUser.getUserName())) {
                checkUN = true;
                if (user.getPassword().equals(newUser.getPassword())) {
                    checkPW = true;
                }
                break;
            }
        }
        if(checkUN && checkPW){
            label.setText("Correct Details");
            new Main().changeScene("/view/MainPage.fxml",newUser);
        }
        else if(checkUN) {
            label.setText("Invalid Password");
        }
        else {
            label.setText("Invalid Username");
            SignUpButton.setOpacity(1);
        }
    }

    public ObservableList<LoginUser> getUser() {
        ObservableList<LoginUser> UserList = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "SELECT * FROM User";
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            LoginUser l;
            while (rs.next()) {
                l = new LoginUser(rs.getInt("userid"),rs.getString("username"), rs.getString("password"));
                UserList.add(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UserList;
    }
}


