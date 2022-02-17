package models;

import java.util.ArrayList;
import java.util.Arrays;


interface Amount{
    float totalAmount = 0;
    float rateOfIntrest = 0;
}
public class EMI implements Amount {
    private final int userId;
    private final float totalAmount;
    private final float rateOfIntrest;
    private final int startMonth;
    private final int startYear;
    private final int noOfMonths;
    private final int startDate;
    public EMI(int userId, float totalAmount, float rateOfIntrest, int startMonth, int startYear, int noOfMonths, int startDate)
    {
        this.userId=userId;
        this.totalAmount = totalAmount;
        this.rateOfIntrest = rateOfIntrest;
        this.startDate = startDate;
        this.startMonth=startMonth;
        this.startYear=startYear;
        this.noOfMonths=noOfMonths;
    }
    public ArrayList<Integer> getDateInfo(){
        return new ArrayList<>(Arrays.asList(startDate, startMonth, startYear, noOfMonths));
    }
    public ArrayList<Float> getAmountInfo(){
        return new ArrayList<>(Arrays.asList(totalAmount,rateOfIntrest));
    }
}

