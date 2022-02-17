package controller;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import models.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {
    private int userID;
    @FXML
    private Circle circle;
    @FXML
    private Button showMoreUser,addEMI,addInvestment,addPersonal;
    @FXML
    private TextArea userinfo,expenses;
    @FXML
    private Label username,total;
    @FXML
    private TextField emino,investmentno;
    @FXML
    private Pagination emis, investments;

    class GetInformation implements Runnable {
        int functionNo;
        GetInformation(int no) {
            this.functionNo = no;
        }
        @Override
        public void run() {
            if(functionNo == 1) {
                setEMIDetails();
            }
            else if(functionNo ==2) {
                setInvestmentDetails();
            }
            else if(functionNo ==3) {
                setExpenseDetails();
            }
        }
    }

    public User getUserDetails(int userID) {
        Connection connection = getConnection();
        String query = "select * from user where userid=" + userID;
        Statement st;
        ResultSet rs;
        User user = null;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                user = new User(rs.getInt("userid"),rs.getString("name"),rs.getString("username"),rs.getString("password"),rs.getLong("salary"),0,0,rs.getString("pan"),rs.getString("demat"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public void AddEMIButton() throws IOException {
        new Main().changeScene("/view/AddEMI.fxml",null);
    }

    public void AddInvestmentButton() throws IOException {
        new Main().changeScene("/view/AddInvestment.fxml",null);
    }

    public void AddExpenseButton() throws IOException {
        new Main().changeScene("/view/AddExpense.fxml",null);
    }

    public ObservableList<EMI> getEMIDetails() {
        ObservableList<EMI> EMIs = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "select * from emis where userid=" + userID;
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                EMIs.add(new EMI(rs.getInt("userid"),rs.getFloat("amount"),rs.getFloat("rate"),rs.getInt("month"),rs.getInt("year"),rs.getInt("noofmonths"),rs.getInt("date")));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return EMIs;
    }

    public ObservableList<Investment> getInvestmentDetails() {
        ObservableList<Investment> Investments = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "select * from investments where userid=" + userID;
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                Investments.add(new Investment(rs.getInt("userid"),rs.getFloat("amount"),rs.getFloat("rate"),rs.getInt("month"),rs.getInt("year")));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Investments;
    }

    public MonthlyExpenses getExpenseDetails()
    {
        MonthlyExpenses Expenses = new MonthlyExpenses(userID);
        Connection connection = getConnection();
        String query = "select * from expenses where userid=" + userID;
        Statement st;
        ResultSet rs;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                Expenses.add(rs.getString("name"),rs.getFloat("amount"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Expenses;
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
    ImageView getImage(String path,int height, int width){
        Image img = new Image(path);
        ImageView view = new ImageView(img);
        view.setFitHeight(height);
        view.setFitWidth(width);
        return view;
    }

    void setUserInfo()
    {
        User user = getUserDetails(this.userID);
        char ch = Character.toUpperCase((user.getName().charAt(0)));
        Image img = new Image("/images/"+ch+".png");
        circle.setFill(new ImagePattern(img));
        username.setAlignment(Pos.CENTER);
        username.setText(user.getLoginInfo().get(0));
        String Info ="Name : "+user.getName()+"\n";
        Info += "Salary : "+user.getSalary()+"\n";
        Info += "Pan Number : "+user.getFinanceInfo().get(0)+"\n";
        Info += "DEMAT number : "+user.getFinanceInfo().get(1)+"\n";
        userinfo.setStyle("-fx-font: 16 arial;");
        userinfo.setText(Info);
    }
    void addImagesToButtons() {
        showMoreUser.setGraphic(getImage("/images/edit.png",20,20));
        addEMI.setGraphic(getImage("/images/plus.png",20,20));
        addInvestment.setGraphic(getImage("/images/plus.png",20,20));
        addPersonal.setGraphic(getImage("/images/plus.png",20,20));
    }

    VBox creatEMIPage(EMI emi, int pageindex){
        VBox pageBox = new VBox();
        TextArea info = new TextArea();
        ArrayList<Integer> dateInfo = emi.getDateInfo();
        ArrayList<Float> amountInfo = emi.getAmountInfo();
        String EMIInfo ="Amount per Month : "+ amountInfo.get(0)+"\n";
        EMIInfo += "Rate of Interest : "+amountInfo.get(1)*100+"%\n";
        EMIInfo += "Payment Date : "+ dateInfo.get(0)+"\n";
        EMIInfo += "Months Left : "+dateInfo.get(3)+"\n";
        info.setStyle("-fx-font: 16 arial;");
        info.setText(EMIInfo);
        pageBox.getChildren().add(info);
        return pageBox;
    }
    void setEMIDetails(){
        ObservableList<EMI> allEMIs = getEMIDetails();
        emis.setPageCount(allEMIs.size());
        emino.setText(Integer.toString(allEMIs.size()));
        System.out.println(""+allEMIs.size());
        emis.setPageFactory((Integer pageIndex) -> creatEMIPage(allEMIs.get(pageIndex), pageIndex));
    }
    VBox createInvestmentPage(Investment investment, int pageindex){
        VBox pageBox = new VBox();
        TextArea info = new TextArea();
        ArrayList<Integer> dateInfo = investment.getDateInfo();
        ArrayList<Float> amountInfo = investment.getAmountInfo();
        String Info ="Amount per Month : "+ amountInfo.get(0)+"\n";
        Info += "Rate of Interest : "+amountInfo.get(1)*100+"%\n";
        Info += "Start Month : "+ dateInfo.get(0)+"\n";
        Info += "Start Year : "+dateInfo.get(1)+"\n";
        info.setStyle("-fx-font: 16 arial;");
        info.setText(Info);
        pageBox.getChildren().add(info);
        return pageBox;
    }
    void setInvestmentDetails(){
        ObservableList<Investment> allInvestments = getInvestmentDetails();
        investments.setPageCount(allInvestments.size());
        investmentno.setText(""+allInvestments.size());
        investments.setPageFactory((Integer pageIndex) -> createInvestmentPage(allInvestments.get(pageIndex), pageIndex));
    }

    void setExpenseDetails(){
        MonthlyExpenses expenses = getExpenseDetails();
        LinkedHashMap<String, Float> allExpenses = expenses.getExpenses();
        StringBuilder Info = new StringBuilder();
        double Total=0;
        for(String expenseName : allExpenses.keySet()){
            Info.append(expenseName).append("    ").append(allExpenses.get(expenseName)).append("\n");
            Total += allExpenses.get(expenseName);
        }
        total.setText("Total Amount: "+Total);
        this.expenses.setStyle("-fx-font: 16 arial;");
        this.expenses.setText(Info.toString());
    }

    void getAllInfo() throws InterruptedException {
        Thread t1 = new Thread(new GetInformation(1));
        Thread t2 = new Thread(new GetInformation(2));
        Thread t3 = new Thread(new GetInformation(3));
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userID = 1;
        addImagesToButtons();
        setUserInfo();
        try {
            getAllInfo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}