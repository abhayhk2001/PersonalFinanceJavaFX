package models;

import java.util.ArrayList;
import java.util.Arrays;

public class User {
    private int userId;
    private String name;
    private String userName;
    private String password;
    private float salaryPerMonth;
    private int  noOfEMIs;
    private int noOfInvestments;
    private String PANNumber;
    private String DEMATAcc;
    public User(int userId)
    {
        this.userId = userId;
    }
    public User(int userId,String name, String userName, String password,float salaryPerMonth,int noOfEMIs, int noOfInvestments,String PANNumber,String DEMATAcc)
    {
        this.userId = userId;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.salaryPerMonth = salaryPerMonth;
        this.noOfEMIs = noOfEMIs;
        this.noOfInvestments = noOfInvestments;
        this.PANNumber = PANNumber;
        this.DEMATAcc = DEMATAcc;
    }
    public int getUserId() {
        return userId;
    }
    public String getName(){return this.name;}
    public ArrayList<String> getLoginInfo(){
        return new ArrayList<>(Arrays.asList(userName,password));
    }
    public float getSalary() {
        return salaryPerMonth;
    }
    public ArrayList<String> getFinanceInfo() {
        return new ArrayList<>(Arrays.asList(PANNumber,DEMATAcc));
    }
}
