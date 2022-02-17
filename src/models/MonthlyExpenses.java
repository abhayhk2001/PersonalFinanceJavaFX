package models;

import java.util.LinkedHashMap;

public class MonthlyExpenses {
    int userid;
    LinkedHashMap<String,Float> Expenses;
    public MonthlyExpenses(int userid) {
        this.userid = userid;
        this.Expenses = new LinkedHashMap<String,Float>();
    }
    public MonthlyExpenses(int userid,LinkedHashMap<String,Float> Expenses) {
        this.userid = userid;
        this.Expenses = Expenses;
    }
    public void add(String name, float amount) {
        this.Expenses.put(name,amount);
    }
    public LinkedHashMap<String, Float> getExpenses(){
        return this.Expenses;
    }
}
