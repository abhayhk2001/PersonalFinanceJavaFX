package models;

import java.util.ArrayList;
import java.util.Arrays;

public class Investment implements Amount{
        private final int userId;
        private final float totalAmount;
        private final float rateOfIntrest;
        private final int startMonth;
        private final int startYear;
        public Investment(int userId, float totalAmount, float rateOfIntrest, int startMonth, int startYear)
        {
            this.userId=userId;
            this.totalAmount = totalAmount;
            this.rateOfIntrest = rateOfIntrest;
            this.startMonth=startMonth;
            this.startYear=startYear;
        }
        public int getID(){
            return userId;
        }
        public ArrayList<Integer> getDateInfo(){
            return new ArrayList<>(Arrays.asList(startMonth, startYear));
        }
        public ArrayList<Float> getAmountInfo(){
            return new ArrayList<>(Arrays.asList(totalAmount,rateOfIntrest));
        }
}
